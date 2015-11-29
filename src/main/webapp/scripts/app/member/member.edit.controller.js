'use strict';

angular.module('ozayApp')
    .controller('MemberEditController', function($scope, $state, $stateParams, Member, MessageService, Page, UserInformation) {
        $scope.button = true;

        Page.get({
            state: $state.current.name,
            building: UserInformation.getBuilding().id,
            id: $stateParams.memberId,
        }).$promise.then(function(data) {
            if($state.current.name == 'member-edit'){
                $scope.deleteBtn = true;
                var message = MessageService.getSuccessMessage();
                if(message !== undefined){
                    $scope.successTextAlert = message;
                }
                $scope.member = data.member;
            }
            $scope.roles = data.roles;
        });

        $scope.deleteBtn = function(){
            alert(1);
        }

        $scope.memberRoleClicked = function(model){
            angular.forEach($scope.roles, function(value, key) {
                if(model.id != value.id && model.belongTo == value.id){
                    if(model.assign == true){
                        value.assign = true;
                    }
                    else {
                        value.assign = false;
                    }
                }
            });

        }
        $scope.submit = function(form) {
            $scope.button = false;
            $scope.successTextAlert = null;
            $scope.errorTextAlert = null;
            if (confirm("Would you like to save?")) {
                if(form.$valid) {
                   if(($scope.member.unit == null || $scope.member.unit == "") && $scope.member.organizationUserId != null){
                        form.unit.$invalid = true;
                        $scope.button = true;
                        return false;
                   }
                }
                $scope.member.buildingId = UserInformation.getBuilding().id;
                var form = {};
                form['member'] = $scope.member;
                form['roles'] = $scope.roles;
                if ($scope.member.id === undefined || $scope.member.id == 0) {
                    Member.save({buildingId:UserInformation.getBuilding().id}, form, function(data) {
                        MessageService.setSuccessMessage('Successfully created');
                        console.log(data.id);
                        $state.transitionTo('member-edit', {memberId: data.id}, {
                            reload: true,
                            inherit: false,
                            notify: true
                        });
                    }, function(error) {
                        $scope.errorTextAlert = "Error! Please try later.";
                    }).$promise.finally(function() {
                        $scope.button = true;
                    });
                } else {
                    Member.update(form, function(data) {
                        $scope.successTextAlert = "Successfully updated";
                    }, function(error) {
                        $scope.errorTextAlert = "Error! Please try later.";
                    }).$promise.finally(function() {
                        $scope.button = true;
                    });
                }
            } else {
                $scope.button = true;
            }
        };

    });
