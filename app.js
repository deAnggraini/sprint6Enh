var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
const cors = require('cors');
const fileUpload = require('express-fileupload');
var bodyParser = require('body-parser');
var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var mod = require('./modules').module;

var app = express();
app.use(cors());
// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(logger('dev'));

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// app.use(express.json());
// app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(mod.cors({
  origin: ['http://localhost:4200'],
  methods: ['GET', 'PUT', 'POST', 'DELETE']
}));
app.use(mod.cookieSession({
  name: 'sess', //name of the cookie containing access token in the //browser
  secret: 'asdfgh',
  httpOnly: true
}));

// body parser
// app.use(bodyParser.urlencoded());
// app.use(bodyParser.urlencoded({ extended: true }));
// app.use(bodyParser.json());

app.use(fileUpload());
app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/auth', require('./routes/oauth'));
app.use('/auth', require('./routes/oauth'));
// app.use('/master', require('./routes/master'));
app.use('/doc', require('./routes/article'));
app.use('/v1/doc', require('./routes/v1'));
// app.use('/theme', require('./routes/themes'));

// catch 404 and forward to error handler
app.use(function (req, res, next) {
  next(createError(404));
});

// error handler
app.use(function (err, req, res, next) {
  console.error(err);
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
