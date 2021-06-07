const express = require('express');
const router = express.Router();

const articles = require('../database/articles');

router.get('/all', (req, res) => {
    res.send({ error: false, msg: "", data: articles });
});

router.get('/recomendation', (req, res) => {
    const { page, offset, limit } = req.body;
    res.send({ error: false, msg: "", data: articles });
});

router.get('/popular', (req, res) => {
    const { page, offset, limit } = req.body;
    res.send({ error: false, msg: "", data: articles });
});

router.get('/news', (req, res) => {
    const { page, offset, limit } = req.body;
    res.send({ error: false, msg: "", data: articles });
});

module.exports = router;
