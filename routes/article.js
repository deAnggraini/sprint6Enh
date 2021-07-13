const express = require('express');
const router = express.Router();
const categoryArticle = require('../database/category-article');
const { search, articles, recommendation, news, popular, suggestion, lastKeyword } = require('../database/articles');
const theme = require('../database/themes');
const _ = require('lodash');
const path = require('path');
const categories = require('../database/category-article');
const { templates } = require('../database/article-template');

router.get('/theme', function (req, res) {
    res.send({ error: false, msg: '', data: theme });
});

router.get('/category', (req, res) => {
    res.send({ error: false, msg: "", data: categoryArticle });
});

router.get('/all', (req, res) => {
    res.send({ error: false, msg: "", data: articles });
});

router.post('/keyword', (req, res) => {
    res.send({ error: false, msg: "", data: lastKeyword });
});
router.post('/search', (req, res) => {
    const { keyword, page } = req.body;
    console.log('search article', { keyword, page });
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
            length: articles.length,
            page,
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

router.post('/templates', (req, res) => {
    res.send({ error: false, msg: "", data: templates });
});

function findParent(categories, parentId, level = 1) {
    if (!categories) return null;
    let parent = null;
    if (level == 2) {
        parent = categories.find(d => d.id == parentId);
    } else {
        parent = categories.find(d => d.id == parentId);
        if (!parent) {
            categories.forEach(d => {
                if (parent) return;
                parent = findParent(d.menus, parentId);
            })
        }
    }
    return parent;
}

function findNode(categories, id) {
    if (!categories) return null;
    node = categories.find(d => d.id == id);
    if (!node) {
        categories.forEach(d => {
            if (node) return;
            node = findNode(d.menus, id);
        });
    }
    return node;
}

router.post('/saveStructure', (req, res, next) => {
    const { body, files } = req;
    console.log({ body, files });
    const { name, desc, edit, level, sort, parent, location, location_text } = body;
    const $level = parseInt(level);
    const $parent = parseInt(parent);


    let _parent = [], _node = null, last_category = [], id = 0;
    if ($level == 1) {
        last_category = categoryArticle.slice(-1)[0];
    } else {
        _parent = findParent(categoryArticle, $parent, $level);
        last_category = _parent.menus.slice(-1)[0];
    }
    if (last_category) {
        id = last_category.id;
    }
    const new_id = id + 1000 + Math.floor(Math.random() * 10000);;

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
            image = `/structure/${new_id}-${imageFile.name}`;
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
    } else {
        _parent.menus.push(new_data);
    }

    res.send({ error: false, msg: "", data: new_data });
});

router.post('/updateStructure', (req, res) => {
    const { body, files } = req;
    console.log({ body, files });
    const { id, name, desc, level, parent, location, location_text } = body;
    const $level = parseInt(level);
    const $parent = parseInt(parent);

    const imagesPath = path.join(process.cwd(), '/public/images');
    let icon = '', image = '';

    let found = null;
    if (level == 1) {
        found = categoryArticle.find(d => d.id == id);
    } else {
        const _parent = findParent(categories, $parent, $level);
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
            image = `/structure/${id}-${imageFile.name}`;
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

function calculateLevel(list = [], level = 1) {
    if (list && list.length) {
        list.forEach(d => {
            d.level = level;
            calculateLevel(d.menus, level + 1);
        })
    }
}

router.post('/deleteStructure', (req, res) => {
    const { id, changeTo } = req.body;
    console.log({ id, changeTo });
    // let notDelete = [], brothers = [];

    const found = findNode(categoryArticle, id, []);
    // if (found.level == 1) {
    //     brothers = categoryArticle;
    //     notDelete = brothers.filter(d => d.id != id);
    // } else {
    // const parent = findParent(categoryArticle, found.parent);
    // brothers = parent.menus;
    // notDelete = brothers.filter(d => d.id != id);

    // cara baru dengan field changeTo
    if (changeTo.length) {
        changeTo.forEach(d => {
            const { id, changeTo } = d;
            const found = findNode(categoryArticle, id, []);
            const parent = findNode(categoryArticle, changeTo, []) || { id: 0, level: 0, menus: [] };
            if (!parent.hasOwnProperty('menus')) {
                parent.menus = [];
            }

            found.parent = parent.id;
            found.level = parent.level + 1;
            found.sort = parent.menus.length + 1;
            parent.menus.push(found);
        });
    }
    if (found.level > 1) {
        const parent = findParent(categoryArticle, found.parent);
        parent.menus = parent.menus.filter(d => d.id != id);
    } else {
        const notDelete = categoryArticle.filter(d => d.id != id);
        while (categoryArticle.length) {
            categoryArticle.pop();
        }
        notDelete.map(d => {
            categoryArticle.push(d);
        });
    }
    res.send({ error: false, msg: "", data: categoryArticle });
    // }

    // if (found.menus && found.menus.length) {
    //     let sort = notDelete.length;
    //     found.menus.forEach(d => {
    //         d.level = found.level;
    //         d.sort = sort++;
    //         notDelete.push(d);
    //     });
    // }

    // while (brothers.length) {
    //     brothers.pop();
    // }
    // notDelete.map(d => {
    //     brothers.push(d);
    // });

    // calculateLevel(categoryArticle);

    // res.send({ error: false, msg: "", data: categoryArticle });
});

router.post('/saveBatchStructure', (req, res) => {
    const { body } = req;
    const { id, children } = body;
    console.log({ id, children });

    const found = categoryArticle.find(d => d.id == id);
    if (!found) {
        if (!found) res.send({ error: true, msg: "data tidak ditemukan" });
    }

    const new_child = [];
    const list_level_2 = children.filter(d => parseInt(d.level) == 2);
    const list_level_3 = children.filter(d => parseInt(d.level) == 3);
    const list_level_4 = children.filter(d => parseInt(d.level) == 4);

    list_level_2.forEach(lvl2 => {
        const node_lvl2 = findNode(categoryArticle, lvl2.id);
        if (node_lvl2) {
            const _node_lvl2 = JSON.parse(JSON.stringify(node_lvl2));
            _node_lvl2.parent = parseInt(lvl2.parent);
            _node_lvl2.sort = parseInt(lvl2.sort);
            _node_lvl2.menus = [];

            const foundLvl3 = list_level_3.filter(d => d.parent == _node_lvl2.id);
            foundLvl3.forEach(lvl3 => {
                const node_lvl3 = findNode(categoryArticle, lvl3.id);
                if (node_lvl3) {
                    const _node_lvl3 = JSON.parse(JSON.stringify(node_lvl3));
                    _node_lvl3.parent = parseInt(lvl3.parent);
                    _node_lvl3.sort = parseInt(lvl3.sort);
                    _node_lvl3.menus = [];

                    const foundLvl4 = list_level_4.filter(d => d.parent == _node_lvl3.id);
                    foundLvl4.forEach(lvl4 => {
                        const node_lvl4 = findNode(categoryArticle, lvl4.id);
                        if (node_lvl4) {
                            const _node_lvl4 = JSON.parse(JSON.stringify(node_lvl4));
                            _node_lvl4.parent = parseInt(lvl4.parent);
                            _node_lvl4.sort = parseInt(lvl4.sort);
                            _node_lvl4.menus = [];
                            _node_lvl3.menus.push(_node_lvl4);
                        }
                    })
                    _node_lvl2.menus.push(_node_lvl3);
                }
            })
            new_child.push(_node_lvl2);
        }
    });

    found.menus = new_child;
    res.send({ error: false, msg: "debug", data: new_child });
});

router.post('/checkUnique', (req, res) => {
    const { title } = req.body;
    if (title == 'test') {
        res.send({ status: { code: '09', message: "title sudah ada" } });
    } else {
        res.send({ error: false, msg: "", data: {} });
    }
});

module.exports = router;
