const express = require('express');
const router = express.Router();

const { search, articles, recommendation, news } = require('../database/articles');

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

router.get('/popular', (req, res) => {
    const { page, offset, limit } = req.body;
    res.send({ error: false, msg: "", data: articles });
});

router.post('/news', (req, res) => {
    const { page, offset, limit } = req.body;
    res.send({ error: false, msg: "", data: news });
});

module.exports = router;
