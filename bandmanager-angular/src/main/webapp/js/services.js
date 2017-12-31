var bandManagerServices = angular.module('bandManagerServices', []);
var baseURL = "http://localhost:8080/pa165/rest";

bandManagerServices.factory('loggedUserFactory', ['$http',
    function ($http) {
        var urlUser = baseURL.concat("/user");
        var dataFactory = {};

        dataFactory.getPrincipal = function (success, error) {
            return $http.get(urlUser).then(success, error);
        };

        return dataFactory;
    }
]);