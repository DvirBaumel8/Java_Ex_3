
var transPoolApp = angular.module('transPoolApp', []);



transPoolApp.controller('mapDetailsCtrl',[ '$scope', '$http', '$rootScope','$window',
    function($scope, $http, $rootScope, $window) {

    function initMapDetailsPage() {

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
                    document.getElementById("graphDesc").innerHTML = response.data.htmlGraph;;
                },
                function errorCallback(response) {
                    $window.alert("UnSuccess get request");
                }
            );




    }


        $scope.addNewSuggestTrip = function () {
            let userSuggestName = $window.sessionStorage.getItem("userNameGlobalVar");;
            let userSuggestRoute = document.getElementsByName("userSuggestRoute")[0].value;
            let userSuggestDepartureDay = document.getElementsByName("userSuggestDepartureDay")[0].value;
            let userSuggestDepartureTime = document.getElementsByName("userSuggestDepartureTime")[0].value;
            let userSuggestScheduleInt = document.getElementsByName("userSuggestScheduleInt")[0].value;
            let userSuggestPPK = document.getElementsByName("userSuggestPPK")[0].value;
            let userSuggestPassengerCapacity = document.getElementsByName("userSuggestPassengerCapacity")[0].value;
            let userName = $window.sessionStorage.getItem("userNameGlobalVar");
            let mapName = $window.sessionStorage.getItem("userMapGlobalVar");

            console.log("I've been pressed!");
            $http({
                url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/AddNewTripSuggestServlet',
                method: "GET",
                params: {userSuggestName: userSuggestName,
                    userSuggestRoute: userSuggestRoute,
                    userSuggestDepartureDay: userSuggestDepartureDay,
                    userSuggestDepartureTime: userSuggestDepartureTime,
                    userSuggestScheduleInt: userSuggestScheduleInt,
                    userSuggestPPK: userSuggestPPK,
                    userSuggestPassengerCapacity: userSuggestPassengerCapacity,
                    userName: userName,
                    mapName: mapName}
            }).then(
                function successCallback(response) {
                    $scope.tripSuggestListPerMap = response.data;
                    let successMessage = "Success Adding Trip Suggest";
                    $window.alert(successMessage);
                },
                function errorCallback(response) {
                    $window.alert("UnSuccess Adding Trip Suggest - Please check your inputs");
                }
            );
        }

        $scope.addNewRequestTrip = function () {
            let userRequestName = $window.sessionStorage.getItem("userNameGlobalVar");;
            let userRequestSourceStation = document.getElementsByName("userRequestSourceStation")[0].value;
            let userRequestDestinationStation = document.getElementsByName("userRequestDestinationStation")[0].value;
            let userRequestTimeParam = document.getElementsByName("userRequestTimeParam")[0].value;
            let userRequestArrivalOrStart = document.getElementsByName("userRequestArrivalOrStart")[0].value;
            let userRequestDay = document.getElementsByName("userRequestDay")[0].value;

            let userName = $window.sessionStorage.getItem("userNameGlobalVar");
            let mapName = $window.sessionStorage.getItem("userMapGlobalVar");


            console.log("I've been pressed!");
            $http({
                url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/AddNewTripRequestServlet',
                method: "GET",
                params: {userRequestName: userRequestName,
                    userRequestSourceStation: userRequestSourceStation,
                    userRequestDestinationStation: userRequestDestinationStation,
                    userRequestTimeParam: userRequestTimeParam,
                    userRequestArrivalOrStart: userRequestArrivalOrStart ,
                    userRequestDay: userRequestDay,
                    userName: userName,
                    mapName: mapName}
            }).then(
                function successCallback(response) {
                    $scope.tripRequestListPerMap = response.data;
                    let successMessage = "Success Adding Trip Request";
                    $window.alert(successMessage);
                },
                function errorCallback(response) {
                    $window.alert("UnSuccess Adding Trip Request - Please check your inputs");
                }
            );
        }


    $scope.highlightTripDetailsForTripSuggest = function (suggestId) {
        let tripSuggestId = suggestId;
        let mapName = $window.sessionStorage.getItem("userMapGlobalVar");

        console.log("I've been pressed!");
        $http({
            url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/HighlightTripDetailsForTripSuggestServlet',
            method: "GET",
            params: {
                tripSuggestId: tripSuggestId,
                mapName: mapName
            }
        }).then(
            function successCallback(response) {
                document.getElementById("graphDesc").innerHTML  = response.data;
                let successMessage = "Success Highlight Trip Suggest Details";
                $window.alert(successMessage);
            },
            function errorCallback(response) {
                $window.alert("UnSuccess Highlight Trip Suggest Details");
            }
        );
    }

        $scope.highlightTripDetailsForTripRequest = function (requestId) {
            let tripRequestId = requestId;
            let mapName = $window.sessionStorage.getItem("userMapGlobalVar");

            console.log("I've been pressed!");
            $http({
                url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/HighlightTripDetailsForTripRequestServlet',
                method: "GET",
                params: {tripRequestId: tripRequestId,
                    mapName: mapName}
            }).then(
                function successCallback(response) {
                    document.getElementById("graphDesc").innerHTML = response.data;
                    let successMessage = "Success Highlight Trip Suggest Details";
                    $window.alert(successMessage);
                },
                function errorCallback(response) {
                    $window.alert("UnSuccess Highlight Trip Suggest Details");
                }
            );
        }

    $scope.displayDriverRating = function () {

    }

    $scope.matchingAndHighlightTripDetailsForTripRequest = function (numOfPotentSuggTrips) {
        let searchParams = new URLSearchParams(window.location.search)
        let tripRequestId = searchParams.get('myvar');
        let userName = $window.sessionStorage.getItem("userNameGlobalVar");
        let mapName = $window.sessionStorage.getItem("userMapGlobalVar");
        let numOfPotentialSuggestedTrips = document.getElementsByName("numOfPotentSuggTrips")[0].value;

        $scope.currTripRequestIdToMatch = tripRequestId;
            console.log("I've been pressed!");
            $http({
                url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/MatchingTripRequestServlet',
                method: "GET",
                params: {tripRequestId: tripRequestId,
                    userName: userName,
                    mapName: mapName,
                    numOfPotentialSuggestedTrips: numOfPotentialSuggestedTrips
                }
            }).then(
                function successCallback(response) {
                    $scope.potentialSuggestedTrips = response.data;
                    //window.open("potenSuggTripsWin.html","bfs","width=500,height=400,scrollbars=yes");
                    //$scope.highlightTripDetailsForTripRequest(requestId);
                    let successMessage = "Success Adding Trip Request";
                    $window.alert(successMessage);
                },
                function errorCallback(response) {
                    $window.alert("UnSuccess Adding Trip Request - Please check your inputs");
                }
            );

    }

    $scope.openMatchingWindow = function (requestId) {
        var matchWindow = window.open("potenSuggTripsWin.html?myvar=" + encodeURI(requestId),"bfs","width=800,height=600,scrollbars=yes");
        $scope.highlightTripDetailsForTripRequest(requestId);
    }

        $scope.matchAction = function (suggestIdPotentialTrip) {

            let userName = $window.sessionStorage.getItem("userNameGlobalVar");
            let mapName = $window.sessionStorage.getItem("userMapGlobalVar");
            let searchParams = new URLSearchParams(window.location.search)
            let tripRequestId = searchParams.get('myvar');

            console.log("I've been pressed!");
            $http({
                url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/MatchingActionServlet',
                method: "GET",
                params: {tripRequestId: tripRequestId,
                    suggestIdPotentialTrip: suggestIdPotentialTrip,
                    userName: userName,
                    mapName: mapName}
            }).then(
                function successCallback(response) {
                    $scope.isMatchSucceed = response.data;
                    let successMessage = "Match Succeed";
                    $window.alert(successMessage);
                },
                function errorCallback(response) {
                    $window.alert("UnSuccess Matching");
                }
            );

        }

        initMapDetailsPage();

} ]);

