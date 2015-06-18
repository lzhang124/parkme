var express = require('express');
var router = express.Router();

module.exports = function(passport) {
  
  /* GET home page. */
  router.get('/', function(req, res) {
    res.render('index');
  });

  /* GET info page. */
  router.get('/info', function(req, res) {
    res.render('info');
  });

  /* GET results page. */
  router.get('/results', function(req, res) {
    res.render('results');
  });

  /* GET map page. */
  router.get('/map', function(req, res) {
    res.render('map');
  });

  /* Signup */
  router.post('/signup', passport.authenticate('signup', {
    successRedirect: '/',
    failureRedirect: '/failure',
    failureFlash: true,
    successFlash: true
  }));

  /* Login */ 
  router.post('/login', passport.authenticate('login', {
    successRedirect: '/',
    failureRedirect: '/failure',
    failureFlash: true,
    successFlash: true
  }));

  /* GET failure page. */
  router.get('/failure', function(req, res) {
    res.render('failure');
  });

  return router;
}
