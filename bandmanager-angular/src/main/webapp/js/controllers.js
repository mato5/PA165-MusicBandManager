var bandManagerControllers = angular.module("bandManagerControllers", ['bandManagerServices']);

/* Tours list controller */

bandManagerControllers.controller('toursController', function ($scope, $rootScope, $route, toursFactory, loggedUserFactory) {

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

    $scope.deleteTour = function (id) {
        toursFactory.deleteTour(
            parseInt(id, 10),
            function (response) {
                $route.reload();
            },
            $rootScope.unsuccessfulResponse
        );
    };

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

/* Songs list controller */

bandManagerControllers.controller('songsController', function ($scope, $rootScope, bandsFactory, songsFactory, loggedUserFactory) {

    $scope.songsByBand = [];

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

    bandsFactory.getByManager(
        $rootScope.principal_id,
        function (response) {
            $scope.availableBands = extractResultsArray(response.data);

            for (band of $scope.availableBands) {
                console.log(band);
                songsFactory.getSongByBandId(
                    band.id,
                    function (response) {
                        let newSongs = response.data._embedded.songs;
                        Array.prototype.push.apply($scope.songsByBand, newSongs);
                    },
                    $rootScope.unsuccessfulResponse
                );

            }
        },
        $rootScope.unsuccessfulResponse
    );

    $scope.isManagerRole = function (roleString) {
        return roleString === "ROLE_MANAGER";
    };

    var extractResultsArray = function (responseData) {
        return responseData._embedded.bands;
    };

    $scope.toHHMMSS = function (sec_string) {
        sec_num = parseInt(sec_string, 10);
        var hours = Math.floor(sec_num / 3600);
        var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
        var seconds = sec_num - (hours * 3600) - (minutes * 60);

        if (hours < 10) {
            hours = "0" + hours;
        }
        if (minutes < 10) {
            minutes = "0" + minutes;
        }
        if (seconds < 10) {
            seconds = "0" + seconds;
        }
        return hours + ':' + minutes + ':' + seconds;
    }
});

/* Song details controller */

bandManagerControllers.controller('songDetailsController', function ($scope, $routeParams, $rootScope, songsFactory) {
    songsFactory.getSong(
        $routeParams.id,
        function (response) {
            $scope.song = response.data;
        },
        $rootScope.unsuccessfulResponse
    );

    $scope.toHHMMSS = function (sec_string) {
        sec_num = parseInt(sec_string, 10);
        var hours = Math.floor(sec_num / 3600);
        var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
        var seconds = sec_num - (hours * 3600) - (minutes * 60);

        if (hours < 10) {
            hours = "0" + hours;
        }
        if (minutes < 10) {
            minutes = "0" + minutes;
        }
        if (seconds < 10) {
            seconds = "0" + seconds;
        }
        return hours + ':' + minutes + ':' + seconds;
    }
});


/* Song create controller */

bandManagerControllers.controller('createSongController', function ($location, $scope, $routeParams, $rootScope, bandsFactory, songsFactory, loggedUserFactory) {

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

    $scope.newSong = {
        name: "",
        duration: "",
        bandId: undefined
    };

    $scope.createNewSong = function (song) {
        $scope.newSong.duration = parseInt($scope.newSong.duration , 10);
        songsFactory.createSong(
            song,
            function () {
                $location.path("/songs");
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

/* Member's invites */
bandManagerControllers.controller('memberInvitesController', function ($scope, $rootScope, $location, invitesFactory, loggedUserFactory, membersFactory) {

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
    $scope.myFunc = function() {
      $scope.count++;
    };
    $scope.declineInvite = function (memberId, inviteId) {
        //alert("deleting".concat(memberId).concat(inviteId));
        invitesFactory.declineInvite(
                memberId,
                inviteId,
                location.reload(),
                $rootScope.unsuccessfulResponse
                );
    };
    $scope.acceptInvite = function (memberId, inviteId) {
        //alert("accepting".concat(memberId).concat(inviteId));
        invitesFactory.acceptInvite(
                memberId,
                inviteId,
                location.reload(),
                $rootScope.unsuccessfulResponse
                );
    };
    invitesFactory.getMemberInvites(
        $rootScope.principal_id,
        function (response) {
            $scope.invites = response.data._embedded.invites;
        },
        $rootScope.unsuccessfulResponse
    );
});