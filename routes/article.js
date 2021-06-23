const express = require('express');
const router = express.Router();
const categoryArticle = require('../database/category-article');
const { search, articles, recommendation, news, popular, suggestion } = require('../database/articles');
const theme = require('../database/themes');
const _ = require('lodash');

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
    console.log({ keyword });
    const keys = _.uniq(articles.map(d => d.type));
    const grouping = _.groupBy(articles, 'type');
    // console.log({ key, grouping });
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

router.post('/struktur-save', (req, res) => {
    const { body, files } = req;
    console.log({ body });
    console.log({ files });
    res.send({ error: true, msg: "", data: {} });
});


module.exports = router;
