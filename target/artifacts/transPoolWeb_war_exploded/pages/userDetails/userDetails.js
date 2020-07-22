
var app1 = angular.module('userDetails', []);

app1.controller('userDetailsCtrl',[ '$scope', '$http', function($scope, $http) {

    $scope.totalMapsInTheSystem = [
        {mapUniqueName: "Asia", totalStations: "2", totalRoutes: "8" ,
            totalMapTrips:"4", totalTripsRequests:"12"}
    ];

} ]);

