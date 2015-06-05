var express = require('express');
var https = require('https');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res) {
  res.render('index');
});

/* GET results page. */
router.get('/results', function(req, res) {
  res.render('results');
});

/* REQUEST lots by zipcode*/
router.post('/', function(req, res) {
  var search = req.body['search'];
  var url = "https://127.0.0.1:8080/lotsByZipcode?zipcode=" + search;

  // https.get(url, function(res) {
  //   console.log("statusCode: ", res.statusCode);
  //   console.log("headers: ", res.headers);
  //   var data = '';

  //   res.on('data', function(d) {
  //     data += d;
  //   });

  //   res.on('end', function() {
  //     var lots = JSON.parse(data);
  //     console.log(lots);
  //   });
  // }).on('error', function(e) {
  //   console.error(e);
  // });

  res.redirect('results');
});

module.exports = router;
