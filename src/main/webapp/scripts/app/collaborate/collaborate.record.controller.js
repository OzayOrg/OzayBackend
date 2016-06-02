'use strict';

angular.module('ozayApp')
    .controller('NotificationRecordController', function($scope, $state, $stateParams, NotificationRecord, Page, UserInformation) {
        $scope.button = true;
        $scope.contentTitle = 'Notification Archive';

        $scope.selectedUsers = [];
        if($stateParams.search !== undefined){
            $scope.searchKeyword = $stateParams.search;
        }


        // pagination

        $scope.setPage = function(pageNo) {
            $scope.currentPage = pageNo;
        };

        if($stateParams.pageId !== undefined){
            $scope.currentPage = 1;
        } else {
            $scope.currentPage = $stateParams.pageId;
        }

       /*
        $scope.pageChanged = function() {
            $state.go('notification-record', {pageId:$scope.currentPage});
        };
        */
        $scope.searchBtnClicked = function(){
            $state.go('notification-record', {search:$scope.searchTrack});
        }

        $scope.pageChanged = function() {
            $state.go('notification-record', {pageId:$scope.currentPage, search:$stateParams.search});
        };

        $scope.maxSize = 8;

        Page.get({
            state: $state.current.name,
            page:$stateParams.pageId,
            search:$stateParams.search
        }).$promise.then(function(data) {
            $scope.totalItems = data.totalNumOfPages/2;
            $scope.notifications = data.notificationRecords;
            $scope.currentPage = $stateParams.pageId;
        });

    });
