'use strict';

angular.module('ozayApp')
    .controller('RoleController', function($scope, $state, $stateParams, Page, Role) {
        $scope.pageTitle = 'Role List';
        $scope.contentTitle = 'Role list';
        $scope.roles = [];
        $scope.organizationId = $stateParams.organizationId;
        $scope.buildingId = $stateParams.buildingId;
        $scope.button = true;

        $scope.getContents = function(){
            Page.get({
                state: $state.current.name,
                building: $stateParams.buildingId
            }).$promise.then(function(data) {
                $scope.roles = data.roles;
            });
        }



        $scope.submit = function() {
            $scope.button = false;
            $scope.successTextAlert = null;
            $scope.errorTextAlert = null;

            var result = confirm("Are you sure you want to delete role(s)?");
            if(result == true){
                var deletedList = [];
                var count = 0;
                angular.forEach($scope.roles, function(value, key) {
                    if(value.deleted == true){
                        deletedList.push(value);
                        count++;
                    }
                });
                if(count == 0){
                    $scope.errorTextAlert = "Error! You have to select at least one role";
                    return false;
                }

                Role.save({method: "delete"}, deletedList, function(data) {
                    $scope.successTextAlert = 'Successfully deleted';
                    $scope.getContents();
                }, function(error) {
                    if(error.data.message !== undefined){
                        $scope.errorTextAlert = error.data.message;
                    } else {
                        $scope.errorTextAlert = "Error! Please try later.";
                    }

                }).$promise.finally(function() {
                    $scope.button = true;
                });
            }
        }
        $scope.getContents();
    });
