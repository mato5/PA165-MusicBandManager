var module = angular.module('bandManager', ['ngRoute', 'bandManagerControllers', 'bandManagerServices', '720kb.datepicker']);

module.config(function ($routeProvider) {


    $routeProvider
        .when('/', {
            templateUrl: 'partials/home.html'
        })
        .when('/bands', {
            templateUrl: 'partials/bands.html',
            controller: 'bandsController'
        })
        .when('/bands/new_band', {
            templateUrl: 'partials/new_band.html',
            controller: 'createBandController'
        })
        .when('/bands/:id', {
            templateUrl: 'partials/band.html',
            controller: 'bandDetailsController'
        })
        .when('/bands/:id/members', {
            templateUrl: 'partials/members.html',
            controller: 'bandMembersController'
        })
        .when('/bands/:id/albums', {
            templateUrl: 'partials/albums.html',
            controller: 'bandAlbumsController'
        })
        .when('/bands/:id/tours', {
            templateUrl: 'partials/tours.html',
            controller: 'bandToursController'
        })
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
        .when('/songs', {
            templateUrl: 'partials/songs.html',
            controller: 'songsController'
        })
        .when('/songs/new_song', {
            templateUrl: 'partials/new_song.html',
            controller: 'createSongController'
        })
        .when('/songs/:id', {
            templateUrl: 'partials/song.html',
            controller: 'songDetailsController'
        })
        .when('/memberinvites', {
            templateUrl: 'partials/members_bandinvites.html',
            controller: 'memberInvitesController'
        })
        .when('/managerinvites', {
            templateUrl: 'partials/managers_bandinvites.html',
            controller: 'managerBandinvitesConstroller'
        })
        .when('/newinvites', {
            templateUrl: 'partials/new_bandinvite.html',
            controller: 'newBandinvitesConstroller'
        })
        .when('/albums', {
            templateUrl: 'partials/albums.html',
            controller: 'albumsListController'
        })
        /*.when('/albums/:id', {
            templateUrl: 'partials/album.html',
            controller: 'albumDetailsController'
        })*/
        .when('/albums/new_album', {
            templateUrl: 'partials/new_album.html',
            controller: 'albumCreateController'
        })
        .when('/albums/:id/add_song', {
            templateUrl: 'partials/add_song.html',
            controller: 'albumAddSongController'
        })
        .when('/albums/:id/remove_song', {
            templateUrl: 'partials/remove_song.html',
            controller: 'albumRemoveSongController'
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