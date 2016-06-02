'use strict';

angular.module('ozayApp')
    .controller('CollaborateController', function($scope, $state, Notification, MenuSearchState, Page, UserInformation) {
        $scope.button = true;
        $scope.role = [];
        $scope.memberList = [];
        $scope.returnedMemberList = [];
        $scope.selectedUsers = [];
        $scope.contentTitle = 'Collaborate Create';

        Page.get({
            state: $state.current.name
        }).$promise.then(function(data) {

        });


    });
