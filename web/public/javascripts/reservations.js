var app = angular.module('reservations', []);
var url = 'http://52.25.5.25:8080';


// SET CONSTANTS //
app.run(function($rootScope, $http, $window) {  
  $rootScope.loading = true;
  
  // INIT ARRAYS
  var hours = 24;
  var days = 7;
  $rootScope.reservations = new Array(days);
  for (var i = 0; i < days; i++) {
    $rootScope.reservations[i] = new Array(hours);
  }

  // GET CURRENT USER
  $http.get('/api/currentUser')
  .success(function(user) {

    // GET RESERVATIONS
    $http.get(url + '/activeReservationsByAccountId?accountId=' + user.id)
    .success(function(reservations) {
      var offset = new Date().getTimezoneOffset()*60000;
      var today = new Date();
      var nextSunday = Math.floor((new Date().getTime() + 345600000)/604800000)*604800000 + 259200000 + offset;
      for (var i = 0; i < reservations.length; i++) {
        var reservation = reservations[i];
        if (reservation.start > today && reservation.end < nextSunday) {
          var day = new Date(reservation.start).getDay();
          var hour = new Date(reservation.start).getHours();
          for (var j = 0; j < reservation.duration; j++) {
            $rootScope.reservations[day][hour + j] = 1;
          }
        }
      }
    })
    .finally(function() {
      $rootScope.loading = false;
    });  
  });
});


app.controller('reservationsController', function($scope, $http, $document, $element, $window) {

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

  // HOVER FOR INFORMATION
  function mouseEnter(el) {    
    var cell = getCoords(el);
    if (!$scope.reservations[cell.day][cell.hour]) return;

    // console.log($scope.reservations[cell.day][cell.hour].lot);
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
  
  $element.delegate('td', 'mouseenter', wrap(mouseEnter));

});
