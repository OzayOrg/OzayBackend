'use strict';

angular.module('ozayApp')
    .controller('RoleController', function ($scope, $state, $stateParams, Page, Principal, Building) {
        $scope.pageTitle = 'Building New';
        $scope.contentTitle = 'Building New';

        $scope.organizationId = $stateParams.organizationId;
        $scope.buildingId = $stateParams.buildingId;


    });

