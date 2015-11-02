'use strict';

angular.module('ozayApp')
    .controller('OrganizationController', function ($scope,  $state, $stateParams, MenuSearchState, Page, Principal) {
        $scope.button_state = MenuSearchState;
        $scope.pageTitle = 'Organization Top';

        Page.get({state: $state.current.name}).$promise.then(function(data){
            $scope.organizations = data.organizations
        });

    });

