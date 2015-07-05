var express = require('express');
var router = express.Router();

var newLot = require('../controller/lot');

var isAuthenticated = function (req, res, next) {
    if (req.isAuthenticated())
      return next();
    res.redirect('/q9xwGoXLGQ');
}

module.exports = function(passport) {
  
  /* GET splash page. */
  router.get('/', function(req, res) {
    res.render('splash');
  })

  /* GET home page. */
  router.get('/q9xwGoXLGQ', function(req, res) {
    res.render('index');
  });

  /* GET info page. */
  router.get('/q9xwGoXLGQ/info', function(req, res) {
    res.render('info');
  });

  /* Sign up */
  router.post('/signup', passport.authenticate('signup', {
    successRedirect: '/q9xwGoXLGQ/register',
    failureRedirect: '/q9xwGoXLGQ/signup',
    failureFlash: true,
    successFlash: true
  }));

  /* Login */ 
  router.post('/login', passport.authenticate('login', {
    successRedirect: '/q9xwGoXLGQ',
    failureRedirect: '/q9xwGoXLGQ/login',
    failureFlash: true,
    successFlash: true
  }));

  /* GET register page. */
  router.get('/q9xwGoXLGQ/register', isAuthenticated, function(req, res) {
    res.render('register', { user: req.user });
  });

  /* Create new lot */ 
  router.post('/newLot', isAuthenticated, newLot);

  return router;
}
