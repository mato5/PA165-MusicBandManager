var module = angular.module('bandManager', ['ngRoute', 'bandManagerControllers', 'bandManagerServices', '720kb.datepicker']);

module.config(function ($routeProvider) {

    $routeProvider
        .when('/tours', {
            templateUrl: 'partials/tours.html',
            controller: 'toursController'
        })
        .when('/tours/new_tour', {
            templateUrl: 'partials/new_tour.html',
            controller: 'createTourController'
        })
        .when('/tours/:id', {
            templateUrl: 'partials/tour.html',
            controller: 'tourDetailsController'
        })
        .when('/forbidden', {
            templateUrl: 'partials/forbidden.html'
        })
        .otherwise({redirectTo: '/'});

});

module.run(function ($rootScope, $location, $window, $http, loggedUserFactory) {

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

    $rootScope.unsuccessfulResponse = function (response) {
        if (response.status === 403) {
            $rootScope.page = $location.path();
            $location.path("/forbidden");
        } else if (response.status === 401) {
            $window.location.href = "login.html";
        }
    };
});