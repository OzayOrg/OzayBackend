'use strict';

angular.module('ozayApp')
    .controller('NavbarController', function ($scope, $location, $state, Auth, Principal, ENV, MenuSearchState, SelectedBuilding) {

        $scope.button_state = MenuSearchState;
        $scope.buildingList = SelectedBuilding.getBuildingList();

        $scope.selectedBuilding = SelectedBuilding.getBuilding();

        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';

        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };

        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
    });
