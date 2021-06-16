const express = require('express');
const router = express.Router();
const categoryArticle = require('../database/category-article');
const { search, articles, recommendation, news, popular } = require('../database/articles');
const theme = require('../database/themes');

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
    res.send({ error: false, msg: "", data: search });
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

module.exports = router;
