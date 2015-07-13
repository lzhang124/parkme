var http = require('http');
var querystring = require('querystring');
var LotController = {};

var createNewLot = function(accountId, name, type, address, latitude, longitude, capacity, reserveMax, calendar, fn) {
  var data = querystring.stringify({
    accountId: accountId,
    name: name,
    type: type,
    address: address,
    latitude: latitude,
    longitude: longitude,
    capacity: capacity,
    reserveMax: reserveMax,
    calendar: calendar,
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

  var req = http.request(options, function(res) {
    res.setEncoding('utf8');
    var data = '';

    res.on('data', function(chunk) {
      data += chunk
    });

    res.on('end', function() {
      if (data === '') {
        return fn(null, null);
      }
      var user = JSON.parse(data);
      return fn(null, user);
    });
  });
  req.on('error', function(e) {
    console.log('Error: ', e);
    return fn(e, null);
  });

  req.write(data);
  req.end();
}

LotController.newLot = function(req, res) {
  var accountId = req.user.id;
  var name;
  var type = req.body.type;
  if (type === 'residential') {
    name = req.user.firstName + "'s House";
  } else {
    name = 'PARKING LOT'
  }
  var address = req.body.address;
  var latitude = req.body.latitude;
  var longitude = req.body.longitude;
  var capacity = req.body.capacity;
  var reserveMax = req.body.reserveMax;
  var calendar = req.body.calendar;
  createNewLot(accountId, name, type, address, latitude, longitude, capacity, reserveMax, calendar, function(err, user) {
    if (err) {
      console.log('Error in creating lot: ' + err);
      throw err;
    }
    if (user === null) {
      console.log('This lot already exists.');
      res.status(409).send({ "error": "This lot already exists." });
    } else {
      req.login(user, function(error) {
        if (error) {
          console.log(error);
        }
      });
      console.log('New lot created!');
      res.send('/q9xwGoXLGQ');
    }
  });
}

module.exports = LotController;
