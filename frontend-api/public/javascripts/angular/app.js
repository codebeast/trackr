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


    var mapController = controllers.controller("MapController", function ($scope, $http) {
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

        $scope.markers = {};

        $http({
            method: 'GET',
            url: 'http://localhost:3000/device/all'
        }).then(function successCallback(response) {
            var cars = response.data;
            updateMarkers(cars);
        }, function errorCallback(response) {
            console.log(response);
        });

        function updateMarkers(cars) {
            $scope.markers = {};
            for (var index in cars) {
                var car = cars[index];
                console.log(car);
                $scope.markers[car.imei] = {
                    lat: car.gpsElement.latitude,
                    lng: car.gpsElement.longitude,
                    message: car.imei,
                    draggable: false,
                    icon: local_icons.active_icon
                }
            }
        }
        angular.extend($scope, {
            ukCenter: {
                lat: 53.490395,
                lng: -2.252197,
                zoom: 10
            }
        });
    });
    mapController.$inject = ["$scope", "$http"];

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