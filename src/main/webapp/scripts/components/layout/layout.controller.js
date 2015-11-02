'use strict';

angular.module('ozayApp')
    .controller('LayoutController', function ($scope, $location, $state, Auth, Principal, $cookies, ENV, MenuSearchState, SelectedBuilding) {
        $scope.button_state = MenuSearchState;
        $scope.buildingList = SelectedBuilding.getBuildingList();
        $scope.selectedBuilding = SelectedBuilding.getBuilding();

    });
