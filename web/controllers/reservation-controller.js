var http = require('http');
var querystring = require('querystring');
var ReservationController = {};

var url = 'http://127.0.0.1:8080';

ReservationController.reserve = function(req, res) {
  var reservation = querystring.stringify({
    accountId: req.user.id,
    lotId: req.body.lotId,
    start: req.body.start,
    duration: req.body.duration
  });
  var options = {
    host: '127.0.0.1',
    port: 8080,
    path: '/reserve',
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  };

  var request = http.request(options, function(response) {
    response.setEncoding('utf8');
    var data = '';

    response.on('data', function(chunk) {
      data += chunk;
    });

    response.on('end', function() {
      if (data === '') {
        console.log('Conflict.');
        res.status(409).send({ 'error': 'Conflict.' });
        return;
      }
      console.log(JSON.parse(data));
      console.log('Reservation created!');
      res.send('/q9xwGoXLGQ');
    });
  });
  request.on('error', function(err) {
    console.log('Error in making reservation: ' + err);
    throw err;
  });

  request.write(reservation);
  request.end();
}

module.exports = ReservationController;
