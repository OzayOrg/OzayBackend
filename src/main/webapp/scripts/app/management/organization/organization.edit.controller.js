'use strict';

angular.module('ozayApp')
    .controller('OrganizationEditController', function ($scope, $state, $stateParams, MenuSearchState, Page, Principal, Organization) {
        $scope.button_state = MenuSearchState;
        $scope.pageTitle = 'Organization Top';
        $scope.contentTitle = 'Organization New';
        $scope.button = true;
        $scope.submitted = false;
        if($state.current.name == ''){
            Page.get({state: 'management_edit', id:$stateParams.id}).$promise.then(function(data){
                $scope.organization = data;
            });
        }


        $scope.submit = function () {

            $scope.button = false;

            if(confirm("Would you like to save?")){
                if($scope.organization.id === undefined || $scope.organization.id == 0){
                    Organization.save($scope.organization, function (data) {
                        $scope.showSuccessAlert = true;
                        $scope.successTextAlert = 'Successfully created';
                        $scope.button = true;
                    }, function (error){
                        $scope.showErrorAlert = true;
                        $scope.errorTextAlert = "Error! Please try later.";
                        $scope.button = true;
                    });
                } else{
                    Organization.update($scope.organization, function (data) {
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
            Organization.save();

        };

    });

