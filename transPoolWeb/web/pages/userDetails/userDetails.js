
var appUserDetails = angular.module('userDetails', []);

appUserDetails.controller('userDetailsCtrl',[ '$scope', '$http', '$location', '$window',
    function($scope, $http, $location, $window) {

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



    $scope.redirectToMapPageApi = function (mapName) {
        console.log("I've been pressed!");
        $http({
            url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/MapScreenServlet',
            method: "GET",
            params: {mapName: mapName}
        }).then(
            function successCallback(response) {
                $scope.totalMapsInTheSystem = response.data;
                $window.location.href = 'http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/mapDetails.html';
            },
            function errorCallback(response) {
                console.log("Unable to perform get request");
            }
        );
    }

        $scope.redirectToFileUploadedApi = function () {
            var mapUploadName = document.getElementsByName("mapUploadName")[0].value;
            //let userName = document.getElementsByName("userLoginName")[0].value;
            let userName = 'x';
            console.log("I've been pressed!");
            $http({
                url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/FileUpload',
                method: "POST",
                params: {mapUploadName: mapUploadName,
                        userName: userName}
            }).then(
                function successCallback(response) {
                    //$scope.totalMapsInTheSystem = response.data;
                    $window.location.href = 'http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/userDetails.html';
                },
                function errorCallback(response) {
                    console.log("Unable to perform get request");
                }
            );
        }

    init();



} ]);

