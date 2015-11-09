'use strict';

angular.module('ozayApp')
    .controller('OrganizationEditController', function ($scope, $state, $stateParams, Page, Principal, Organization) {
        $scope.pageTitle = 'Organization Top';
        $scope.contentTitle = 'Organization New';
        $scope.button = true;
        $scope.submitted = false;
        if($state.current.name == 'organization-edit'){
        $scope.contentTitle = 'Organization Edit';
            Page.get({state: 'organization', id:$stateParams.organizationId}).$promise.then(function(data){
                $scope.organization = data;
            });
        }

        $scope.submit = function () {
            $scope.button = false;

            if(confirm("Would you like to save?")){
                if($scope.organization.id === undefined || $scope.organization.id == 0){
                    Organization.save($scope.organization, function (data) {
                        $scope.successTextAlert = 'Successfully created';
                        $scope.button = true;
                    }, function (error){
                        $scope.errorTextAlert = "Error! Please try later.";
                        $scope.button = true;
                    });
                } else{
                    Organization.update($scope.organization, function (data) {
                        $scope.successTextAlert = 'Successfully updated';
                        $scope.button = true;
                    }, function (error){
                        $scope.errorTextAlert = "Error! Please try later.";
                        $scope.button = true;
                    });
                }
            }
        };

    });

