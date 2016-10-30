var entrypoint = function () {
    console.log("starting application");
    var main = angular.module("main", ["controllers", "services", "directives", "leaflet-directive"]);
    var controllers = angular.module("controllers", []);
    var services = angular.module("services", []);
    var directives = angular.module("directives", []);
    console.log("created controller, service, directives");



    var mapDirective = directives.directive("mapDirective", function() {
        console.log("create directive");
        return {
            templateUrl: 'templates/map.html'
        };
    });
    mapDirective.$inject = [];

    var vehicleDirective = directives.directive("vehicleDirective", function() {
        console.log("create directive");
        return {
            templateUrl: 'templates/vehicles.html'
        };
    });
    mapDirective.$inject = [];

    controllers.controller("MapController", [ "$scope", function($scope) {
    }]);




}();