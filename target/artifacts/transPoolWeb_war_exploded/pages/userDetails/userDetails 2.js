
var app1 = angular.module('userDetails', []);

app1.controller('userDetailsCtrl',[ '$scope', '$http', function($scope, $http) {

    function init() {
        $scope.totalMapsInTheSystem = {};

        $scope.createUser = function()
        {
            console.log("I've been pressed!");
            $http.get('http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/UserMapDetailsServlet').then(
                function successCallback(response) {
                    $scope.response = response.data;
                    $scope.totalMapsInTheSystem = response.data;
                },
                function errorCallback(response) {
                    console.log("Unable to perform get request");
                }
            );
             var k = $scope.totalMapsInTheSystem;
            var x = 4;
            var j = 0;
        };
    }



    $scope.redirectToMapPageApi = function () {

    }

    init();



} ]);

