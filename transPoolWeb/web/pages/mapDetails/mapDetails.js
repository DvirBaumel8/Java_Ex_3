
var transPoolApp = angular.module('transPoolApp', []);



transPoolApp.controller('mapDetailsCtrl',[ '$scope', '$http', '$rootScope','$window',
    function($scope, $http, $rootScope, $window) {

    function initMapDetailsPage() {


        $scope.tripSuggestListPerMap = [
            { suggestID: '2', passengerNames: ' ', destinationStation: ' ', isMatched: 'yes', matchTrip:'a' },
            { suggestID: '1', passengerNames: ' ', destinationStation: ' ', isMatched: 'yes', matchTrip:'a' },
            { suggestID: '3', passengerNames: ' ', destinationStation: ' ', isMatched: 'yes', matchTrip:'a' }
        ];


    }


        $scope.addNewSuggestTrip = function () {
            let userSuggestName = document.getElementsByName("userSuggestName")[0].value;
            let userSuggestRoute = document.getElementsByName("userSuggestRoute")[0].value;
            let userSuggestDepartureTime = document.getElementsByName("userSuggestDepartureTime")[0].value;
            let userSuggestPPK = document.getElementsByName("userSuggestPPK")[0].value;
            let userSuggestPassengerCapacity = document.getElementsByName("userSuggestPassengerCapacity")[0].value;

            console.log("I've been pressed!");
            $http({
                url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/AddNewTripSuggestServlet',
                method: "GET",
                params: {userSuggestName: userSuggestName,
                    userSuggestRoute: userSuggestRoute,
                    userSuggestDepartureTime: userSuggestDepartureTime,
                    userSuggestPPK: userSuggestPPK,
                    userSuggestPassengerCapacity: userSuggestPassengerCapacity }
            }).then(
                function successCallback(response) {
                    //$scope.tripSuggestListPerMap = $scope.tripSuggestListPerMap.add(response.data);
                    $window.location.href = 'http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/mapDetails.html';
                },
                function errorCallback(response) {
                    console.log("Unable to perform get request");
                }
            );
        }

        $scope.addNewRequestTrip = function () {
            let userRequestName = document.getElementsByName("userRequestName")[0].value;
            let userRequestSourceStation = document.getElementsByName("userRequestSourceStation")[0].value;
            let userRequestDestinationStation = document.getElementsByName("userRequestDestinationStation")[0].value;
            let userRequestPPK = document.getElementsByName("userRequestPPK")[0].value;
            let userRequestDepartureOrArrival = document.getElementsByName("userRequestDepartureOrArrival")[0].value;

            console.log("I've been pressed!");
            $http({
                url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/AddNewTripRequestServlet',
                method: "GET",
                params: {userRequestName: userRequestName,
                    userRequestSourceStation: userRequestSourceStation,
                    userRequestDestinationStation: userRequestDestinationStation,
                    userRequestPPK: userRequestPPK,
                    userRequestDepartureOrArrival: userRequestDepartureOrArrival }
            }).then(
                function successCallback(response) {
                    //$scope.tripRequestListPerMap = $scope.tripRequestListPerMap.add(response.data);
                    $window.location.href = 'http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/mapDetails.html';
                },
                function errorCallback(response) {
                    console.log("Unable to perform get request");
                }
            );
        }


    $scope.highlightAndDisplayTripDetailsOnTheMap = function () {

    }

    $scope.displayDriverRating = function () {

    }

    $scope.displayAllPassengersDetailsPerTripRequest = function () {

    }


    initMapDetailsPage();



} ]);

