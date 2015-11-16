'use strict';

angular.module('ozayApp')
    .controller('RoleEditController', function ($scope, $state, $stateParams, Page, Principal, Building) {
        $scope.pageTitle = 'Role New';
        $scope.contentTitle = 'Role New';
        $scope.button = true;
        $scope.submitted = false;
        $scope.organizationId = $stateParams.organizationId;

        if($state.current.name == 'role-edit'){
            $scope.contentTitle = 'Role Edit';
            $scope.pageTitle = 'Role Edit';
            Page.get({state: 'role-edit', id:$stateParams.buildingId}).$promise.then(function(data){
                $scope.role = data.role;
            });
        }
        else{
            $scope.building = {};
        }

        $scope.submit = function () {

            $scope.button = false;

            if(confirm("Would you like to save?")){
                if($scope.building.id === undefined || $scope.building.id == 0){
                    $scope.building.organizationId = $stateParams.organizationId;
                    Building.save($scope.building, function (data) {
                        $scope.showSuccessAlert = true;
                        $scope.successTextAlert = 'Successfully created';
                        $scope.button = true;
                    }, function (error){
                        $scope.showErrorAlert = true;
                        $scope.errorTextAlert = "Error! Please try later.";
                        $scope.button = true;
                    });
                } else{
                    Building.update($scope.building, function (data) {
                        $scope.showSuccessAlert = true;
                        $scope.successTextAlert = 'Successfully updated';
                        $scope.button = true;
                    }, function (error){
                        $scope.showErrorAlert = true;
                        $scope.errorTextAlert = "Error! Please try later.";
                        $scope.button = true;
                    });
                }
            }
        };
    });

