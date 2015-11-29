'use strict';

angular.module('ozayApp')
    .controller('MemberController', function($scope, $state, Notification, MenuSearchState, Page, UserInformation) {

        $scope.memberList = [];

        $scope.predicate = 'unit';

        Page.query({
            state: $state.current.name,
            building: UserInformation.getBuilding().id
        }).$promise.then(function(data) {
            for(var i =0; i< data.length;i++){
                data[i].name = data[i].firstName + " " + data[i].lastName;
                $scope.memberList.push(data[i]);
            }
        });

    });
