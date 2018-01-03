var bandManagerControllers = angular.module("bandManagerControllers", ['bandManagerServices']);

/* Tours list controller */

bandManagerControllers.controller('toursController', function ($scope, $rootScope, toursFactory, loggedUserFactory) {

    loggedUserFactory.getPrincipal(
        function (response) {
            var values = JSON.parse(response.data);
            $rootScope.principal_username = values.username;
            $rootScope.principal_id = values.id;
            $rootScope.role = values.role;
            $scope.role = $rootScope.role;
        },
        function (response) {
            alert("An error occurred when getting the logged user.");
        }
    );

    toursFactory.getAllBands(
        function (response) {
            $scope.tours = extractToursArray(response.data);
        },
        $rootScope.unsuccessfulResponse
    );

    var extractToursArray = function (responseData) {
        return responseData._embedded.tours;
    };

    $scope.isManagerRole = function (roleString) {
        return roleString === "ROLE_MANAGER";
    };
});

/* Tour details controller */

bandManagerControllers.controller('tourDetailsController', function ($scope, $routeParams, $rootScope, bandsFactory) {
    toursFactory.getBand(
        $routeParams.id,
        function (response) {
            $scope.tour = response.data;
        },
        $rootScope.unsuccessfulResponse
    );
});

/* Tour create controller */

bandManagerControllers.controller('createTourController', function ($location, $scope, $routeParams, $rootScope, bandsFactory, toursFactory, loggedUserFactory) {

    $scope.stringDatetime = "";

    loggedUserFactory.getPrincipal(
        function (response) {
            var values = JSON.parse(response.data);
            $rootScope.principal_username = values.username;
            $rootScope.principal_id = values.id;
            $rootScope.role = values.role;
        },
        function (response) {
            alert("An error occurred when getting the logged user.");
        }
    );

    bandsFactory.getByManager(
        $rootScope.principal_id,
        function (response) {
            $scope.availableBands = extractResultsArray(response.data);
        },
        $rootScope.unsuccessfulResponse
    );

    $scope.newTour = {
        name: "",
        cityName: "",
        bandId: undefined,
        managerId: $rootScope.principal_id,
        datetime: undefined
    };

    $scope.createNewTour = function (tour) {
        $scope.newTour.datetime = (new Date($scope.stringDatetime).getTime());

        toursFactory.createTour(
            tour,
            function () {
                $location.path("/tours");
            },
            $rootScope.unsuccessfulResponse
        );
    };

    $scope.isManagerRole = function (roleString) {
        return roleString === "ROLE_MANAGER";
    };

    var extractResultsArray = function (responseData) {
        return responseData._embedded.bands;
    };

});