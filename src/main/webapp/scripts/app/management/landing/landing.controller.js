'use strict';

angular.module('ozayApp')
    .controller('OrganizationController', function ($scope, $stateParams, MenuSearchState, Auth) {
        $scope.buildingInformation = MenuSearchState;
    });

