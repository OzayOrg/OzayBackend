'use strict';

angular.module('ozayApp')
    .controller('OrganizationDetailController', function ($scope, $state, $stateParams, MenuSearchState, Page, Principal, Organization) {
        $scope.button_state = MenuSearchState;
        $scope.pageTitle = 'Organization Detail';
        $scope.predicate = 'name';

        Page.get({state: $state.current.name, id:$stateParams.id}).$promise.then(function(data){
            $scope.buildings = data.buildings;
        });

    });

