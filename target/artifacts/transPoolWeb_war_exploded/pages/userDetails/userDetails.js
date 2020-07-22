
var app1 = angular.module('userDetails', []);

app1.controller('userDetailsCtrl',[ '$scope', '$http', function($scope, $http) {

    $scope.totalMapsInTheSystem = [
        {mapUniqueName: "Asia", totalStations: "2", totalRoutes: "8" ,
            totalMapTrips:"4", totalTripsRequests:"12"}
    ];
    function init() {
        $scope.totalMapsInTheSystem = {};

        $scope.createUser = function()
        {
            $http({
                method: 'GET',
                url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/UserMapDetailsServlet',
                headers: {'Content-Type': 'application/json'},
                data:  $scope.user
            }).success(function (data)
            {
                $scope.status=data;
            });
        };
    }

    init();



} ]);

