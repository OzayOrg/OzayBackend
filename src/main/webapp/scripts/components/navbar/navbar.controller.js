'use strict';

angular.module('ozayApp')
    .controller('NavbarController', function ($scope, $location, $state, Auth, Principal, ENV, MenuSearchState, SelectedBuilding) {

        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';

        $scope.button_state = MenuSearchState;
        $scope.buildingList = SelectedBuilding.getBuildingList();
        $scope.selectedBuilding = SelectedBuilding.getBuilding();

        $scope.logout = function () {
            SelectedBuilding.clear();
            Auth.logout();
            $state.go('login');
        };

        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
    });
