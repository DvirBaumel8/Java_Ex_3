


var transPoolApp = angular.module('transPoolApp', []);


transPoolApp.controller('signUpCtrl',[ '$scope', '$http', '$rootScope','$window',
    function($scope, $http, $rootScope, $window) {

    function initSign() {





    }

    initSign();

    $scope.getUserSignInDetailsAndRedirect = function () {
        $window.sessionStorage.setItem("userNameGlobalVar",$scope.userLoginNameModel);

        console.log("I've been pressed!");
        $http({
            url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/signup/LoginServlet',
            method: "GET",
            params: {userName: $scope.userLoginNameModel,
                userType: $scope.userTypeModel
            }
        }).then(
            function successCallback(response) {
                var respMessage = response.data.toString();
                respMessage = respMessage.slice(1,-1)
                let successMessage = "The Sign In Was Successful";
                if(respMessage.trim() == successMessage.trim()) {
                    $window.alert(respMessage);
                    $window.location.href = 'http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/userDetails.html';
                }
                else {
                    $window.alert(respMessage);
                    $window.location.href = 'http://localhost:8080/transPoolWeb_war_exploded/pages/signup/signup.html';
                }
            },
            function errorCallback(response) {
                $window.alert(respMessage);
                $window.location.href = 'http://localhost:8080/transPoolWeb_war_exploded/pages/signup/signup.html';
            }
        );
    }


} ]);



