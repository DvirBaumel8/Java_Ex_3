
var app1 = angular.module('userDetails', []);

app1.controller('userDetailsCtrl', function($scope) {

        $scope.totalMapsInTheSystem = [
            {userName:"Dvir", mapName: "Asia", totalStations: "2", totalRoutes: "8" ,
                totalMapTrips:"4", totalTripsRequests:"12"}
        ];
});

var openFile = function(event) {
    var input = event.target;

    var reader = new FileReader();
    reader.onload = function(){
        var text = reader.result;
        var node = document.getElementById('output');
        node.innerText = text;
        console.log(reader.result.substring(0, 200));
    };
    reader.readAsText(input.files[0]);
};