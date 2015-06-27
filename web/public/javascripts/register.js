var app = angular.module('register', []);
var url = 'https://127.0.0.1:8443/';

app.controller('registerController', function($scope, $document, $element) {
  
  // INIT MAP //
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


  // MAPS SEARCH //
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


  // CAPACITY FIELD //
  $scope.capacity = 1;
  $scope.plusCapacity = function() {
    if ($scope.capacity > 0) {
      $scope.capacity += 1;
    } else {
      $scope.capacity = 1;
    }
    if ($scope.capacity === $scope.reservable + 1) {
      $scope.reservable = $scope.capacity;
    }
  }
  $scope.minusCapacity = function() {
    if ($scope.capacity > 1) {
      $scope.capacity -= 1;
    } else {
      $scope.capacity = 1;
    }
    if ($scope.capacity === $scope.reservable - 1) {
      $scope.reservable = $scope.capacity;
    }
  }


  // RESERVABLE FIELD //
  $scope.reservable = 1;
  $scope.plusReserve = function() {
    if ($scope.reservable >= 0) {
      $scope.reservable += 1;
    } else {
      $scope.reservable = $scope.capacity;
    }
    if ($scope.reservable > $scope.capacity) {
      $scope.reservable = $scope.capacity;
    }
  }
  $scope.minusReserve = function() {
    if ($scope.reservable > 0) {
      $scope.reservable -= 1;
    } else {
      $scope.reservable = 0;
    }
  }

  // CALENDAR DATA //
  $scope.week = [
    'Sun',
    'Mon',
    'Tue',
    'Wed',
    'Thu',
    'Fri',
    'Sat'
  ];
  $scope.times = [
    '1',
    '2',
    '3',
    '4',
    '5',
    '6',
    '7',
    '8',
    '9',
    '10',
    '11',
    '12',
    '1',
    '2',
    '3',
    '4',
    '5',
    '6',
    '7',
    '8',
    '9',
    '10',
    '11'
  ];


  // INIT CALENDAR //
  var hours = 24;
  var days = 7;
  $scope.schedule = new Array(days);
  for (var i = 0; i < days; i++) {
    $scope.schedule[i] = new Array(hours);
  }


  // DRAG TO SELECT //
  var startCell = null;
  var dragging = false;

  function mouseUp(el) {
    startCell = null;
    dragging = false;
  }
  
  function mouseDown(el) {
    dragging = true;

    var cell = getCoords(el);
    if ($scope.schedule[cell.day][cell.hour] === 1) {
      $scope.schedule[cell.day][cell.hour] = null;
    } else {
      $scope.schedule[cell.day][cell.hour] = 1;
    }
    startCell = cell;
  }

  function mouseEnter(el) {
    if (!dragging) return;
    
    var cell = getCoords(el);
    if ($scope.schedule[startCell.day][startCell.hour] === 1) {
      $scope.schedule[cell.day][cell.hour] = 1;
    } else {
      $scope.schedule[cell.day][cell.hour] = null;
    }
  }
        
  function getCoords(cell) {
    var column = cell[0].cellIndex;
    var row = cell.parent()[0].rowIndex;
    return {
      day: column,
      hour: row
    };
  }
  
  function wrap(fn) {
    return function() {
      var el = angular.element(this);
      $scope.$apply(function() {
        fn(el);
      });
    }
  }
  
  $element.delegate('td', 'mousedown', wrap(mouseDown));
  $element.delegate('td', 'mouseenter', wrap(mouseEnter));
  $document.delegate('body', 'mouseup', wrap(mouseUp));


  // CALCULATE TIMES //
  $scope.scheduleTimes = function() {
    var offset = new Date().getTimezoneOffset()*60*1000;
    var date = Math.floor(new Date()/604800000)*604800000 - 345600000 + offset;

    $scope.startTimes = [];
    $scope.durations = [];
    var block = false;
    var duration;
    for (var day = 0; day < 7; day++) {
      for (var hour = 0; hour < 24; hour++) {
        if ($scope.schedule[day][hour] === 1) {
          if (block) {
            duration++;
          } else {
            $scope.startTimes.push(date + day*86400000 + hour*3600000);
            console.log(new Date(date + day*86400000 + hour*3600000));
            block = true;
            duration = 1;
          }
        } else {
          if (block) {
            $scope.durations.push(duration);
            block = false;
          }
        }
      }
      if (block) {
        $scope.durations.push(duration);
        block = false;
      }
    }
  }
});
