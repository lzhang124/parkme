var http = require('http');
var querystring = require('querystring');
var ReservationController = {};

var url = 'http://127.0.0.1:8080';

ReservationController.newReservation = function(req, res) {
  var type = req.body.type;
  if (type === 'residential') {
    var name = req.user.firstName + "'s House";
  } else {
    var name = 'PARKING LOT'
  }
  var lot = querystring.stringify({
    accountId: req.user.id,
    name: name,
    type: type,
    address: req.body.address,
    latitude: req.body.latitude,
    longitude: req.body.longitude,
    capacity: req.body.capacity,
    reserveMax: req.body.reserveMax,
    calendar: req.body.calendar
  });
  var options = {
    host: '127.0.0.1',
    port: 8080,
    path: '/newLot',
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
        console.log('This lot already exists.');
        res.status(409).send({ 'error': 'This lot already exists.' });
        return;
      }
      var user = JSON.parse(data);
      req.login(user, function(error) {
        if (error) {
          console.log(error);
        }
      });
      console.log('New lot created!');
      res.send('/q9xwGoXLGQ');
    });
  });
  request.on('error', function(err) {
    console.log('Error in creating lot: ' + err);
    throw err;
  });

  request.write(lot);
  request.end();
}

module.exports = ReservationController;
