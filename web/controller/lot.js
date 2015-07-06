var http = require('http');
var querystring = require('querystring');

var createNewLot = function(accountId, name, type, address, latitude, longitude, capacity, reserveMax, startTimes, durations, fn) {
  var data = querystring.stringify({
    accountId: accountId,
    name: name,
    type: type,
    address: address,
    latitude: latitude,
    longitude: longitude,
    capacity: capacity,
    reserveMax: reserveMax,
    startTimes: startTimes,
    durations: durations
  });

  var options = {
    // host: '52.25.5.25',
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
  var startTimes = req.body.startTimes;
  var durations = req.body.durations;

  createNewLot(accountId, name, type, address, latitude, longitude, capacity, reserveMax, startTimes, durations, function(err, lot) {
    if (err) {
      console.log('Error in creating lot: ' + err);
      throw err;
    };
    console.log('New lot ' + lot.name);
    res.send('/q9xwGoXLGQ');
  });
}

module.exports = newLot;
