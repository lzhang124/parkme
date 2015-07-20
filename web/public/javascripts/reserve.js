var app = angular.module('reserve', []);
var url = 'http://127.0.0.1:8080';


// SET CONSTANTS //
app.run(function($rootScope, $http, $window) {  
  $rootScope.loading = true;
  var hours = 24;
  var days = 7;
  $rootScope.schedule = new Array(days);
  for (var i = 0; i < days; i++) {
    $rootScope.schedule[i] = new Array(hours);
  }
  $rootScope.reservation = $rootScope.schedule.slice(0);
  $rootScope.reserved = $rootScope.schedule.slice(0);
  var lotId = $window.location.pathname.split('/')[3]; // CHANGE THIS AFTER TESTING
  var today = new Date();

  $http.get(url + '/lotById?lotId=' + lotId)
  .success(function(lot) {
    $rootScope.lot = lot;
    
    // SET CALENDAR
    for (var i = 0; i < lot.calendar.length; i++) {
      var date = new Date(lot.calendar[i]);
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

    // CHECK FOR CONFLICTS
    var offset = new Date().getTimezoneOffset()*60000;
    var sunday = Math.floor((new Date().getTime() + 345600000)/604800000)*604800000 - 345600000 + offset;
    for (var i = 0; i < reservations.length; i++) {
      var reservation = reservations[i];
      if (reservation.start > today && reservation.start < 
    }
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
    var sunday = Math.floor((new Date().getTime() + 345600000)/604800000)*604800000 - 345600000 + offset;

    var startTimes = [];
    var durations = [];
    var block = false;
    var duration;
    for (var day = 0; day < 7; day++) {
      for (var hour = 0; hour < 24; hour++) {
        if ($scope.reservation[day][hour] === 1) {
          if (block) {
            duration++;
          } else {
            startTimes.push(sunday + day*86400000 + hour*3600000);
            block = true;
            duration = 1;
          }
        } else {
          if (block) {
            durations.push(duration);
            block = false;
          }
        }
      }
      if (block) {
        durations.push(duration);
        block = false;
      }
    }
    return [startTimes, durations];
  }


  // MAKE RESERVATION //
  $scope.reserve = function() {
    var reservations = reservationTimes();
    var startTimes = reservations[0];
    var durations = reservations[1];

    $http.post('/api/reserve', {
      lotId: $scope.lot.id,
      startTimes: startTimes,
      durations: durations
    })
    .success(function(redirectURL) {
      $window.location = redirectURL;
    })
    .error(function(err) {
      $window.alert(err.error);
    });
  }
});
