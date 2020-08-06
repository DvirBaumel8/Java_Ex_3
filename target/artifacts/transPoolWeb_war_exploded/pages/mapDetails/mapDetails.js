
var transPoolApp = angular.module('transPoolApp', []);



transPoolApp.controller('mapDetailsCtrl',[ '$scope', '$http', '$rootScope','$window',
    function($scope, $http, $rootScope, $window) {

        initMapDetailsPage();
    //setInterval(
    function initMapDetailsPage() {
        let errors;
        let notification;

        let userName = $window.sessionStorage.getItem("userNameGlobalVar");
        let mapName = $window.sessionStorage.getItem("userMapGlobalVar");
         let userType = $window.sessionStorage.getItem("userTypeGlobalVar");
        $scope.userGlobalType = userType;
        if(userType.trim() == "suggestPassenger") {
            $scope.isSuggestPassenger = true;
        }
        else {
            $scope.isSuggestPassenger = false;
        }
        if (userType.trim() == "requestPassenger") {
            $scope.isRequestPassenger = true;
        }
        else {
            $scope.isRequestPassenger = false;
        }

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
                    document.getElementById("graphDesc").innerHTML = response.data.htmlGraph;
                },
                function errorCallback(response) {
                    $window.alert("UnSuccess get request");
                }
            );
    }
    //, 300);


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

            let errors;

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
                    $scope.tripSuggestListPerMap = response.data.tripSuggestDtoList;
                    $scope.errors = response.data.errors;
                    $scope.notification = response.data.notification;

                        if(errors == undefined) {
                            let successMessage = "Success Adding Trip Suggest";
                            $window.alert(successMessage);
                        }

                    else {
                            $window.alert("errors:" + errors);
                            errors = undefined;
                    }

                },
                function errorCallback(response) {
                    $window.alert("UnSuccess Adding Trip Suggest - Please check your inputs");
                }
            );
        }


        $scope.addNewRequestTrip = function () {
            let errors;
            let notification;

            let userRequestName = $window.sessionStorage.getItem("userNameGlobalVar");;
            let userRequestSourceStation = document.getElementsByName("userRequestSourceStation")[0].value;
            let userRequestDestinationStation = document.getElementsByName("userRequestDestinationStation")[0].value;
            let userRequestTimeParam = document.getElementsByName("userRequestTimeParam")[0].value;
            let userRequestArrivalOrStart = document.getElementsByName("userRequestArrivalOrStart")[0].value;
            let userRequestDay = document.getElementsByName("userRequestDay")[0].value;

            let userName = $window.sessionStorage.getItem("userNameGlobalVar");
            let mapName = $window.sessionStorage.getItem("userMapGlobalVar");

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
                    $scope.tripRequestListPerMap = response.data.tripRequestDtoList;
                    errors = response.data.errors;

                    if(errors == undefined) {
                        let successMessage = "Success Adding Trip Suggest";
                        $window.alert(successMessage);
                    }
                    else {
                        $window.alert("errors:" + errors);
                        errors = undefined;
                    }
                },
                function errorCallback(response) {
                    $window.alert("UnSuccess Adding Trip Request - Please check your inputs");
                }
            );
        }


    $scope.highlightTripDetailsForTripSuggest = function (suggestId) {
        let errors;

        let tripSuggestId = suggestId;
        let mapName = $window.sessionStorage.getItem("userMapGlobalVar");

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
            let errors;

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


    $scope.getPotentialSuggestedTripsForTripRequest = function () {

        let errors;

        let searchParams = new URLSearchParams(window.location.search)
        let tripRequestId = searchParams.get('myvar');
        let userName = $window.sessionStorage.getItem("userNameGlobalVar");
        let mapName = $window.sessionStorage.getItem("userMapGlobalVar");
        let numOfPotentialSuggestedTrips = document.getElementsByName("numOfPotentSuggTrips")[0].value;

        $scope.currTripRequestIdToMatch = tripRequestId;
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
                    $scope.potentialSuggestedTrips = response.data.potentialRoadTripDto;
                    errors = response.data.errors;
                    //window.open("potenSuggTripsWin.html","bfs","width=500,height=400,scrollbars=yes");
                    if(errors == undefined) {
                        let successMessage = "Success Adding Trip Request";
                        $window.alert(successMessage);
                    }
                    else {
                        $window.alert("errors:" + errors);
                        errors = undefined;
                    }
                },
                function errorCallback(response) {
                    $window.alert("UnSuccess Adding Trip Request - Please check your inputs");
                }
            );

    }
        var matchWindow;
    $scope.openMatchingWindow = function (requestId) {
        matchWindow = window.open("potenSuggTripsWin.html?myvar=" + encodeURI(requestId),"bfs","width=800,height=600,scrollbars=yes");
        $scope.highlightTripDetailsForTripRequest(requestId);
    }

        $scope.matchAction = function (suggestIdPotentialTrip) {
            let errors;
            let notification;

            let userName = $window.sessionStorage.getItem("userNameGlobalVar");
            let mapName = $window.sessionStorage.getItem("userMapGlobalVar");
            let searchParams = new URLSearchParams(window.location.search)
            let tripRequestId = searchParams.get('myvar');

            $http({
                url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/MatchingActionServlet',
                method: "GET",
                params: {tripRequestId: tripRequestId,
                    suggestIdPotentialTrip: suggestIdPotentialTrip,
                    userName: userName,
                    mapName: mapName}
            }).then(
                function successCallback(response) {
                    errors =  response.data;

                    if(errors == undefined) {
                        $scope.isMatchSucceed = true;
                        let successMessage = "Match Succeed";
                        $window.alert(successMessage);
                        window.close();
                    }
                    else {
                        $scope.isMatchSucceed = false;
                        $window.alert("errors:" + errors);
                        errors = undefined;
                    }
                },
                function errorCallback(response) {
                    $window.alert("UnSuccess Matching");
                }
            );

        }

        $scope.openRatingWindow = function (requestId, isMatched) {
        if(isMatched) {
            matchWindow = window.open("ratingWin.html?myvar=" + encodeURI(requestId),"bfs","width=800,height=600,scrollbars=yes");
            $scope.showDriversWhichNotRank(requestId);
        }
        else {
            $window.alert("This is request is not Match");
        }
        }


        $scope.showDriversWhichNotRank = function(requestId) {

            let errors;
            let notification;

            $http({
                url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/ShowUnRankDriversServlet',
                method: "GET",
                params: {tripRequestId: requestId}
            }).then(
                function successCallback(response) {
                    $scope.driversWhichNotRank = response.data.driversWhichNotRank;
                    errors = response.data.errors;

                    if(errors != undefined) {
                        $window.alert("errors:" + errors);
                        errors = undefined;
                    }
                },
                function errorCallback(response) {
                    $window.alert("UnSuccess Matching");
                }
            );

        }

        $scope.ratingActionByRequestPass = function () {
            let errors;
            let notification;

            let searchParams = new URLSearchParams(window.location.search)
            let tripRequestId = searchParams.get('myvar');

            let chosenDriver = document.getElementsByName("chosenDriver")[0].value;
            let ratingNumber = document.getElementsByName("ratingNumber")[0].value;
            let ratingNotes = document.getElementsByName("ratingNotes")[0].value;

            $http({
                url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/RatingDriversServlet',
                method: "GET",
                params: { tripRequestId: tripRequestId,
                    chosenDriver:chosenDriver,
                    ratingNumber:ratingNumber,
                    ratingNotes:ratingNotes }
            }).then(
                function successCallback(response) {
                    $scope.isRatingSucceed = response.data.isRatingSucceed;
                    let successMessage = "Rating Succeed";
                    errors = response.data.errors;


                    if(errors != undefined) {
                        $window.alert("errors:" + errors);
                        errors = undefined;
                    }
                    else {
                        let successMessage = "Rating Succeed";
                        $window.alert(successMessage);
                    }
                },
                function errorCallback(response) {
                    $window.alert("Rating Not Succeed");
                }
            );

        }

} ]);

