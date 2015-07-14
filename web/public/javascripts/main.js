var app = angular.module('main', []);
var url = 'http://127.0.0.1:8080';


// SET CONSTANTS //
app.run(function($rootScope) {
  $rootScope.loading = true;
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


app.controller('loginController', function($scope, $rootScope, $http, focus) {

  // GET CURRENT USER
  $http.get('/api/currentUser')
  .success(function(user) {
    $scope.user = user;
  })
  .finally(function() {
    $rootScope.loading = false;
  });

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


app.controller('searchController', function($scope, $rootScope, $http, $timeout, focus) {

  $scope.home = function() {
    $scope.showResults = false;
    $rootScope.showLogin = true;
    $rootScope.showContent = true;
    focus('search-main');
  }

  // INIT PLACES AUTOCOMPLETE //
  var search_main = document.getElementById('search-main');
  var auto_search_main = new google.maps.places.Autocomplete(search_main);

  var search = document.getElementById('search');
  var auto_search = new google.maps.places.Autocomplete(search);

  var map;
  var infowindow;
  var currentMarker = null;
  $scope.markers = [];


  // INIT MAP //
  google.maps.event.addListener(auto_search_main, 'place_changed', function() {
    var start = auto_search_main.getPlace();
    if (start.hasOwnProperty('geometry')) {
      $timeout(function() {
        $scope.showResults = true;
        $rootScope.showLogin = false;
        $rootScope.showContent = false;
        search.value = start.name;
        search_main.blur();
        search_main.value = "";

        if (!map) {
          map = new google.maps.Map(document.getElementById('map'), {
            mapTypeId: google.maps.MapTypeId.ROADMAP,
            disableDefaultUI: true
          });
        }

        $http.post('/api/search', {
          latitude: start.geometry.location.lat(),
          longitude: start.geometry.location.lng()
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
      search.value = start.name;
      
      $http.post('/api/search', {
        latitude: start.geometry.location.lat(),
        longitude: start.geometry.location.lng()
      });
      
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
      }
    });
    google.maps.event.addListener(startMarker, 'click', function() {
      if (infowindow.anchor !== this) {
        infowindow.setContent('<div class="infoWindow">Your Destination</div>');
        infowindow.open(this.map, this);
        $timeout(function() {
          if (currentMarker !== null) {
            currentMarker.lot.selected = false;
          }
          currentMarker = null;
        });
      } else {
        infowindow.close();
      }
    });
    $scope.markers.push(startMarker);

    $http.get(url + '/searchNear?latitude=' + startLoc.lat() + 
                              '&longitude=' + startLoc.lng())
    .success(function(data) {
      $scope.lots = data;
      for (var i = 0; i < data.length; i++) {
        lot = data[i];
        createMarker(lot);
      }
    });

    infowindow = new google.maps.InfoWindow();

    google.maps.event.addListener(infowindow, 'closeclick', function() {
      $timeout(function() {
        if (currentMarker !== null) {
          currentMarker.lot.selected = false;
        }
        currentMarker = null;
      });
    });

    // Style the infowindow
    google.maps.event.addListener(infowindow, 'domready', function() {
      var iwOuter = $('.gm-style-iw');
      var iw = iwOuter.prev();
      var close = iwOuter.next();

      // Remove the defaults
      iw.children(':nth-child(1)').css({'display' : 'none'});
      iw.children(':nth-child(2)').css({'display' : 'none'});
      iw.children(':nth-child(4)').css({'display' : 'none'});

      // Move the close button
      close.css({
        right: '15px',
        top: '45px',
      });

      // Style the tail
      iw.children(':nth-child(3)').children(':nth-child(1)').css({
        height: '13px',
        width: '13px',
        top: '11px',
        left: '-2px',
        'box-shadow': 'none',
        'z-index' : '1',
      });
      iw.children(':nth-child(3)').children(':nth-child(1)').children().css({
        height: '10px',
        width: '10px',
        left: '0',
        'border-left': '3px solid rgba(0,72,105,1)',
        'border-bottom': '3px solid rgba(0,72,105,1)',
        '-webkit-transform': 'skewX(45deg)',
        'box-shadow': 'none',
      });
      iw.children(':nth-child(3)').children(':nth-child(2)').css({
        height: '13px',
        width: '13px',
        top: '11px',
        left: '11px',
        'box-shadow': 'none',
        'z-index' : '1',
      });
      iw.children(':nth-child(3)').children(':nth-child(2)').children().css({
        height: '10px',
        width: '10px',
        'border-right': '3px solid rgba(0,72,105,1)',
        'border-bottom': '3px solid rgba(0,72,105,1)',
        '-webkit-transform': 'skewX(-45deg)',
        'box-shadow': 'none',
      });
    });
  }

  var createMarker = function(lot) {
    if (lot.available === true) {
      var image = 'images/pin-green.png';
    } else  {
      var image = 'images/pin-red.png';
    }

    var marker = new google.maps.Marker({
      map: map,
      position: new google.maps.LatLng(lot.location[1], lot.location[0]),
      icon: image,
    });

    google.maps.event.addListener(marker, 'click', function() {
      if (!this.lot.selected) {
        infowindow.setContent('<div class="infoWindow">' + lot.name + ' <span id="type">' + lot.type + '</div>');
        infowindow.open(this.map, this);
        $timeout(function() {
          if (currentMarker !== null) {
            currentMarker.lot.selected = false;
          }
          lot.selected = true;
          currentMarker = marker;
        });
      } else {
        infowindow.close();
        currentMarker = null;
        $timeout(function() {
          lot.selected = false;
        });
      }
    });

    lot.selected = false;
    marker.lot = lot;
    $scope.markers.push(marker);
  }

  var deleteMarkers = function() {
    for (var i = 0; i < $scope.markers.length; i++) {
      $scope.markers[i].setMap(null);
    }
    $scope.markers = [];
  }

  $scope.openInfoWindow = function(marker){
    google.maps.event.trigger(marker, 'click');
  }
});
