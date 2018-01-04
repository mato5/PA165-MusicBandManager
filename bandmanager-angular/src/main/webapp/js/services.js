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
            return $http.post(baseURL.concat("/managers/create_tour"), tour).then(success, error);
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
            return $http.post(baseURL.concat("/managers/create_band"), band).then(success, error);
        };

        bandsDataFactory.getBandmates = function (id, success, error) {
            return $http.get(baseURL.concat("/members/bandmates/" + id), band).then(success, error);
        };

        return bandsDataFactory;
    }
]);

/* Manager factory */

bandManagerServices.factory('managersFactory', ['$http',
    function ($http) {

        var managersDataFactory = {};

        var managerResourceNameUrl = baseURL.concat("/managers");
        var newManagerResourceId = managerResourceNameUrl.concat("/{id}");

        managersDataFactory.getAllManagers = function (success, error) {
            return $http.get(managerResourceNameUrl).then(success, error);
        };

        managersDataFactory.getManager = function (id, success, error) {
            return $http.get(managerResourceNameUrl + "/" + id).then(success, error);
        };

        managersDataFactory.createManager = function (manager, success, error) {
            return $http.post(managerResourceNameUrl + "/create", manager).then(success, error);
        };

        return bandsDataFactory;
    }
]);

/* Member factory */

bandManagerServices.factory('membersFactory', ['$http',
    function ($http) {

        var membersDataFactory = {};

        var memberResourceUrl = baseURL.concat("/members");
        var newMemberResourceId = memberResourceUrl.concat("/{id}");

        membersFactory.getAllMembers = function (success, error) {
            return $http.get(memberResourceUrl).then(success, error);
        };

        membersFactory.getMember = function (id, success, error) {
            return $http.get(memberResourceUrl + "/" + id).then(success, error);
        };

        membersDataFactory.createMember = function (member, success, error) {
            return $http.post(memberResourceUrl + "/create", member).then(success, error);
        };

        return bandsDataFactory;
    }
]);

/* Songs factory */

bandManagerServices.factory('songsFactory', ['$http',
    function ($http) {

        var songsDataFactory = {};

        var songResourceUrl = baseURL.concat("/songs");
        var newSongResourceId = songResourceUrl.concat("/{id}");

        songsDataFactory.getAllSongs = function (success, error) {
            return $http.get(songResourceUrl).then(success, error);
        };

        songsDataFactory.getSong = function (id, success, error) {
            return $http.get(songResourceUrl + "/" + id).then(success, error);
        };

        songsDataFactory.getSongByBandId = function (bandId, success, error) {
            return $http.get(songResourceUrl + "/by_band_id" + "/" + bandId).then(success, error);
        };

        songsDataFactory.getSongByName = function (searchName, success, error) {
            return $http.get(
                songResourceUrl + "/by_name",
                {params: {name: searchName}}
            ).then(success, error);
        };

        songsDataFactory.createSong = function (member, success, error) {
            return $http.post(songResourceUrl + "/create", member).then(success, error);
        };

        return songsDataFactory;
    }
])