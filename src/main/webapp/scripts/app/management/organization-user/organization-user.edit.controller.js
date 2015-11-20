'use strict';

angular.module('ozayApp')
    .controller('OrganizationUserEditController', function ($scope, $state, $stateParams, Page, Principal, OrganizationUser) {
        $scope.pageTitle = 'Organization User New';
        $scope.contentTitle = 'Organization User New';
        $scope.button = true;
        $scope.organizationId = $stateParams.organizationId;
        $scope.access = [];
        $scope.accessList = [];
        var organizationUserId = $stateParams.organizationUserId;


        Page.get({state: $state.current.name, id:organizationUserId, building:$stateParams.buildingId}).$promise.then(function(data){
            $scope.permissions = data.permissions;
            for(var i = 0; i< data.permissions.length;i++){
                $scope.accessList.push({
                    id: data.permissions[i].id,
                    label:data.permissions[i].label,
                });
            }
            if($state.current.name == 'organization-user-edit'){
                $scope.organizationUser = data.organizationUser;

                if(data.organizationUser.organizationPermissions.length == 0){
                    $scope.organizationUser.organizationPermissions = [];
                } else{
                    for(var i = 0; i< $scope.organizationUser.organizationPermissions.length;i++){
                        var index = $scope.organizationUser.organizationPermissions[i].permissionId;
                        $scope.access[index] = true;
                    }
                }
            }
        });

        if($state.current.name == 'organization-user-edit'){
            $scope.contentTitle = 'Organization User Edit';
            $scope.pageTitle = 'Organization User Edit';
        }
        else{
            $scope.organizationUser = {};
            $scope.organizationUser.organizationId = $stateParams.organizationId;
            $scope.organizationUser.organizationPermissions = [];
        }

        $scope.organizationPermissionsClicked = function(value, modelValue){
            if(modelValue == true){
                $scope.organizationUser.organizationPermissions.push({permissionId:value});
            } else {
                for(var i = 0; i< $scope.organizationUser.organizationPermissions.length; i++){
                    if(value == $scope.organizationUser.organizationPermissions[i].permissionId){
                        $scope.organizationUser.organizationPermissions.splice(i, 1);
                        break;
                    }
                }
            }
        }

        $scope.submit = function () {
            $scope.button = false;
            $scope.successTextAlert = null;
            $scope.errorTextAlert = null;

            if(confirm("Would you like to save?")){

                if($scope.organizationUser.id === undefined || $scope.organizationUser.id == 0){
                    $scope.organizationUser.organizationId = $stateParams.organizationId;
                    OrganizationUser.save($scope.organizationUser, function (data) {
                        $scope.successTextAlert = 'Successfully created';
                    }, function (error){
                        $scope.errorTextAlert = "Error! Please try later.";
                    }).$promise.finally(function(){
                        $scope.button = true;
                    });
                } else{
                    OrganizationUser.update($scope.organizationUser, function (data) {
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

