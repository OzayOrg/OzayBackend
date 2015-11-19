'use strict';

angular.module('ozayApp')
    .controller('OrganizationUserController', function($scope, $state, $stateParams, Page, Principal, Building) {
        $scope.pageTitle = 'Role List';
        $scope.contentTitle = 'Role list';
        $scope.roles = [];
        $scope.organizationId = $stateParams.organizationId;
        $scope.buildingId = $stateParams.buildingId;

        Page.get({
            state: $state.current.name
        }).$promise.then(function(data) {
            $scope.roles = data.roles;
        });

    });
