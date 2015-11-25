'use strict';

angular.module('ozayApp')
    .controller('NotificationRecordController', function($scope, $state, NotificationRecord, Page, UserInformation) {
        $scope.button = true;
        $scope.contentTitle = 'Notification Archive';

        // pagination


        $scope.setPage = function(pageNo) {
            $scope.currentPage = pageNo;
        };

        $scope.pageChanged = function() {
            $log.log('Page changed to: ' + $scope.currentPage);
        };

        $scope.maxSize = 5;
        $scope.bigTotalItems = 15;
        $scope.bigCurrentPage = 1;



        Page.get({
            state: $state.current.name,
            building: UserInformation.getBuilding().id
        }).$promise.then(function(data) {
            console.log(data);
            $scope.notifications = data.notificationRecords;
        });

    });
