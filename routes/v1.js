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

module.exports = router;
