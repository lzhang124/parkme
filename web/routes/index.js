var express = require('express');
var router = express.Router();

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

/* GET home page. */
router.get('/q9xwGoXLGQ', function(req, res) {
  res.render('index', { user: req.user });
});

/* GET about page. */
router.get('/q9xwGoXLGQ/about', function(req, res) {
  res.render('about');
});

/* GET profile page. */
router.get('/q9xwGoXLGQ/profile', isAuthenticated, function(req, res) {
  res.render('profile', { user: req.user });
});

/* GET register page. */
router.get('/q9xwGoXLGQ/register', isAuthenticated, function(req, res) {
  res.render('register', { user: req.user });
});

module.exports = router;
