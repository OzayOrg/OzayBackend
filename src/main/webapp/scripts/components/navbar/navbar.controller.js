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

        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        if(UserInformation.getBuildingList().length > 0){
            $scope.buildingList = UserInformation.getBuildingList();
            $scope.selectedBuilding = UserInformation.getBuilding().id;
            $scope.organizationMenuExtra = false;
            if(UserInformation.getBuilding().id !== undefined){
                $scope.orgName = UserInformation.getBuilding().organizationName;
                $scope.buildingName = UserInformation.getBuilding().name;
                $scope.organizationMenuExtra = true;
            }

            $scope.organizationId = UserInformation.getBuilding().organizationId;

        }


        $scope.changeBuilding = function(){
            UserInformation.setBuilding($scope.selectedBuilding);
            $cookies.put('selectedBuilding', $scope.selectedBuilding);
//            $state.transitionTo('home', null, {'reload':true});
            $state.reload();
        }
    });
