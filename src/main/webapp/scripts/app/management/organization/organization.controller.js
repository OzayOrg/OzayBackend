'use strict';

angular.module('ozayApp')
    .controller('OrganizationController', function ($scope, $state, $stateParams, MenuSearchState, Page, Principal, Organization) {
        $scope.button_state = MenuSearchState;
        $scope.pageTitle = 'Organization Top';
        $scope.predicate = 'name';

        Page.get({state: $state.current.name}).$promise.then(function(data){
            $scope.organizations = data.organizations;
        });

    });

