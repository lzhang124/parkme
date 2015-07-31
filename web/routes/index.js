var express = require('express');
var router = express.Router();
var http = require('http');

var isAuthenticated = function (req, res, next) {
  if (req.isAuthenticated()) {
    return next();
  }
  res.redirect('/q9xwGoXLGQ');
}

/* GET splash page. */
router.get('/', function(req, res) {
  res.render('splash');
});

/* GET parth's page. */
router.get('/DJYWcyUqGV', function(req, res) {
  http.get('http://52.25.5.25:8080/lotById?lotId=55a58121e4b081c25a7efae0', function(response) {
    var body = '';
    response.on('data', function(d) {
      body += d;
    });
    response.on('end', function() {
      var data = JSON.parse(body);
      res.render('parth', { lot : data });
    });
  });
});

/* GET home page. */
router.get('/q9xwGoXLGQ', function(req, res) {
  res.render('index');
});

/* GET info page. */
router.get('/q9xwGoXLGQ/info', function(req, res) {
  res.render('info');
});

/* GET profile page. */
router.get('/q9xwGoXLGQ/profile', isAuthenticated, function(req, res) {
  res.render('profile');
});

/* GET register page. */
router.get('/q9xwGoXLGQ/register', isAuthenticated, function(req, res) {
  res.render('register');
});

/* redirect reservations page. */
router.get('/q9xwGoXLGQ/reservations', isAuthenticated, function(req, res) {
  res.render('reservations');
});

/* GET reservations page. */
router.get('/q9xwGoXLGQ/reservations/:lotId', isAuthenticated, function(req, res) {
  res.render('reserve');
});

module.exports = router;
