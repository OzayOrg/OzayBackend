'use strict';

angular.module('ozayApp')
    .controller('BuildingEditController', function ($scope, $state, $stateParams, MenuSearchState, Page, Principal, Building) {
        $scope.button_state = MenuSearchState;
        $scope.pageTitle = 'Building Top';
        $scope.contentTitle = 'Building New';
        $scope.button = true;
        $scope.submitted = false;

        if($state.current.name == 'management-edit'){
            Page.get({state: 'management_edit', id:$stateParams.id}).$promise.then(function(data){
                $scope.building = data;
            });
        }
        else{
            $scope.building = {};
        }


        $scope.submit = function () {

            $scope.button = false;

            if(confirm("Would you like to save?")){
                if($scope.building.id === undefined || $scope.building.id == 0){
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
            Building.save();

        };


    });

