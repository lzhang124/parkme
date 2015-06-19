var app = angular.module('main', []);
var url = 'https://127.0.0.1:8443/';

app.controller('loginController', function($scope, $http) {
  $scope.showDimmer = false;
  $scope.LoginForm = false;
  $scope.SignupForm = false;

  $scope.showLogin = function() {
    $scope.showDimmer = true;
    $scope.LoginForm = true;
  }

  $scope.showSignup = function() {
    $scope.showDimmer = true;
    $scope.SignupForm = true;
  }

  $scope.hideDimmer = function() {
    $scope.showDimmer = false;
    $scope.LoginForm = false;
    $scope.SignupForm = false;
  }
});
