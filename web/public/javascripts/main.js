var app = angular.module('main', []);

app.controller('searchController', function($scope, $http) {
  $scope.showSearch = true;

  $scope.showResults = function() {
    var search = this.search;
    $http.get('https://127.0.0.1:8443/lotsByZipcode?zipcode='+search).
    success(function(response){
      $scope.lots = response;
    });
    $scope.showSearch = false;
  };

  $scope.hideResults = function() {
    $scope.showSearch = true;
  };

  $scope.close = function(event) {
    if (event.keyCode === 27) {
      $scope.hideResults();
    };
  };
});

app.controller('loginController', function($scope, $http) {
  $scope.showDimmer = false;

  $scope.showLogin = function() {
    $scope.showDimmer = true;
  };

  $scope.hideLogin = function() {
    $scope.showDimmer = false;
  };

  $scope.login = function() {
    $http.post('url', {
      username: $scope.username,
      password: $scope.password,
    }).success(function(){
      console.log(arguments)
    });
  };
});
