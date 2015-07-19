var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var flash = require('connect-flash');

var routes = require('./routes/index');
var api = require('./routes/api');
var auth = require('./routes/auth');

var app = express();

var expressSession = require('express-session');
var passport = require('passport');

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.engine('html', require('ejs').renderFile);
app.set('view engine', 'html');

//app.use(favicon(__dirname + '/public/favicon.ico'));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
// remove after testing
app.use('/DJYWcyUqGV', express.static(path.join(__dirname, 'public')));
app.use('/q9xwGoXLGQ', express.static(path.join(__dirname, 'public')));
app.use('/q9xwGoXLGQ/reservations', express.static(path.join(__dirname, 'public')));

app.use(expressSession({
  secret: 'thisisasecret',
  resave: false,
  saveUninitialized: true
}));
app.use(passport.initialize());
app.use(passport.session());
app.use(flash());

// routes
app.use('/', routes);
app.use('/api', api);
app.use('/auth', auth);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
    res.render('error');
});

// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
  process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";
  app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
      message: err.message,
      error: err
    });
  });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
  res.status(err.status || 500);
  res.render('error', {
    message: err.message,
    error: {}
  });
});

app.set('port', process.env.PORT || 3000);
var server = app.listen(app.get('port'));

module.exports = app;
