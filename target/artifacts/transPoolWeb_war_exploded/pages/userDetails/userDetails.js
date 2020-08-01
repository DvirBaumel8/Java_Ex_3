
var transPoolApp = angular.module('transPoolApp', []);

transPoolApp.controller('userDetailsCtrl',[ '$scope', '$http', '$location', '$rootScope','$window',
    function($scope, $http, $location, $rootScope, $window) {


    function init() {
        $scope.totalMapsInTheSystem = {};
        $scope.userNameInUserPage = $window.sessionStorage.getItem("userNameGlobalVar");


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
        let userName = $scope.userNameInUserPage;
        console.log("I've been pressed!");
        $http({
            url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/MapScreenServlet',
            method: "GET",
            params: {mapName: mapName,
            userName: userName}
        }).then(
            function successCallback(response) {
                //$scope.totalMapsInTheSystem = response.data;
                $window.location.href = 'http://localhost:8080/transPoolWeb_war_exploded/pages/mapDetails/mapDetails.html';
            },
            function errorCallback(response) {
                console.log("Unable to perform get request");
            }
        );
    }

        $scope.redirectToFileUploadedApi = function () {
            var mapUploadName = document.getElementsByName("mapUploadName")[0].value;
            let userName = $scope.userNameInUserPage;
            console.log("I've been pressed!");
            $http({
                url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/FileUpload',
                method: "POST",
                params: {mapUploadName: mapUploadName,
                        userName: userName}
            }).then(
                function successCallback(response) {
                    //$scope.totalMapsInTheSystem.add(response.data);
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
            console.log("I've been pressed!");
            $http({
                url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/LoadAccountBalanceServlet',
                method: "GET",
                params: {amountToLoad: amountToLoad,
                    userName: userName}
            }).then(
                function successCallback(response) {
                    //$scope.userAccountBalance = response.data;
                    $window.location.href = 'http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/userDetails.html';
                },
                function errorCallback(response) {
                    console.log("Unable to perform get request");
                }
            );

            $http({
                url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/UserTransactionsHistoryServlet',
                method: "GET",
                params: {amountToLoad: amountToLoad,
                    userName: userName}
            }).then(
                function successCallback(response) {
                    //$scope.userTransactionsHistoryTable = response.data;
                    $window.location.href = 'http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/userDetails.html';
                },
                function errorCallback(response) {
                    console.log("Unable to perform get request");
                }
            );
        }

    init();

} ]);

