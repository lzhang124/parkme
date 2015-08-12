var http = require('http');
var querystring = require('querystring');
var ReservationController = {};

var url = 'http://52.25.5.25:8080';

ReservationController.reserve = function(req, res) {
  var reservation = querystring.stringify({
    accountId: req.user.id,
    lotId: req.body.lotId,
    startTimes: req.body.startTimes,
    durations: req.body.durations
  });
  var options = {
    host: '52.25.5.25',
    port: 8080,
    path: '/reserve',
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  };

  // NO NEW RESERVATIONS
  if (req.body.startTimes.length === 0) {
    res.send('/miidUx5rtG');
  } else {
    var request = http.request(options, function(response) {
      response.setEncoding('utf8');
      var data = '';

      response.on('data', function(chunk) {
        data += chunk;
      });

      response.on('end', function() {
        if (data === '') {
          console.log('Conflict.');
          res.status(409).send({ 'error': 'Could not make reservations.' });
          return;
        }
        console.log(JSON.parse(data));
        console.log('Reservation created!');
        res.send('/miidUx5rtG');
      });
    });
    request.on('error', function(err) {
      console.log('Error in making reservation: ' + err);
      throw err;
    });
    
    request.write(reservation);
    request.end();
  }
}

module.exports = ReservationController;
