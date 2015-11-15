'use strict';

angular.module('ozayApp')
    .controller('PageController', function ($scope, MenuSearchState) {
        $scope.button_state = MenuSearchState;
        $scope.loaded = true;

    });
