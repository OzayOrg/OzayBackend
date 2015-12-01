'use strict';

angular.module('ozayApp')
    .controller('NotificationRecordController', function($scope, $state, NotificationRecord, Page, UserInformation) {
        $scope.button = true;
        $scope.contentTitle = 'Notification Archive';

        $scope.process = function(pageNumber){
            Page.get({
                state: $state.current.name,
                page:pageNumber
            }).$promise.then(function(data) {
                $scope.totalItems = data.totalNumOfPages/2;
                $scope.notifications = data.notificationRecords;
            });
        }

        // pagination

        $scope.setPage = function(pageNo) {
            $scope.currentPage = pageNo;
        };

        $scope.pageChanged = function() {
            $scope.process($scope.currentPage);
        };

        $scope.maxSize = 8;
        $scope.currentPage = 1;
        $scope.process();






    });
