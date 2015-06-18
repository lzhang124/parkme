var app = angular.module('results', []);
var url = 'https://127.0.0.1:8443/';

app.controller('resultsController', function($http) {

});





var map;
var infowindow;

function initialize() {

  var search = document.getElementById('search');
  var auto_search = new google.maps.places.Autocomplete(search);

  map = new google.maps.Map(document.getElementById('map'), {
    mapTypeId: google.maps.MapTypeId.ROADMAP,
    zoom: 17
  });

  google.maps.event.addListener(auto_search, 'place_changed', function() {
    var start = auto_search.getPlace();
    var startLoc = start.geometry.location;

    map.panTo(start);
    map.setZoom(17);

    createMarker(start);
    
    https.get(url + 'searchNear?latitude=' + startLoc.lat() +
                              'longitude=' + startLoc.lng(), function(res) {
      var data = '';

      res.on('data', function(chunk) {
        data += chunk;
      });

      res.on('end', function() {
        if (data !== '') {
          var lots = JSON.parse(data);

          for (var i = 0; i < lots.length; i++) {
            createMarker(lots[i]);
          }
        }
      });
    }).on('error', function(e) {
      console.log('Error: ', e);
    });

    // var request = {
    //   location: start,
    //   radius: 400,
    //   types: ['parking']
    // };

    infowindow = new google.maps.InfoWindow();
    var service = new google.maps.places.PlacesService(map);
    // createDestinationMarker(start);
    // service.nearbySearch(request, callback);
  });
}

// function createDestinationMarker(location) {
//   var greenPin = 'images/pin-red.png';
//   var marker = new google.maps.Marker({
//     map: map,
//     position: location,
//     icon: greenPin,
//   });

//   google.maps.event.addListener(marker, 'click', function() {
//     infowindow.setContent('Your destination');
//     infowindow.open(map, this);
//   });
// }

// function callback(results, status) {
//   if (status == google.maps.places.PlacesServiceStatus.OK) {
//     var bounds = map.getBounds();
//     for (var i = 0; i < results.length; i++) {
//       createMarker(results[i], bounds);
//     }
//     map.fitBounds(bounds);
//   }
// }

function createMarker(place) {
  var placeLoc = place.geometry.location;

  if (place.available === true) {
    var pin = 'images/pin-green.png';
  } else {
    var pin = 'images/pin-red.png';
  }

  var marker = new google.maps.Marker({
    map: map,
    position: placeLoc,
    icon: pin
  });

  google.maps.event.addListener(marker, 'click', function() {
    infowindow.setContent(place.name);
    infowindow.open(map, this);
  });
}

google.maps.event.addDomListener(window, 'load', initialize);
