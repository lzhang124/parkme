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
  $scope.LoginForm = false;
  $scope.SignupForm = false;

  $scope.showLogin = function() {
    $scope.showDimmer = true;
    $scope.LoginForm = true;
  };

  $scope.showSignup = function() {
    $scope.showDimmer = true;
    $scope.SignupForm = true;
  };

  $scope.hideDimmer = function() {
    $scope.showDimmer = false;
    $scope.LoginForm = false;
    $scope.SignupForm = false;
  };

  $scope.login = function() {
    $http.post('url', {
      username: $scope.username,
      password: $scope.password,
    }).success(function(){
      console.log(arguments)
    });
  };

  $scope.signup = function() {
    $http.post('url', {
      firstName: $scope.firstName,
      lastName: $scope.lastName,
      username: $scope.username,
      password: $scope.password,
      phone: $scope.phone,
      email: $scope.email,
    }).success(function(){
      console.log(arguments)
    });
  };
});
