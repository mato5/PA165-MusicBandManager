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

        toursDataFactory.getAllTours = function (success, error) {
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
            return $http.get(baseURL.concat("/members/bandmates/" + id)).then(success, error);
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

        return managersDataFactory;
    }
]);

/* Member factory */

bandManagerServices.factory('membersFactory', ['$http',
    function ($http) {

        var membersDataFactory = {};

        var memberResourceUrl = baseURL.concat("/members");
        var newMemberResourceId = memberResourceUrl.concat("/{id}");

        membersDataFactory.getAllMembers = function (success, error) {
            return $http.get(memberResourceUrl).then(success, error);
        };
        membersDataFactory.getAllUnassignedMembers = function (success, error) {
            return $http.get(memberResourceUrl.concat("/unassigned")).then(success, error);
        };
        membersDataFactory.getMember = function (id, success, error) {
            return $http.get(memberResourceUrl + "/" + id).then(success, error);
        };

        membersDataFactory.createMember = function (member, success, error) {
            return $http.post(memberResourceUrl + "/create", member).then(success, error);
        };
        membersDataFactory.acceptBandInvite = function (inviteId, success, error) {
            return $http.put(memberResourceUrl + "/accept_invite/{id}", inviteId).then(success, error);
        };
        membersDataFactory.declineBandInvite = function (inviteId, success, error) {
            return $http.put(memberResourceUrl + "/decline_invite/{id}", inviteId).then(success, error);
        };

        membersDataFactory.getAllTours = function (id, success, error) {
            return $http.get(memberResourceUrl + "/activities/" + id).then(success, error);
        };

        return membersDataFactory;
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
]);

//<<<<<<< HEAD
/* BandInvites factory */
/*
bandManagerServices.factory('bandInvitesFactory', ['$http',
    function ($http) {

        var bandInvitesDataFactory = {};

        var bandInvitesResourceUrl = baseURL.concat("/bandinvites");

        bandInvitesDataFactory.getMemberInvites = function (memId, success, error) {
            return $http.get(bandInvitesResourceUrl.concat("?memId=").concat(memId)).then(success, error);
        };
        bandInvitesDataFactory.getManagerInvites = function (manId, success, error) {
            return $http.get(bandInvitesResourceUrl + "?manId=" + manId).then(success, error);
        };
        bandInvitesDataFactory.createBandInvite = function (bandInvite, success, error) {
            return $http.post(bandInvitesResourceUrl + "/create", bandInvite).then(success, error);
        };

        return bandInvitesDataFactory;
    }
]);

=======*/
/* BandInvite factory */
//TODO
bandManagerServices.factory('invitesFactory', ['$http',
    function ($http) {

        var invitesDataFactory = {};

        var inviteResourceUrl = baseURL.concat("/bandinvites");
        var newInviteResourceId = inviteResourceUrl.concat("/{id}");

        invitesDataFactory.acceptInvite = function (memberId, invId, success, error) {
            return $http.put(baseURL.concat("/members/accept_invite/" + memberId), invId).then(success, error);
        };

        invitesDataFactory.declineInvite = function (memberId, invId, success, error) {
            return $http.put(baseURL.concat("/members/decline_invite/" + memberId), invId).then(success, error);
        };

        invitesDataFactory.getInvitesByMember = function (memberId, success, error) {
            return $http.get(baseURL.concat("/members/invites/" + memberId)).then(success, error);
        };
        // Mel jsem to uz napsany s pomoci teto metody, tak sjem tu zatim nechal obe
        invitesDataFactory.getMemberInvites = function (memId, success, error) {
            return $http.get(inviteResourceUrl.concat("?memId=").concat(memId)).then(success, error);
        };
        invitesDataFactory.getManagerInvites = function (manId, success, error) {
            return $http.get(inviteResourceUrl.concat("?manId=").concat(manId)).then(success, error);
        };
        invitesDataFactory.sendInvite = function (invite, success, error) {
            return $http.post(baseURL.concat("/managers/send_invite"), invite).then(success, error);
        };

        return invitesDataFactory;
    }
]);

/* Album factory */
bandManagerServices.factory('albumsFactory', ['$http',
    function ($http) {

        var albumsDataFactory = {};

        var albumResourceUrl = baseURL.concat("/albums");
        var newAlbumResourceId = albumResourceUrl.concat("/{id}");

        albumsDataFactory.getAllAlbums = function (success, error) {
            return $http.get(albumResourceUrl).then(success, error);
        };

        albumsDataFactory.getAlbum = function (id, success, error) {
            return $http.get(albumResourceUrl + "/" + id).then(success, error);
        };

        albumsDataFactory.getAlbumsByBand = function (bandId, success, error) {
            return $http.get(albumResourceUrl + "/by_band_id" + "/" + bandId).then(success, error);
        };

        albumsDataFactory.deleteAlbum = function (id, success, error) {
            return $http.delete(albumResourceUrl + "/" + id).then(success, error);
        };

        albumsDataFactory.createAlbum = function (album, success, error) {
            return $http.post(baseURL.concat("/managers/create_album"), album).then(success, error);
        };

        albumsDataFactory.addSongToAlbum = function (songToAlbum, success, error) {
            return $http.put(baseURL.concat("/managers/add_to_album"), songToAlbum).then(success, error);
        };

        albumsDataFactory.removeSongFromAlbum = function (songFromAlbum, success, error) {
            return $http.put(baseURL.concat("/managers/remove_from_album"), songFromAlbum).then(success, error);
        };

        return albumsDataFactory;
    }
]);
