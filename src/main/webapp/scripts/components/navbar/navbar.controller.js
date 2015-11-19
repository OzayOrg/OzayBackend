'use strict';

angular.module('ozayApp')
    .controller('NavbarController', function ($scope, $location, $state, Auth, Principal, $cookies, ENV, UserInformation, Building) {
        $scope.activeMenu = $state;
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';


        $scope.logout = function () {
//            UserInformation.clear();
            Auth.logout();
            $state.go('login');
        };

        Principal.identity(true).then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });



        $scope.buildingList = UserInformation.getBuildingList();
        $scope.selectedBuilding = UserInformation.getBuilding();

        $scope.changeBuilding = function(){
            UserInformation.setBuilding($scope.selectedBuilding);
            $cookies.put('selectedBuilding', $scope.selectedBuilding);
            //$state.transitionTo('home.home', null, {'reload':true});
            $state.reload();
        }
    });
