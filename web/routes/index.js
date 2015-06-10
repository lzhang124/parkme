var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res) {
  res.render('index');
});

router.get('/info', function(req, res) {
    res.render('info');
});

module.exports = router;
