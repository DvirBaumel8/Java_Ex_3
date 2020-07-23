
var appMapDetails = angular.module('mapDetails', []);

appMapDetails.controller('mapDetailsCtrl',[ '$scope', '$http', function($scope, $http) {

    function initMapDetailsPage() {
        $scope.totalMapsInTheSystem = {};

        $scope.createUser = function()
        {
            console.log("I've been pressed!");
            $http.get('http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/UserMapDetailsServlet').then(
                function successCallback(response) {
                    $scope.totalMapsInTheSystem = response.data;
                },
                function errorCallback(response) {
                    console.log("Unable to perform get request");
                }
            );
        };
    }

    $scope.addNewSuggestTrip = function () {
        console.log("I've been pressed!");
        $http.get('http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/MapScreenServlet').then(
            function successCallback(response) {
                $scope.totalMapsInTheSystem = response.data;
            },
            function errorCallback(response) {
                console.log("Unable to perform get request");
            }
        );
    }

    $scope.addNewRequestTrip = function () {

    }


    $scope.highlightAndDisplayTripDetailsOnTheMap = function () {

    }

    $scope.displayDriverRating = function () {

    }

    $scope.displayAllPassengersDetailsPerTripRequest = function () {

    }


    $scope.redirectToMapPageApi = function () {
        console.log("I've been pressed!");
        $http.get('http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/MapScreenServlet').then(
            function successCallback(response) {
                $scope.totalMapsInTheSystem = response.data;
            },
            function errorCallback(response) {
                console.log("Unable to perform get request");
            }
        );
    }


    initMapDetailsPage();



} ]);

