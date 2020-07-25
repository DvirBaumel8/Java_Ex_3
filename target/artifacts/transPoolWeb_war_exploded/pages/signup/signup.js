
var transPoolApp = angular.module('transPoolApp', []);


transPoolApp.controller('signUpCtrl',[ '$scope', '$http', '$rootScope','$window',
    function($scope, $http, $rootScope, $window) {

    function initSign() {





    }

    initSign();

    $scope.getUserSignInDetails = function () {
        $window.sessionStorage.setItem("userNameGlobalVar",$scope.userLoginNameModel);
    }


} ]);



