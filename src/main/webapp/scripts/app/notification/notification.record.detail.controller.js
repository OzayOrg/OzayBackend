'use strict';

angular.module('ozayApp')
    .controller('NotificationRecordDetailController', function($scope, $state, $stateParams, NotificationRecord, Page, UserInformation) {
        $scope.button = true;
        $scope.contentTitle = 'Notification Archive';

        Page.get({
            state: $state.current.name,
            building: UserInformation.getBuilding().id,
            id:$stateParams.notificationId
        }).$promise.then(function(data) {
            $scope.notification = data.notification;
            $scope.notificationRecords = data.notificationRecords;
        });


    });
