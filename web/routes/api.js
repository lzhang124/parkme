var express = require('express');
var router = express.Router();
var LotController = require('../controllers/lot-controller');
var SearchController = require('../controllers/search-controller');

var isAuthenticated = function (req, res, next) {
  if (req.isAuthenticated()) {
    return next();
  }
  res.redirect('/q9xwGoXLGQ');
}

/* GET current user */
router.get('/currentUser', function(req, res) {
  if (req.user) {
    res.send(req.user);
  } else {
    res.send(false);
  }
});

/* Add search history */ 
router.post('/search', SearchController.newHistory);

/* Create new lot */ 
router.post('/newLot', isAuthenticated, LotController.newLot);

module.exports = router;
