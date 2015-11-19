'use strict';

angular.module('ozayApp')
    .controller('BuildingEditController', function ($scope, $state, $stateParams, Page, Principal, Building) {
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
            $scope.successTextAlert = null;
            $scope.errorTextAlert = null;
            if(confirm("Would you like to save?")){
                if($scope.building.id === undefined || $scope.building.id == 0){
                    $scope.building.organizationId = $stateParams.organizationId;
                    Building.save($scope.building, function (data) {
                        $scope.successTextAlert = 'Successfully created';
                    }, function (error){
                        $scope.errorTextAlert = "Error! Please try later.";
                    }).$promise.finally(function(){
                        $scope.button = true;
                    });
                } else{
                    Building.update($scope.building, function (data) {
                        $scope.successTextAlert = 'Successfully updated';
                    }, function (error){
                        $scope.errorTextAlert = "Error! Please try later.";
                    }).$promise.finally(function(){
                        $scope.button = true;
                    });
                }
            }
        };
    });

