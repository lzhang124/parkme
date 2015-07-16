var express = require('express');
var router = express.Router();
var http = require('http');
var ReservationController = require('../controllers/reservation-controller');

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

/* GET about page. */
router.get('/q9xwGoXLGQ/about', function(req, res) {
  res.render('about');
});

/* GET profile page. */
router.get('/q9xwGoXLGQ/profile', isAuthenticated, function(req, res) {
  res.render('profile');
});

/* GET register page. */
router.get('/q9xwGoXLGQ/register', isAuthenticated, function(req, res) {
  res.render('register');
});

/* GET reservations page. */
router.get('/q9xwGoXLGQ/reservations/:lotId', isAuthenticated, ReservationController.getReservations);

module.exports = router;
