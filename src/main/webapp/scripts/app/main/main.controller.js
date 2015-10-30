'use strict';

angular.module('ozayApp')
    .controller('MainController', function ($scope, Principal, ozaySearchState) {
        // For mobile nav
        $scope.button_state = ozaySearchState;


        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
    });
