'use strict';

angular.module('ozayApp')
    .controller('NavbarController', function ($scope, $location, $state, Auth, Principal, $cookies, ENV, MenuSearchState, SelectedBuilding) {
        $scope.activeMenu = $state;

        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';



        $scope.logout = function () {
            SelectedBuilding.clear();
            Auth.logout();
            $state.go('login');
        };

        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.changeBuilding = function(){
            SelectedBuilding.setBuilding($scope.selectedBuilding);
            $cookies.put('selectedBuilding', $scope.selectedBuilding);
            //$state.transitionTo('home.home', null, {'reload':true});
            $state.reload();
        }

    });
