
var app1 = angular.module('userDetails', []);

app1.controller('userDetailsCtrl', function($scope) {

        $scope.totalMapsInTheSystem = [
            {mapUniqueName: "Asia", totalStations: "2", totalRoutes: "8" ,
                totalMapTrips:"4", totalTripsRequests:"12"}
        ];
});

var fileChooser = document.getElementById('fileChooser');

function parseTextAsXml(text) {
    var parser = new DOMParser(),
        xmlDom = parser.parseFromString(text, "text/xml");

    //now, extract items from xmlDom and assign to appropriate text input fields
}

function waitForTextReadComplete(reader) {
    reader.onloadend = function(event) {
        var text = event.target.result;

        parseTextAsXml(text);
    }
}

function handleFileSelection() {
    var file = fileChooser.files[0],
        reader = new FileReader();

    waitForTextReadComplete(reader);
    reader.readAsText(file);
}

fileChooser.addEventListener('change', handleFileSelection, false);