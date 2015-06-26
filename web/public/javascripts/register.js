var app = angular.module('register', []);
var url = 'https://127.0.0.1:8443/';

app.controller('registerController', function($scope, $filter) {
  var search = document.getElementById('search');
  var auto_search = new google.maps.places.Autocomplete(search);

  var map;
  var infowindow;
  var markers = [];

  var searchName;
  search.addEventListener('blur', function() {
    if (searchName) {
      setTimeout(function() {
        search.value = searchName;
      }, 1);
    }
  });

  map = new google.maps.Map(document.getElementById('map'), {
    scrollwheel: false,
    mapTypeId: google.maps.MapTypeId.ROADMAP,
    disableDefaultUI: true
  });

  var US = {
    NorthEast: {
      lat: 49.38,
      lng: -66.94
    },
    SouthWest: {
      lat: 25.82,
      lng: -124.39
    }
  }
  var USbounds = new google.maps.LatLngBounds(
    new google.maps.LatLng(US.SouthWest.lat, US.SouthWest.lng),
    new google.maps.LatLng(US.NorthEast.lat, US.NorthEast.lng)
  );
  map.fitBounds(USbounds);

  google.maps.event.addListener(auto_search, 'place_changed', function() {
    var start = auto_search.getPlace();
    var startLoc = start.geometry.location;
    searchName = start.name;
    search.value = searchName;

    deleteMarkers();

    map.panTo(startLoc);
    map.setZoom(17);
    
    var marker = new google.maps.Marker({
      map: map,
      position: startLoc,
      icon: 'images/pin-green.png'
    });

    google.maps.event.addListener(marker, 'click', function() {
      infowindow.setContent('<strong>' + start.name + '</strong>');
      infowindow.open(this.map, this);
    });

    markers.push(marker);
    infowindow = new google.maps.InfoWindow();
  });

  var deleteMarkers = function() {
    for (var i = 0; i < markers.length; i++) {
      markers[i].setMap(null);
    }
    markers = [];
  }

  $scope.capacity = 1;
  $scope.plus = function() {
    if ($scope.capacity > 0) {
      $scope.capacity += 1;
    } else {
      $scope.capacity = 1;
    }
  }
  $scope.minus = function() {
    if ($scope.capacity > 1) {
      $scope.capacity -= 1;
    } else {
      $scope.capacity = 1;
    }
  }

  $scope.type = "HOUR";
});
