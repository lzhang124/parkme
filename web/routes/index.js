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

  return router;
}
