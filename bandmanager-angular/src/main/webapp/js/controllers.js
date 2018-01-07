var bandManagerControllers = angular.module("bandManagerControllers", ['bandManagerServices']);

/* Band create controller */

bandManagerControllers.controller('createBandController', function ($location, $scope, $routeParams, $rootScope, bandsFactory, toursFactory, loggedUserFactory) {

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

    $scope.genres = [
        "UNKNOWN",
        "BLUES",
        "COUNTRY",
        "CLASSICAL",
        "ELECTRONIC",
        "FOLK",
        "JAZZ",
        "NEW_AGE",
        "REGGAE",
        "RAP",
        "ROCK",
        "R_B",
        "MISC",
        "INDUSTRIAL",
        "WORLD"
    ];


    $scope.newBand = {
        name: "",
        genre: "",
        logoURI: "",
        managerId: $rootScope.principal_id
    };

    $scope.createNewBand = function (band) {
        bandsFactory.createBand(
            band,
            function () {
                $location.path("/bands");
            },
            $rootScope.unsuccessfulResponse
        );
    };

    $scope.isManagerRole = function (roleString) {
        return roleString === "ROLE_MANAGER";
    };

});

/* Bands list controller */

bandManagerControllers.controller('bandsController', function ($scope, $rootScope, $route, bandsFactory, membersFactory, loggedUserFactory) {

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

    $scope.deleteBand = function (id) {
        bandsFactory.deleteBand(
            parseInt(id, 10),
            function (response) {
                $route.reload();
            },
            $rootScope.unsuccessfulResponse
        );
    };

    if ($scope.role === 'ROLE_MEMBER') {
        membersFactory.getMember(
            $rootScope.principal_id,
            function(response) {
                $scope.bands = [response.data.band];
            },
            $rootScope.unsuccessfulResponse
        )
    }
    else if ($scope.role === 'ROLE_MANAGER') {
        bandsFactory.getByManager(
            $rootScope.principal_id,
            function(response) {
                $scope.bands = response.data._embedded ? response.data._embedded.bands : [];
            },
            $rootScope.unsuccessfulResponse
        )
    }

    $scope.isManagerRole = function (roleString) {
        return roleString === "ROLE_MANAGER";
    };


});

/* Band details controller */

bandManagerControllers.controller('bandDetailsController', function ($scope, $routeParams, $rootScope, bandsFactory) {
    bandsFactory.getBand(
        $routeParams.id,
        function (response) {
            $scope.band = response.data;
        },
        $rootScope.unsuccessfulResponse
    );
});

/* Band members controller */

bandManagerControllers.controller('bandMembersController', function ($scope, $routeParams, $rootScope, $route, bandsFactory, loggedUserFactory) {

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

    bandsFactory.getBandmates(
        $routeParams.id,
        function (response) {
            $scope.members = response.data._embedded ? response.data._embedded.members : [];
        },
        $rootScope.unsuccessfulResponse
    );

    $scope.isManagerRole = function (roleString) {
        return roleString === "ROLE_MANAGER";
    };
});

/* Band albums controller */

bandManagerControllers.controller('bandAlbumsController', function ($location, $scope, $routeParams, $rootScope, $route, albumsFactory, loggedUserFactory, bandsFactory) {

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

    $scope.deleteAlbum = function (id) {
        albumsFactory.deleteAlbum(
            parseInt(id, 10),
            function (response) {
                $route.reload();
            },
            $rootScope.unsuccessfulResponse
        );
    };

    albumsFactory.getAlbumsByBand(
        $routeParams.id,
        function (response) {
            $scope.albums = extractAlbumsArray(response.data);
        },
        $rootScope.unsuccessfulResponse
    );

    var extractAlbumsArray = function (responseData) {
        return responseData._embedded ? responseData._embedded.albums : [];
    };

    $scope.availableBands = [];

    bandsFactory.getByManager(
        $rootScope.principal_id,
        function (response) {
            $scope.availableBands = extractResultsArray(response.data);
        },
        $rootScope.unsuccessfulResponse
    );


    $scope.managesAlbum = function (input) {
        for (band of $scope.availableBands) {
            if (input.band.id === band.id) {
                return true;
            }
        }
        return false;
    };

    $scope.isManagerRole = function (roleString) {
        return roleString === "ROLE_MANAGER";
    };

    var extractResultsArray = function (responseData) {
        return responseData._embedded.bands;
    };

    /* Expand album to see its songs */

    $scope.tableRowExpanded = false;
    $scope.tableRowIndexExpandedCurr = "";
    $scope.tableRowIndexExpandedPrev = "";
    $scope.storeIdExpanded = "";

    $scope.dayDataCollapseFn = function () {
        $scope.dayDataCollapse = [];
        for (var i = 0; i < $scope.albums.length; i += 1) {
            $scope.dayDataCollapse.push(false);
        }
    };


    $scope.selectTableRow = function (index, storeId) {
        if (typeof $scope.dayDataCollapse === 'undefined') {
            $scope.dayDataCollapseFn();
        }

        if ($scope.tableRowExpanded === false && $scope.tableRowIndexExpandedCurr === "" && $scope.storeIdExpanded === "") {
            $scope.tableRowIndexExpandedPrev = "";
            $scope.tableRowExpanded = true;
            $scope.tableRowIndexExpandedCurr = index;
            $scope.storeIdExpanded = storeId;
            $scope.dayDataCollapse[index] = true;
        } else if ($scope.tableRowExpanded === true) {
            if ($scope.tableRowIndexExpandedCurr === index && $scope.storeIdExpanded === storeId) {
                $scope.tableRowExpanded = false;
                $scope.tableRowIndexExpandedCurr = "";
                $scope.storeIdExpanded = "";
                $scope.dayDataCollapse[index] = false;
            } else {
                $scope.tableRowIndexExpandedPrev = $scope.tableRowIndexExpandedCurr;
                $scope.tableRowIndexExpandedCurr = index;
                $scope.storeIdExpanded = storeId;
                $scope.dayDataCollapse[$scope.tableRowIndexExpandedPrev] = false;
                $scope.dayDataCollapse[$scope.tableRowIndexExpandedCurr] = true;
            }
        }

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
    };

    $scope.songFromAlbum = {
        songId: undefined,
        albumId: undefined
    };

    $scope.removeSongFromAlbum = function (songId, albumId) {
        //$scope.newSong.duration = parseInt($scope.newSong.duration , 10);
        $scope.songFromAlbum.songId = songId;
        $scope.songFromAlbum.albumId = albumId;
        albumsFactory.removeSongFromAlbum(
            $scope.songFromAlbum,
            function () {
                $location.path("/albums");
            },
            $rootScope.unsuccessfulResponse
        );
    };

    $scope.reloadPage = function () {
        $route.reload();
    }

});

/* Band album details controller */

bandManagerControllers.controller('bandAlbumDetailsController', function ($scope, $routeParams, $rootScope, $route, albumsFactory, loggedUserFactory) {
    albumsFactory.getAlbum(
        $routeParams.albumId,
        function (response) {
            $scope.album = response.data;
        },
        $rootScope.unsuccessfulResponse
    );

    $scope.toHHMMSS = toHHMMSS;

});

/* Band tour controller */

bandManagerControllers.controller('bandToursController', function ($scope, $routeParams, $rootScope, $route, toursFactory, bandsFactory, loggedUserFactory) {

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

    toursFactory.getByBand(
        $routeParams.id,
        function (response) {
            $scope.tours = extractToursArray(response.data);
        },
        $rootScope.unsuccessfulResponse
    );

    var extractToursArray = function (responseData) {
        return responseData._embedded ? responseData._embedded.tours : [];
    };

    $scope.isManagerRole = function (roleString) {
        return roleString === "ROLE_MANAGER";
    };
});

/* Tours list controller */

bandManagerControllers.controller('toursController', function ($scope, $rootScope,
                                                               toursFactory,
                                                               bandsFactory,
                                                               membersFactory,
                                                               loggedUserFactory) {

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
                $scope.removeById(parseInt(id, 10));
            },
            $rootScope.unsuccessfulResponse
        );
    };

    var extractToursArray = function (responseData) {
        if (responseData._embedded) {
            return responseData._embedded.tours;
        } else {
            return [];
        }
    };

    $scope.isManagerRole = function (roleString) {
        return roleString === "ROLE_MANAGER";
    };

    $scope.removeById = function (index) {
        for (var i = $scope.tours.length - 1; i >= 0; --i) {
            if ($scope.tours[i].id == index) {
                $scope.tours.splice(i, 1);
            }
        }
    };

    var extractResultsArray = function (responseData) {
        return responseData._embedded ? responseData._embedded.bands : [];
    };

    if ($scope.isManagerRole($rootScope.role)) {
        bandsFactory.getByManager(
            $rootScope.principal_id,
            function (response) {
                $scope.availableBands = extractResultsArray(response.data);
            },
            $rootScope.unsuccessfulResponse
        );

        toursFactory.getByManager(
            $rootScope.principal_id,
            function (response) {
                console.log($rootScope.principal_id);
                $scope.tours = extractToursArray(response.data);
            },
            $rootScope.unsuccessfulResponse
        );
    } else {
        membersFactory.getAllTours(
            $rootScope.principal_id,
            function (response) {
                $scope.availableBands = extractResultsArray(response.data);
            },
            $rootScope.unsuccessfulResponse
        )
    }
});

/* Tour details controller */

bandManagerControllers.controller('tourDetailsController', function ($scope, $routeParams, $rootScope, toursFactory) {
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
    };

    $scope.removeByIndex = function (index) {
        for (var i = $scope.songsByBand.length - 1; i >= 0; --i) {
            if ($scope.songsByBand[i].id == index) {
                $scope.songsByBand.splice(i, 1);
            }
        }
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
        $scope.newSong.duration = parseInt($scope.newSong.duration, 10);
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
    $scope.myFunc = function () {
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
            $scope.invites = response.data._embedded ? response.data._embedded.invites : [];
        },
        $rootScope.unsuccessfulResponse
    );
});
/* Managed invites */
bandManagerControllers.controller('managerBandinvitesConstroller', function ($scope, $rootScope, invitesFactory, loggedUserFactory) {

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

    $scope.cancelInvite = function (memberId, inviteId) {
        //alert("deleting".concat(memberId).concat(inviteId));
        invitesFactory.declineInvite(
            memberId,
            inviteId,
            location.reload(),
            $rootScope.unsuccessfulResponse
        );
    };

    invitesFactory.getManagerInvites(
        $rootScope.principal_id,
        function (response) {
            $scope.invites = response.data._embedded ? response.data._embedded.invites : [];
        },
        $rootScope.unsuccessfulResponse
    );
});


/* Invite new member - listing free musicians and sending invites */
bandManagerControllers.controller('newBandinvitesConstroller', function ($scope, $rootScope, invitesFactory, loggedUserFactory, membersFactory, bandsFactory) {

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
    
    $scope.sendInvite = function (memberId, bandId) {
        var newInvite = {
        memberId: memberId,
        managerId: $rootScope.principal_id,
        bandId: bandId
        };
        invitesFactory.sendInviteCreate(
            newInvite,
            location.reload(),
            $rootScope.unsuccessfulResponse
        );
    };
    bandsFactory.getByManager(
        $rootScope.principal_id,
        function (response) {
            $scope.bands = response.data._embedded.bands;
        },
        $rootScope.unsuccessfulResponse
    );
    membersFactory.getAllUnassignedMembers(
        function (response) {
            $scope.members = response.data._embedded.members;
        },
        $rootScope.unsuccessfulResponse
    );
});

/* Album details controller */

bandManagerControllers.controller('albumDetailsController', function ($scope, $routeParams, $rootScope, albumsFactory) {
    albumsFactory.getAlbum(
        $routeParams.id,
        function (response) {
            $scope.album = response.data;
        },
        $rootScope.unsuccessfulResponse
    );
});

/* Album create controller */

bandManagerControllers.controller('albumCreateController', function ($location, $scope, $routeParams, $rootScope, bandsFactory, albumsFactory, loggedUserFactory) {

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

    $scope.newAlbum = {
        name: "",
        coverURI: "",
        bandId: undefined
    };

    $scope.createNewAlbum = function (album) {
        //$scope.newSong.duration = parseInt($scope.newSong.duration , 10);
        albumsFactory.createAlbum(
            album,
            function () {
                $location.path("/albums");
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

/* Album add Song controller */

bandManagerControllers.controller('albumAddSongController', function ($location, $scope, $routeParams, $rootScope, bandsFactory, albumsFactory, loggedUserFactory, songsFactory) {

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

    albumsFactory.getAlbum($routeParams.id, function (response) {
        $scope.album = response.data;
        $scope.availableSongs = [];
        songsFactory.getSongByBandId($scope.album.band.id, function (resp) {
            for (s of resp.data._embedded.songs) {
                //console.log(s.name);
                var marker = true;
                for (t of $scope.album.songs) {
                    if (s.name === t.name) {
                        marker = false;
                    }
                }
                if (marker === true) {
                    $scope.availableSongs.push(s);
                    console.log(s.name);
                }
            }
        }, $rootScope.unsuccessfulResponse)
    }, $rootScope.unsuccessfulResponse)


    $scope.songToAlbum = {
        songId: undefined,
        albumId: $routeParams.id
    };

    $scope.addSongToAlbum = function (songToAlbum) {
        albumsFactory.addSongToAlbum(
            songToAlbum,
            function () {
                $location.path("/albums");
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

/* Album remove Song controller */

bandManagerControllers.controller('albumRemoveSongController', function ($location, $scope, $routeParams, $rootScope, bandsFactory, albumsFactory, loggedUserFactory, songsFactory) {

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

    albumsFactory.getAlbum($routeParams.id, function (response) {
        $scope.album = response.data;
        $scope.availableSongs = $scope.album.songs;
    }, $rootScope.unsuccessfulResponse);

    $scope.songFromAlbum = {
        songId: undefined,
        albumId: $routeParams.id
    };

    $scope.removeSongFromAlbum = function (songFromAlbum) {
        //$scope.newSong.duration = parseInt($scope.newSong.duration , 10);
        albumsFactory.removeSongFromAlbum(
            songFromAlbum,
            function () {
                $location.path("/albums");
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

/* Albums list controller */

bandManagerControllers.controller('albumsListController', function ($location, $scope, $rootScope, $route, albumsFactory, loggedUserFactory, bandsFactory) {

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

    $scope.deleteAlbum = function (id) {
        albumsFactory.deleteAlbum(
            parseInt(id, 10),
            function (response) {
                $route.reload();
            },
            $rootScope.unsuccessfulResponse
        );
    };

    albumsFactory.getAllAlbums(
        function (response) {
            $scope.albums = extractAlbumsArray(response.data);
        },
        $rootScope.unsuccessfulResponse
    );

    var extractAlbumsArray = function (responseData) {
        return responseData._embedded.albums;
    };

    $scope.availableBands = [];

    bandsFactory.getByManager(
        $rootScope.principal_id,
        function (response) {
            $scope.availableBands = extractResultsArray(response.data);
        },
        $rootScope.unsuccessfulResponse
    );


    $scope.managesAlbum = function (input) {
        for (band of $scope.availableBands) {
            if (input.band.id === band.id) {
                return true;
            }
        }
        return false;
    };

    $scope.isManagerRole = function (roleString) {
        return roleString === "ROLE_MANAGER";
    };

    var extractResultsArray = function (responseData) {
        return responseData._embedded.bands;
    };

    /* Expand album to see its songs */

    $scope.tableRowExpanded = false;
    $scope.tableRowIndexExpandedCurr = "";
    $scope.tableRowIndexExpandedPrev = "";
    $scope.storeIdExpanded = "";

    $scope.dayDataCollapseFn = function () {
        $scope.dayDataCollapse = [];
        for (var i = 0; i < $scope.albums.length; i += 1) {
            $scope.dayDataCollapse.push(false);
        }
    };


    $scope.selectTableRow = function (index, storeId) {
        if (typeof $scope.dayDataCollapse === 'undefined') {
            $scope.dayDataCollapseFn();
        }

        if ($scope.tableRowExpanded === false && $scope.tableRowIndexExpandedCurr === "" && $scope.storeIdExpanded === "") {
            $scope.tableRowIndexExpandedPrev = "";
            $scope.tableRowExpanded = true;
            $scope.tableRowIndexExpandedCurr = index;
            $scope.storeIdExpanded = storeId;
            $scope.dayDataCollapse[index] = true;
        } else if ($scope.tableRowExpanded === true) {
            if ($scope.tableRowIndexExpandedCurr === index && $scope.storeIdExpanded === storeId) {
                $scope.tableRowExpanded = false;
                $scope.tableRowIndexExpandedCurr = "";
                $scope.storeIdExpanded = "";
                $scope.dayDataCollapse[index] = false;
            } else {
                $scope.tableRowIndexExpandedPrev = $scope.tableRowIndexExpandedCurr;
                $scope.tableRowIndexExpandedCurr = index;
                $scope.storeIdExpanded = storeId;
                $scope.dayDataCollapse[$scope.tableRowIndexExpandedPrev] = false;
                $scope.dayDataCollapse[$scope.tableRowIndexExpandedCurr] = true;
            }
        }

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
    };

    $scope.songFromAlbum = {
        songId: undefined,
        albumId: undefined
    };

    $scope.removeSongFromAlbum = function (songId, albumId) {
        //$scope.newSong.duration = parseInt($scope.newSong.duration , 10);
        $scope.songFromAlbum.songId = songId;
        $scope.songFromAlbum.albumId = albumId;
        albumsFactory.removeSongFromAlbum(
            $scope.songFromAlbum,
            function () {
                $location.path("/albums");
            },
            $rootScope.unsuccessfulResponse
        );
    };

    $scope.reloadPage = function () {
        $route.reload();
    };

});

toHHMMSS = function (sec_string) {
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
};