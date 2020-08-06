
var transPoolApp = angular.module('transPoolApp', []);

transPoolApp.controller('userDetailsCtrl',[ '$scope', '$http', '$location', '$rootScope','$window',
    function($scope, $http, $location, $rootScope, $window) {

        setInterval(
    function init() {
        $scope.totalMapsInTheSystem = {};
        $scope.userNameInUserPage = $window.sessionStorage.getItem("userNameGlobalVar");
        document.getElementById('userName').value = $scope.userNameInUserPage;
        let notification;

        $scope.createUser = function() {
            console.log("I've been pressed!");

            $http({
                url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/UserMapDetailsServlet',
                method: "GET",
                params: {userName: $scope.userNameInUserPage}
            }).then(
                function successCallback(response) {
                    $scope.totalMapsInTheSystem = response.data.mapsTableElementsInfo;
                    $scope.userTransactionsHistoryTable = response.data.userAccountTransactions;
                    $scope.userLoadingAccountBalance = response.data.userBalanceDto;

                },
                function errorCallback(response) {
                    console.log("Unable to perform get request");
                }
            );
        }
    } , 2000);


    $scope.redirectToMapPageApi = function (mapName) {
        let userName = $scope.userNameInUserPage;
        let errors;
        console.log("I've been pressed!");
        $http({
            url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/MapScreenServlet',
            method: "GET",
            params: {mapName: mapName,
            userName: userName}
        }).then(
            function successCallback(response) {
                //$scope.totalMapsInTheSystem = response.data;

                errors = response.data.errors;

                if(errors != undefined) {
                    $window.alert("errors:" + errors);
                    errors = undefined;
                }
                else {
                    let successMessage = "Success Move Map Screen Page";
                    $window.sessionStorage.setItem("userMapGlobalVar",mapName);
                    $window.alert(successMessage);
                    $window.location.href = 'http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/mapDetails.html';
                }
            },
            function errorCallback(response) {
                $window.alert("UnSuccess get request");
            }
        );
    }

        $scope.redirectToFileUploadedApi = function () {
            var mapUploadName = document.getElementsByName("mapUploadName")[0].value;
            let userName = $scope.userNameInUserPage;
            let errors;

            console.log("I've been pressed!");
            $http({
                url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/FileUpload',
                method: "POST",
                params: {mapUploadName: mapUploadName,
                        userName: userName}
            }).then(
                function successCallback(response) {
                    errors = response.data;

                    if(errors != undefined) {
                        $window.alert("errors:" + errors);
                        errors = undefined;
                    }
                },
                function errorCallback(response) {
                    console.log("Unable to perform get request");
                }
            );
            $window.location.href = 'http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/userDetails.html';
        }


        $scope.loadAccountBalanceAction = function () {
            let amountToLoad = document.getElementsByName("userLoadingAccountBalance")[0].value;
            let userName = $scope.userNameInUserPage;
            let errors;
            console.log("I've been pressed!");
            $http({
                url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/LoadAccountBalanceServlet',
                method: "GET",
                params: {amountToLoad: amountToLoad,
                    userName: userName}
            }).then(
                function successCallback(response) {
                    $scope.userAccountBalance = response.data.newBalance;

                    errors = response.data.error;

                    if(errors != undefined) {
                        $window.alert("errors:" + errors);
                        errors = undefined;
                    }
                    else {
                        $window.location.href = 'http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/userDetails.html';
                        let successMessage = "Success Adding Cash";
                        $window.alert(successMessage);
                    }
                },
                function errorCallback(response) {
                    $window.alert("UnSuccess Adding Cash To User");
                    $window.location.href = 'http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/userDetails.html';
                }
            );



            $http({
                url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/UserTransactionsHistoryServlet',
                method: "GET",
                params: {amountToLoad: amountToLoad,
                    userName: userName}
            }).then(
                function successCallback(response) {
                    errors = response.data;

                    if(errors != undefined) {
                        $window.alert("errors:" + errors);
                        errors = undefined;
                    }
                    else {
                        $window.location.href = 'http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/userDetails.html';
                    }
                },
                function errorCallback(response) {
                    console.log("Unable to perform get request");
                }
            );
        }

} ]);

