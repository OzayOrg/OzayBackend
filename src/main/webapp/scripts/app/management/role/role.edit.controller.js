'use strict';

angular.module('ozayApp')
    .controller('RoleEditController', function ($scope, $state, $stateParams, Page, Principal, Building) {
        $scope.pageTitle = 'Role New';
        $scope.contentTitle = 'Role New';
        $scope.button = true;
        $scope.submitted = false;
        $scope.organizationId = $stateParams.organizationId;

        Page.get({state: $state.current.name, building:$stateParams.buildingId}).$promise.then(function(data){
            $scope.permissions = data.permissions;
            $scope.roles = data.roles;

        });

        if($state.current.name == 'role-edit'){
            $scope.contentTitle = 'Role Edit';
            $scope.pageTitle = 'Role Edit';
        }
        else{
            $scope.role = {};
        }

        $scope.submit = function () {
            $scope.button = false;
            if(confirm("Would you like to save?")){
                if($scope.building.id === undefined || $scope.building.id == 0){
                    $scope.building.organizationId = $stateParams.organizationId;
                    Building.save($scope.building, function (data) {
                        $scope.successTextAlert = 'Successfully created';
                    }, function (error){
                        $scope.errorTextAlert = "Error! Please try later.";
                    }).finally(function(){
                        $scope.button = true;
                    });
                } else{
                    Building.update($scope.building, function (data) {
                        $scope.successTextAlert = 'Successfully updated';
                    }, function (error){
                        $scope.errorTextAlert = "Error! Please try later.";
                    }).finally(function(){
                        $scope.button = true;
                    });
                }
            }
        };
    });

