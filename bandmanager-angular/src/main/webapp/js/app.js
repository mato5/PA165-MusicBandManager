var module = angular.module('bandManager', ['ngRoute', 'bandManagerControllers', 'bandManagerServices']);

module.config(function ($routeProvider) {

    $routeProvider
        .when('/', {
            templateUrl: 'partials/home.html'
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
            console.log(values);

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

    $rootScope.testCreate = function () {
        var body = "{\n" +
            "\t\"name\": \"New REST Test Song #3\",\n" +
            "\t\"duration\": 100,\n" +
            "\t\"bandId\" : 1\n" +
            "}";


        $http({
            method: 'POST',
            url: 'http://localhost:8080/pa165/rest/songs/create',
            data: body,
            timeout: 4000
        }).then(function (success) {
            $window.alert("OK");
        }, function (error) {
            $window.alert(error);
        });

    };

});