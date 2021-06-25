const express = require('express');
const router = express.Router();
const categoryArticle = require('../database/category-article');
const { search, articles, recommendation, news, popular, suggestion } = require('../database/articles');
const theme = require('../database/themes');
const _ = require('lodash');
const path = require('path');

router.get('/theme', function (req, res) {
    res.send({ error: false, msg: '', data: theme });
});

router.get('/category-article', (req, res) => {
    res.send({ error: false, msg: "", data: categoryArticle });
});

router.get('/all', (req, res) => {
    res.send({ error: false, msg: "", data: articles });
});

router.post('/search', (req, res) => {
    const { keyword } = req.body;
    if (!keyword || keyword == 'kosong') {
        res.send({
            error: false, msg: "", data: {
                result: {
                    total: 0, length: 0, data: []
                }
            }
        });
        return;
    }
    const keys = _.uniq(articles.map(d => d.type));
    const grouping = _.groupBy(articles, 'type');
    const group = {};
    for (let k of keys) {
        group[k] = {
            total: Math.floor(Math.random() * 100 + 10),
            length: grouping[k].length,
            data: grouping[k]
        }
    }
    const data = {
        result: {
            data: articles,
            total: Math.floor(Math.random() * 100 + 10),
            length: articles.length
        },
        keys,
        group,
        suggestion: 'Pengajuan Time Loan SME'
    }
    res.send({ error: false, msg: "", data });
});

router.post('/suggestion', (req, res) => {
    res.send({ error: false, msg: "", data: suggestion });
});

router.post('/recommendation', (req, res) => {
    const { page, offset, limit } = req.body;
    res.send({ error: false, msg: "", data: recommendation });
});

router.post('/popular', (req, res) => {
    const { page, offset, limit } = req.body;
    res.send({ error: false, msg: "", data: popular });
});

router.post('/news', (req, res) => {
    const { page, offset, limit } = req.body;
    res.send({ error: false, msg: "", data: news });
});

function findParent(categories, parentId, level) {
    let parent = [], node;
    // const { id, name, desc, edit, level, sort } = item;
    if (level == 2) {
        let found = categories.find(d => d.id == parentId);
        if (found) {
            parent.push(found);
        }
        // categories.forEach(d => {
        //     if (found) return;
        //     found = d.menus.find(d => d.id == id);
        //     if (found) {
        //         node = d;
        //         parent.push(d.id);
        //     } else {

        //     }
        // })
    }
    return [parent, node];
}

router.post('/struktur-save', (req, res, next) => {
    const { body, files } = req;
    console.log({ body, files });
    const { name, desc, edit, level, sort, parent, location, location_text } = body;
    const $level = parseInt(level);
    const $parent = parseInt(parent);


    let _parent = [], _node = null, last_category = [], id = 0;
    if ($level == 1) {
        last_category = categoryArticle.slice(-1)[0];
    } else if ($level == 2) {
        [_parent, _node] = findParent(categoryArticle, $parent, $level);
        _parent = _parent.slice(-1)[0];
        last_category = _parent.menus.slice(-1)[0];
    }
    if (last_category) {
        id = last_category.id;
    }
    const new_id = id + 1000;

    const imagesPath = path.join(process.cwd(), '/public/images');
    let icon = '', image = '';
    if (files) {
        const iconFile = files['icon'];
        if (iconFile) {
            icon = `/menus/${new_id}-${iconFile.name}`;
            iconFile.mv(`${imagesPath}${icon}`, function (err) {
                if (err) console.error(err)
            });
        }

        const imageFile = files['image'];
        if (imageFile) {
            image = `/struktur/${new_id}-${imageFile.name}`;
            imageFile.mv(`${imagesPath}${image}`, function (err) {
                if (err) console.error(err)
            });
        }
    }

    const new_data = {
        id: new_id,
        title: name,
        desc,
        sort: parseInt(sort),
        uri: `/article/list/${new_id}`,
        level: $level,
        menus: [],
        icon,
        image,
        edit,
        location,
        location_text,
        parent: $parent
    }

    if ($level == 1) {
        categoryArticle.push(new_data);
    } else if ($level == 2) {
        _parent.menus.push(new_data);
    }

    res.send({ error: false, msg: "", data: new_data });
});

router.post('/struktur-update', (req, res) => {
    const { body, files } = req;
    const { id, name, desc, level, parent, location, location_text } = body;

    console.log({ body, files });

    const imagesPath = path.join(process.cwd(), '/public/images');
    let icon = '', image = '';

    let found = null;
    if (level == 1) {
        found = categoryArticle.find(d => d.id == id);
    } else if (level == 2) {
        const _parent = categoryArticle.find(d => d.id == parent);
        found = _parent.menus.find(d => d.id == id);
    }

    if (!found) res.send({ error: true, msg: "data tidak ditemukan" });
    if (files) {
        const iconFile = files['icon'];
        if (iconFile) {
            icon = `/menus/${id}-${iconFile.name}`;
            iconFile.mv(`${imagesPath}${icon}`, function (err) {
                if (err) console.error(err)
            });
        }

        const imageFile = files['image'];
        if (imageFile) {
            image = `/struktur/${id}-${imageFile.name}`;
            imageFile.mv(`${imagesPath}${image}`, function (err) {
                if (err) console.error(err)
            });
        }
    }
    found.title = name;
    found.desc = desc;
    found.icon = icon || found.icon;
    found.image = icon || found.image;
    found.location = location;
    found.location_text = location_text;

    res.send({ error: false, msg: "", data: found });
});

router.post('/struktur-delete', (req, res) => {
    const { id } = req.body;
    const found = categoryArticle.find(d => d.id == id);
    const notDelete = categoryArticle.filter(d => d.id != id);

    if (found.menus && found.menus.length) {
        let sort = notDelete.length;
        found.menus.forEach(d => {
            d.level = 0;
            d.sort = sort++;
            notDelete.push(d);
        });
    }

    while (categoryArticle.length) {
        categoryArticle.pop();
    }
    notDelete.map(d => {
        categoryArticle.push(d);
    })

    res.send({ error: false, msg: "", data: categoryArticle });
});


module.exports = router;
