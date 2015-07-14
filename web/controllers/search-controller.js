var http = require('http');
var querystring = require('querystring');
var SearchController = {};

SearchController.newHistory = function(req, res) {
  console.log('test');
  var search = querystring.stringify({
    accountId: req.user.id,
    latitude: req.body.latitude,
    longitude: req.body.longitude
  });
  console.log(search);
  var options = {
    host: '127.0.0.1',
    port: 8080,
    path: '/addSearchHistory',
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  };

  var request = http.request(options, function(response) {
    console.log('hi');
    response.setEncoding('utf8');
    var data = '';

    response.on('data', function(chunk) {
      console.log('hi');
      data += chunk;
    });

    response.on('end', function() {
      console.log('hello');
      if (data === '') {
        console.log('blank');
        res.status(500).send('error');
        return;
      }
      var history = JSON.parse(data);
      res.send('success');
    });
  });
  request.on('error', function(err) {
    console.log('Error in adding history: ' + err);
      throw err;
  });

  request.write(search);
  request.end();
}

module.exports = SearchController;
