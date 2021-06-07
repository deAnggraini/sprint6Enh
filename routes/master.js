const express = require('express');
const router = express.Router();

const categoryArticle = require('../database/category-article');

router.get('/category-article', (req, res) => {
    res.send({ error: false, msg: "", data: categoryArticle });
});

module.exports = router;
