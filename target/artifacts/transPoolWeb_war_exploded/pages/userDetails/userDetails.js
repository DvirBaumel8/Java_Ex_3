
var app1 = angular.module('userDetails', []);

app1.controller('userDetailsCtrl',[ '$scope', '$http', function($scope, $http) {

    function init() {
        $scope.totalMapsInTheSystem = {};

        $scope.createUser = function()
        {
            $http({
                method: 'GET',
                url: 'http://localhost:8080/transPoolWeb_war_exploded/pages/userDetails/UserMapDetailsServlet',
                headers: {'Content-Type': 'application/json'},
                data:  $scope.totalMapsInTheSystem
            }).success(function (data)
            {
                $scope.status=data;
            });
             var k = $scope.totalMapsInTheSystem;
            var x = 4;
            var j = 0;
        };
    }

    init();



} ]);

