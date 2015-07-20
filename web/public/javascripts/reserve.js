var app = angular.module('reserve', []);
var url = 'http://127.0.0.1:8080';


// SET CONSTANTS //
app.run(function($rootScope, $http, $window) {  
  $rootScope.loading = true;

  var lotId = $window.location.pathname.split('/')[3]; // CHANGE THIS AFTER TESTING
  $http.get(url + '/lotById?lotId=' + lotId)
  .success(function(lot) {
    $rootScope.lot = lot;
    
    // SET CALENDAR
    var hours = 24;
    var days = 7;
    $rootScope.schedule = new Array(days);
    for (var i = 0; i < days; i++) {
      $rootScope.schedule[i] = new Array(hours);
    }
    for (var i = 0; i < lot.calendar.length; i++) {
      var date = new Date(lot.calendar[i]);
      var today = new Date();
      if (date.getDay() === today.getDay() && date.getHours() > today.getHours()) {
        $rootScope.schedule[date.getDay()][date.getHours()] = 1;
      }
      if (date.getDay() > today.getDay()) {
        $rootScope.schedule[date.getDay()][date.getHours()] = 1;
      }
    }
  })
  .finally(function() {
    if ($rootScope.lot && $rootScope.reservations) {
      $rootScope.loading = false;
    }
  });
  $http.get(url + '/activeReservationsByLotId?lotId=' + lotId)
  .success(function(reservations) {
    $rootScope.reservations = reservations;
  })
  .finally(function() {
    if ($rootScope.lot && $rootScope.reservations) {
      $rootScope.loading = false;
    }
  });
});


app.controller('reserveController', function($scope, $rootScope, $http, $document, $element, $window) {

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

  $scope.reservation = new Array(days);
  for (var i = 0; i < days; i++) {
    $scope.reservation[i] = new Array(hours);
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
    if (!$scope.schedule[cell.day][cell.hour]) return;

    if ($scope.reservation[cell.day][cell.hour] === 1) {
      $scope.reservation[cell.day][cell.hour] = null;
    } else {
      $scope.reservation[cell.day][cell.hour] = 1;
    }
    startCell = cell;
  }

  function mouseEnter(el) {
    if (!dragging) return;
    if (!startCell) return;
    
    var cell = getCoords(el);
    if (!$scope.schedule[startCell.day][cell.hour]) return;

    if ($scope.reservation[startCell.day][startCell.hour] === 1) {
      for (var hour = Math.min(startCell.hour, cell.hour); hour < Math.max(startCell.hour, cell.hour) + 1; hour++) {
        $scope.reservation[startCell.day][hour] = 1;
      }
    } else {
      for (var hour = Math.min(startCell.hour, cell.hour); hour < Math.max(startCell.hour, cell.hour) + 1; hour++) {
        $scope.reservation[startCell.day][hour] = null;
      }
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
  var reservationTimes = function() {
    var offset = new Date().getTimezoneOffset()*60000;
    var sunday = Math.floor(new Date()/604800000)*604800000 + 259200000 + offset;

    var reservation = [];
    for (var day = 0; day < 7; day++) {
      for (var hour = 0; hour < 24; hour++) {
        if ($scope.reservation[day][hour] === 1) {
          reservation.push(sunday + day*86400000 + hour*3600000);
        }
      }
    }
    return reservation;
  }


  // MAKE RESERVATION //
  $scope.reserve = function() {
    var reservation = reservationTimes();

    // $http.post('/api/newLot', {
    //   type: 'residential',
    //   address: start.formatted_address,
    //   latitude: startLoc.lat(),
    //   longitude: startLoc.lng(),
    //   capacity: $scope.capacity,
    //   reserveMax: $scope.reservable,
    //   calendar: $scope.calendar,
    // })
    // .success(function(redirectURL) {
    //   $window.location = redirectURL;
    // })
    // .error(function(err) {
    //   $window.alert(err.error);
    // });
  }
});
