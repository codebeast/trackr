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
            controller: "MapController",
            templateUrl: 'templates/map.html'
        };
    });
    mapDirective.$inject = [];

    var mapController = controllers.controller("MapController", ["$scope", function ($scope) {
        console.log("creating map controller");
        var local_icons = {
            default_icon: {},
            active_icon: {
                iconUrl: 'https://cdn2.iconfinder.com/data/icons/function_icon_set/circle_green.png',
                iconSize: [20, 20]
            },
            idle_icon: {
                iconUrl: 'https://cdn2.iconfinder.com/data/icons/function_icon_set/circle_orange.png',
                iconSize: [20, 20]
            },
            inactive_icon: {
                iconUrl: 'https://cdn2.iconfinder.com/data/icons/function_icon_set/circle_red.png',
                iconSize: [20, 20]
            }
        };

        angular.extend($scope, {
            ukCenter: {
                lat: 53.490395,
                lng: -2.252197,
                zoom: 10
            },
            markers: {
                activeMarker: {
                    lat: 53.490395,
                    lng: -2.252197,
                    message: "SJ56 NFG",
                    draggable: false,
                    icon: local_icons.active_icon
                },
                idleMarker: {
                    lat: 53.110395,
                    lng: -2.212197,
                    message: "PR12 FGV",
                    draggable: false,
                    icon: local_icons.idle_icon
                },
                inactiveMarker1: {
                    lat: 53.690395,
                    lng: -2.222197,
                    message: "AJ52 BVG",
                    draggable: false,
                    icon: local_icons.inactive_icon
                },
                inactiveMarker2: {
                    lat: 53.790395,
                    lng: -2.272197,
                    message: "PO16 OOP",
                    draggable: false,
                    icon: local_icons.inactive_icon
                }
            }
        });
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