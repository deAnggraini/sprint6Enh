const express = require('express');
const router = express.Router();
const theme = require('../database/themes');

/* GET users listing. */
router.get('/', function (req, res, next) {
    res.send({ error: false, msg: '', data: theme });
});

module.exports = router;
