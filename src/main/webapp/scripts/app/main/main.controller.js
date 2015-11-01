'use strict';

angular.module('ozayApp')
    .controller('MainController', function ($scope, Principal, MenuSearchState) {
        // For mobile nav
        $scope.menu_search_state = MenuSearchState;

        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
    });
