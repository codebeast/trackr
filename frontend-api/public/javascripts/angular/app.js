var entrypoint = function () {
    console.log("starting application");
    var main = angular.module("main", ["controllers", "services", "directives", "leaflet-directive"]);
    var controllers = angular.module("controllers", []);
    var services = angular.module("services", []);
    var directives = angular.module("directives", []);
    console.log("created controller, service, directives");

    main.run(function ($rootScope, $timeout) {
        $rootScope.$on('$viewContentLoaded', function () {
            $timeout(function () {
                componentHandler.upgradeAllRegistered();
            })
        })
    });


    //MAPS
    var mapDirective = directives.directive("mapDirective", function () {
        console.log("create directive");
        return {
            templateUrl: 'templates/map.html'
        };
    });
    mapDirective.$inject = [];

    controllers.controller("MapController", ["$scope", function ($scope) {
    }]);

    //VEHICLES
    var vehicleDirective = directives.directive("vehicleDirective", function () {
        console.log("create directive");
        return {
            templateUrl: 'templates/vehicles.html'
        };
    });
    vehicleDirective.$inject = [];


    //LOGIN
    var loginDirective = directives.directive("loginDirective", function () {
        console.log("create login directive");
        return {
            templateUrl: 'templates/login_form.html'
        };
    });
    loginDirective.$inject = [];


}();