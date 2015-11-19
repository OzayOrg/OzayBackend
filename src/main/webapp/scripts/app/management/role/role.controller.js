'use strict';

angular.module('ozayApp')
    .controller('RoleController', function($scope, $state, $stateParams, Page, Principal, Building) {
        $scope.pageTitle = 'Role List';
        $scope.contentTitle = 'Role list';
        $scope.roles = [];
        $scope.organizationId = $stateParams.organizationId;
        $scope.buildingId = $stateParams.buildingId;

        Page.get({
            state: $state.current.name,
            building: $stateParams.buildingId
        }).$promise.then(function(data) {
            $scope.roles = data.roles;
        });

    });
