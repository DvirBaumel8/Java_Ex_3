
var transPoolApp = angular.module('transPoolApp', []);



transPoolApp.controller('mapDetailsCtrl',[ '$scope', '$http', '$rootScope','$window',
    function($scope, $http, $rootScope, $window) {

    function initMapDetailsPage() {


        $scope.tripSuggestListPerMap = [
            { suggestID: '2', passengerNames: ' ', destinationStation: ' ', isMatched: 'yes', matchTrip:'a' },
            { suggestID: '1', passengerNames: ' ', destinationStation: ' ', isMatched: 'yes', matchTrip:'a' },
            { suggestID: '3', passengerNames: ' ', destinationStation: ' ', isMatched: 'yes', matchTrip:'a' }
        ];

        let userName = $window.sessionStorage.getItem("userNameGlobalVar");
        let mapName = $window.sessionStorage.getItem("userMapGlobalVar");

        console.log("I've been pressed!");
            $http({
                url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/MapScreenServlet',
                method: "GET",
                params: {mapName: mapName,
                    userName: userName}
            }).then(
                function successCallback(response) {
                    $scope.tripSuggestListPerMap = response.data.tripSuggestDtoList;
                    $scope.tripRequestListPerMap = response.data.tripRequestDtoList;
                    var graphDesc = response.data.htmlGraph;
                },
                function errorCallback(response) {
                    $window.alert("UnSuccess get request");
                }
            );


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
                    userSuggestPassengerCapacity: userSuggestPassengerCapacity,
                    userName: $window.sessionStorage.getItem("userNameGlobalVar"),
                    mapName: $window.sessionStorage.getItem("userMapGlobalVar")}
            }).then(
                function successCallback(response) {
                    $scope.tripSuggestListPerMap = response.data.tripSuggestDtoList;
                    let successMessage = "Success Adding Trip Suggest";
                    $window.alert(successMessage);
                },
                function errorCallback(response) {
                    $window.alert("UnSuccess Adding Trip Suggest - Please check your inputs");
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
                    userRequestDepartureOrArrival: userRequestDepartureOrArrival ,
                    userName: $window.sessionStorage.getItem("userNameGlobalVar"),
                    mapName: $window.sessionStorage.getItem("userMapGlobalVar")}
            }).then(
                function successCallback(response) {
                    $scope.tripRequestListPerMap = response.data.tripRequestDtoList;
                    let successMessage = "Success Adding Trip Request";
                    $window.alert(successMessage);
                },
                function errorCallback(response) {
                    $window.alert("UnSuccess Adding Trip Request - Please check your inputs");
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

