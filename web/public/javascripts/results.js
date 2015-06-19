var app = angular.module('results', []);
var url = 'https://127.0.0.1:8443/';

app.service('Map', function($http) {

  var map;
  var infowindow;
  var markers = [];

  this.init = function() {
    var search = document.getElementById('search');
    var auto_search = new google.maps.places.Autocomplete(search);

    map = new google.maps.Map(document.getElementById('map'), {
      mapTypeId: google.maps.MapTypeId.ROADMAP,
      disableDefaultUI: true
    });

    google.maps.event.addListener(auto_search, 'place_changed', function() {
      deleteMarkers();

      var start = auto_search.getPlace();
      var startLoc = start.geometry.location;

      map.panTo(startLoc);
      map.setZoom(17);

      createMarker(startLoc.lat(), startLoc.lng(), start.name, null);
      searchNear(startLoc);

      infowindow = new google.maps.InfoWindow();
      places = new google.maps.places.PlacesService(map);
    });
  }

  var createMarker = function(lat, lng, name, available) {

    if (available === true) {
      var pin = 'images/pin-green.png';
    } else if (available === false) {
      var pin = 'images/pin-red.png';
    } else {
      var pin = 'images/pin-green.png';
    }

    var marker = new google.maps.Marker({
      map: map,
      position: new google.maps.LatLng(lat, lng),
      icon: pin
    });

    google.maps.event.addListener(marker, 'click', function() {
      infowindow.setContent(name);
      infowindow.open(this.map, this);
    });

    markers.push(marker);
  }

  var deleteMarkers = function() {
    for (var i = 0; i < markers.length; i++) {
      markers[i].setMap(null);
    }
    markers = [];
  }

  var searchNear = function(location) {
    $http.get(url + 'searchNear?longitude=' + location.lng() + 
                               '&latitude=' + location.lat()).
    success(function(response) {
      for (var i = 0; i < response.length; i++) {
        place = response[i];
        createMarker(place.location[1], place.location[0], place.name, place.available);
      }
    })
  }
});

app.controller('resultsController', function(Map) {
  Map.init();
});
