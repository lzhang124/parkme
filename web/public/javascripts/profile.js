var app = angular.module('profile', []);
var url = 'http://52.25.5.25:8080/';

app.controller('profileController', function($scope, $http) {
  
  // GET CURRENT USER
  $scope.loading = true;
  $http.get('/currentUser')
  .success(function(user) {
    $scope.user = user;
    $http.get(url + '/getLots?accountId=' + $scope.user.id)
    .success(function(lots) {
        $scope.lots = lots;
        console.log(lots);
    });
  })
  .finally(function() {
    $scope.loading = false;
  });
});
