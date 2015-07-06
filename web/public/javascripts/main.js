var app = angular.module('main', []);
var url = 'http://52.25.5.25:8080/';


// SET CONSTANTS //
app.run(function($rootScope) {
  $rootScope.showContent = true;
  $rootScope.showLogin = true;
  $rootScope.showResults = false;
});


// AUTOFOCUS //
app.factory('focus', function($timeout) {
  return function(id) {
    $timeout(function() {
      var element = document.getElementById(id);
      if(element)
        element.focus();
    });
  }
});


app.controller('loginController', function($scope, $rootScope, focus) {
  
  // LOGIN //
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

  // INIT PLACES AUTOCOMPLETE //
  var search_main = document.getElementById('search-main');
  var auto_search_main = new google.maps.places.Autocomplete(search_main);

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


  // INIT MAP //
  google.maps.event.addListener(auto_search_main, 'place_changed', function() {
    var start = auto_search_main.getPlace();
    if (start.hasOwnProperty('geometry')) {
      $timeout(function() {
        $rootScope.showResults = true;
        $rootScope.showLogin = false;
        $rootScope.showContent = false;
        search.value = start.name;

        map = new google.maps.Map(document.getElementById('map'), {
          mapTypeId: google.maps.MapTypeId.ROADMAP,
          disableDefaultUI: true
        });

        searchNear(start);
      });
    } else {
      // REPLACE THIS
      console.log("that's not a real place");
    }

  });


  // NEW SEARCH //
  google.maps.event.addListener(auto_search, 'place_changed', function() {
    var start = auto_search.getPlace();
    if (start.hasOwnProperty('geometry')) {
      searchName = start.name;
      search.value = searchName;
      
      deleteMarkers();
      searchNear(start);
    } else {
      // REPLACE THIS
      console.log("that's not a real place");
    }
  });


  // SEARCH //
  var searchNear = function(start) {
    var startLoc = start.geometry.location;

    map.panTo(startLoc);
    map.setZoom(16);

    var startMarker = new google.maps.Marker({
      map: map,
      position: startLoc,
      icon: {
        url: 'images/star.png',
        size: new google.maps.Size(27, 26),
        origin: new google.maps.Point(0,0),
        anchor: new google.maps.Point(13.5, 13)
      },
      draggable: true
    });
    google.maps.event.addListener(startMarker, 'click', function() {
      infowindow.setContent('<div class="infoWindow">Your Destination</div>');
      infowindow.open(this.map, this);
    });
    markers.push(startMarker);

    $http.get(url + 'searchNear?latitude=' + startLoc.lat() + 
                               '&longitude=' + startLoc.lng())
    .success(function(data) {
      $scope.lots = data;
      for (var i = 0; i < data.length; i++) {
        place = data[i];
        createMarker(place);
      }
    });

    infowindow = new google.maps.InfoWindow();
  }

  var createMarker = function(place) {
    if (place.available === true) {
      var image = 'images/pin-green.png';
    } else  {
      var image = 'images/pin-red.png';
    }

    var marker = new google.maps.Marker({
      map: map,
      position: new google.maps.LatLng(place.location[1], place.location[0]),
      icon: image,
    });
    marker.selected = false;

    google.maps.event.addListener(marker, 'click', function() {
      infowindow.setContent('<div class="infoWindow">' + place.name + '</div>');
      infowindow.open(this.map, this);
      marker.selected = true;
    });

    place.marker = marker;
    markers.push(marker);
  }

  var deleteMarkers = function() {
    for (var i = 0; i < markers.length; i++) {
      markers[i].setMap(null);
    }
    markers = [];
  }

  $scope.openInfoWindow = function(marker){
    google.maps.event.trigger(marker, 'click');
  }
});
