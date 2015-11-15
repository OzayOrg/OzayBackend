'use strict';

angular.module('ozayApp')
    .controller('OrganizationDetailController', function ($scope, $state, $stateParams, Page, Principal, Organization, UserInformation) {
        $scope.pageTitle = 'Organization Detail';
        $scope.predicate = 'name';
        $scope.organizationId = $stateParams.organizationId;


        Page.get({state: $state.current.name, id:$stateParams.organizationId}).$promise.then(function(data){
            $scope.buildings = data.buildings;
        });

    });

