var express = require('express');
var router = express.Router();

var newLot = require('../controller/lot');

var isAuthenticated = function (req, res, next) {
    if (req.isAuthenticated()) {
      return next();
    }
    res.redirect('/q9xwGoXLGQ');
}

module.exports = function(passport) {

  /* GET current user */
  router.get('/currentUser', function(req, res) {
    if (req.user) {
      res.send(req.user);
    } else {
      res.send(null);
    }
  });

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

  /* Logout */ 
  router.get('/logout', function(req, res) {
    req.logout();
    res.redirect('/q9xwGoXLGQ');
  });

  /* GET profile page. */
  router.get('/q9xwGoXLGQ/profile', isAuthenticated, function(req, res) {
    res.render('profile', { user: req.user });
  });


  /* GET register page. */
  router.get('/q9xwGoXLGQ/register', isAuthenticated, function(req, res) {
    res.render('register', { user: req.user });
  });

  /* Create new lot */ 
  router.post('/newLot', isAuthenticated, newLot);

  return router;
}
