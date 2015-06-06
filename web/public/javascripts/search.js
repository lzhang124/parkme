var app = angular.module('search', []);

app.controller('searchController', function($scope, $http) {

  $scope.showResults = function() {
    var search = this.search;
    $http.get('https://127.0.0.1:8443/lotsByZipcode?zipcode='+search).success(function(){console.log('hello')})
  };
});
