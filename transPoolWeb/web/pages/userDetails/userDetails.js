
var appUserDetails = angular.module('userDetails', []);

appUserDetails.controller('userDetailsCtrl',[ '$scope', '$http', '$location',
    function($scope, $http, $location) {

    function init() {
        $scope.totalMapsInTheSystem = {};

        $scope.createUser = function()
        {
            console.log("I've been pressed!");
            $http.get('http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/UserMapDetailsServlet').then(
                function successCallback(response) {
                    $scope.totalMapsInTheSystem = response.data;
                },
                function errorCallback(response) {
                    console.log("Unable to perform get request");
                }
            );
        };
    }



    $scope.redirectToMapPageApi = function () {
        console.log("I've been pressed!");
        $http.get('http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/MapScreenServlet').then(
            function successCallback(response) {
                $scope.totalMapsInTheSystem = response.data;
                var link = $location.protocol() + $location.host() + '/transPoolWeb_war_exploded/pages/mapDetails/mapDetail.html';
                $location.path(link);
            },
            function errorCallback(response) {
                console.log("Unable to perform get request");
            }
        );
    }


    init();



} ]);

