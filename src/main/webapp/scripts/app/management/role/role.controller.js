'use strict';

angular.module('ozayApp')
    .controller('RoleController', function ($scope, $state, $stateParams, Page, Principal, Building) {
        $scope.pageTitle = 'Building New';
        $scope.contentTitle = 'Building New';
        $scope.button = true;
        $scope.submitted = false;
        $scope.organizationId = $stateParams.organizationId;

        if($state.current.name == 'building-edit'){
            $scope.contentTitle = 'Building Edit';
            $scope.pageTitle = 'Building Edit';
            Page.get({state: 'building-edit', id:$stateParams.buildingId}).$promise.then(function(data){
                $scope.building = data.building;
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

