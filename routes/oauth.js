var express = require('express');
var router = express.Router();
const mod = require('../modules').module;
const users = require('../database/users.json');
const { menus_top, menus_bottom } = require('../database/menus');
const categories = require('../database/category-article');
const moment = require('moment');

const EXPIRES_IN = 60 * 60 * 24; // seconds

/* GET home page. */
router.get('/AuthPage', function (req, res, next) {
    let state = mod.crypto.randomBytes(16).toString('hex');
    res.cookie('XSRF-TOKEN', state);
    res.send({ authUrl: "https://github.com/login/oauth/authorize?client_id=" + mod.config.CLIENT_ID + '&redirect_uri=' + mod.config.REDIRECT_URI + '&scope=read:user&allow_signup=' + true + '&state=' + state });

});

router.post('/getAccessToken', function (req, res) {
    let state = req.headers["x-xsrf-token"];
    mod.axios({
        url: 'https://github.com/login/oauth/access_token?client_id=' + mod.config.CLIENT_ID + '&client_secret=' + mod.config.CLIENT_SECRET + '&code=' + req.body.code + '&redirect_uri=' + mod.config.REDIRECT_URI + '&state=' + state,
        method: 'POST',
        headers: { 'Accept': 'application/json' }
    })
        .then(function (resp) {
            if (resp.data.access_token) {
                req.session.token = resp.data.access_token;
            }
            res.send(resp.data);
        })
        .catch(function (err) {
            console.error(err);
            res.send(err);
        })

});

let failCount = 0;
router.post('/login', (req, res) => {
    const { username, password, remember } = req.body;
    const found = users.find(d => d.username == username && d.password == password);
    const now = moment();
    // const expiresIn = now.add(1, 'h');
    const expiresIn = EXPIRES_IN;
    if (found) {
        failCount = 0;
        res.send({ error: false, msg: "", data: Object.assign({}, found, { password: null, expiresIn, remember }) });
    } else {
        failCount++;
        let remaining = 6 - failCount;
        let alert = `You have ${remaining} remaining attempts to login`;
        if (failCount > 5) {
            alert = `User ‘<b>${username}</b>’ currently locked. Please try again in <b>xx minutes.</b><br>Test new Line`;
        }
        res.send({ status: { code: '99', message: "Incorrect User ID or password", failCount, alert } });
    }
});

router.post('/getUser', (req, res) => {
    const { authToken, username } = req.body;
    const found = users.find(d => {
        if (d.username == username && d.authToken == authToken)
            return true;
        else
            return false
    })
    if (found) {
        res.send({ error: false, msg: "", data: Object.assign({}, found, { password: null }) });
    } else {
        res.send({ status: { code: '99', message: "Username and token invalid" } });
    }
})

router.post('/refreshToken', (req, res) => {
    const { refreshToken } = req.body;
    console.log(req.headers);
    const found = users.find(d => d.refreshToken == refreshToken);
    if (found) {
        found.authToken = `${found.authToken}-REFRESH`;
        found.refreshToken = `${found.authToken}-refreshToken`;
        const { authToken, refreshToken } = found;
        res.send({ error: false, msg: "", data: { authToken, refreshToken, expiresIn: EXPIRES_IN } });
    } else {
        res.send({ error: true, msg: "Username and token invalid" });
    }
})

router.post('/logout', (req, res) => {
    res.send({ error: false, msg: "", data: {} });
});

router.get('/menu', (req, res) => {
    res.send({ error: false, msg: "", data: menus_top.concat(categories).concat(menus_bottom) });
});

router.post('/searchUserNotReader', (req, res) => {
    res.send({ error: false, msg: "", data: users });
});

module.exports = router;
