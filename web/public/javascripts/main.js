var app = angular.module('main', []);
var url = 'https://127.0.0.1:8443/';

app.run(function($rootScope) {
  $rootScope.showContent = true;
  $rootScope.showLogin = true;
  $rootScope.showResults = false;
});

app.factory('focus', function($timeout) {
  return function(id) {
    $timeout(function() {
      var element = document.getElementById(id);
      if(element)
        element.focus();
    });
  }
});

app.controller('loginController', function($scope, $rootScope, $http, focus) {
  $scope.login = function() {
    $scope.LoginForm = true;
    $rootScope.showContent = false;
    focus('login');
  }

  $scope.signup = function() {
    $scope.SignupForm = true;
    $rootScope.showContent = false;
    focus('signup');
  }

  $scope.hideDimmer = function() {
    $scope.LoginForm = false;
    $scope.SignupForm = false;
    $rootScope.showContent = true;
    focus('search-main');
  }
});

app.controller('searchController', function($scope, $rootScope, $http, $timeout) {
  var search_main = document.getElementById('search-main');
  var auto_search_main = new google.maps.places.Autocomplete(search_main);

  var search = document.getElementById('search');
  var auto_search = new google.maps.places.Autocomplete(search);

  var map;
  var infowindow;
  var markers = [];

  google.maps.event.addListener(auto_search_main, 'place_changed', function() {
    $timeout(function() {
      $rootScope.showResults = true;
      $rootScope.showLogin = false;
      $rootScope.showContent = false;
      search.value = search_main.value;

      map = new google.maps.Map(document.getElementById('map'), {
        mapTypeId: google.maps.MapTypeId.ROADMAP
      });

      searchNear(auto_search_main.getPlace());
    });
  });

  google.maps.event.addListener(auto_search, 'place_changed', function() {
    deleteMarkers();
    searchNear(auto_search.getPlace());
  });

  var searchNear = function(start) {
    var startLoc = start.geometry.location;

    map.panTo(startLoc);
    map.setZoom(16);
    createMarker(startLoc.lat(), startLoc.lng(), 'Your Destination', null);

    $http.get(url + 'searchNear?latitude=' + startLoc.lat() + 
                               '&longitude=' + startLoc.lng()).
    success(function(response) {
      $scope.lots = response;
      for (var i = 0; i < response.length; i++) {
        place = response[i];
        createMarker(place.location[1], place.location[0], place.name, place.available);
      }
    })

    infowindow = new google.maps.InfoWindow();
    places = new google.maps.places.PlacesService(map);
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
      infowindow.setContent('<strong>' + name + '</strong>');
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
});
