var app = angular.module('search', []);

app.controller('searchController', function($scope, $http) {
  $scope.showSearch = true;

  $scope.showResults = function() {
    var search = this.search;
    $http.get('https://127.0.0.1:8443/lotsByZipcode?zipcode='+search)
      .success(function(response){
        $scope.lots = response;
      });
    $scope.showSearch = false;
  };

  $scope.hideResults = function() {
    $scope.showSearch = true;
    this.search = "";
  };

  $scope.close = function(event) {
    if (event.keyCode === 27) {
      $scope.hideResults();
    };
  };
});
