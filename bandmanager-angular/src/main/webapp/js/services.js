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


/* Tours factory */

bandManagerServices.factory('toursFactory', ['$http',
    function ($http) {

        var toursDataFactory = {};

        var tourResourceNameUrl = baseURL.concat("/tours");
        var newTourResourceId = tourResourceNameUrl.concat("/{id}");

        toursDataFactory.getAllBands = function (success, error) {
            return $http.get(tourResourceNameUrl).then(success, error);
        };

        toursDataFactory.getByManager = function (managerId, success, error) {
            return $http.get(tourResourceNameUrl + "/by_manager_id" + "/" + managerId).then(success, error);
        };

        toursDataFactory.getByBand = function (bandId, success, error) {
            return $http.get(tourResourceNameUrl + "/by_band_id" + "/" + bandId).then(success, error);
        };

        toursDataFactory.getBand = function (id, success, error) {
            return $http.get(tourResourceNameUrl + "/" + id).then(success, error);
        };

        toursDataFactory.deleteTour = function (id, success, error) {
            return $http.delete(tourResourceNameUrl + "/" + id).then(success, error);
        };

        toursDataFactory.createTour = function (tour, success, error) {
            return $http.post(tourResourceNameUrl + "/create", tour).then(success, error);
        };

        return toursDataFactory;
    }
]);

/* Bands factory */

bandManagerServices.factory('bandsFactory', ['$http',
    function ($http) {

        var bandsDataFactory = {};

        var bandResourceNameUrl = baseURL.concat("/bands");
        var newBandResourceId = bandResourceNameUrl.concat("/{id}");

        bandsDataFactory.getAllBands = function (success, error) {
            return $http.get(bandResourceNameUrl).then(success, error);
        };

        bandsDataFactory.getByManager = function (managerId, success, error) {
            return $http.get(bandResourceNameUrl + "/by_manager_id" + "/" + managerId).then(success, error);
        };
        
        bandsDataFactory.getBand = function (id, success, error) {
            return $http.get(bandResourceNameUrl + "/" + id).then(success, error);
        };

        bandsDataFactory.deleteBand = function (id, success, error) {
            return $http.delete(bandResourceNameUrl + "/" + id).then(success, error);
        };

        bandsDataFactory.createBand = function (band, success, error) {
            return $http.post(bandResourceNameUrl + "/create", band).then(success, error);
        };

        return bandsDataFactory;
    }
]);