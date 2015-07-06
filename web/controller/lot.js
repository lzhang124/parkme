var http = require('http');
var querystring = require('querystring');

var url = 'http://52.25.5.25:8080/';

var createNewLot = function(accountId, name, type, address, latitude, longitude, capacity, reserveMax, fn) {
  var data = querystring.stringify({
    accountId: accountId,
    name: name,
    type: type,
    address: address,
    latitude: latitude,
    longitude: longitude,
    capacity: capacity,
    reserveMax: reserveMax
  });

  var options = {
    host: '52.25.5.25',
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
      var lot = JSON.parse(data);
      return fn(null, lot);
    });
  });
  req.on('error', function(e) {
    console.log('Error: ', e);
    return fn(e, null);
  });

  req.write(data);
  req.end();
};

var newLot = function(req, res) {
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

  createNewLot(accountId, name, type, address, latitude, longitude, capacity, reserveMax, function(err, lot) {
    if (err) {
      console.log('Error in creating lot: ' + err);
      throw err;
    };
    console.log('New lot ' + lot.name);
    res.send('/q9xwGoXLGQ');
  });
}

module.exports = newLot;
