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

  /* SIGNUP */
  router.post('/signup', passport.authenticate('signup', {
    successRedirect: '/success',
    failureRedirect: '/failure',
    failureFlash: true,
    successFlash: true
  }));

  /* Login */ 
  router.post('/login', passport.authenticate('login', {
    successRedirect: '/success',
    failureRedirect: '/failure',
    failureFlash: true,
    successFlash: true
  }));

  /* GET success page. */
  router.get('/success', function(req, res) {
    res.render('success');
  });

  /* GET failure page. */
  router.get('/failure', function(req, res) {
    res.render('failure');
  });

  return router;
}
