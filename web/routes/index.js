var express = require('express');
var router = express.Router();

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

  /* signup */
  router.post('/signup', passport.authenticate('signup', {
    successRedirect: '/q9xwGoXLGQ/register',
    failureRedirect: '/q9xwGoXLGQ/signup',
    failureFlash: true,
    successFlash: true
  }));

  /* login */ 
  router.post('/login', passport.authenticate('login', {
    successRedirect: '/q9xwGoXLGQ',
    failureRedirect: '/q9xwGoXLGQ/login',
    failureFlash: true,
    successFlash: true
  }));

  /* GET register page. */
  router.get('/q9xwGoXLGQ/register', function(req, res) {
    res.render('register');
  });

  return router;
}
