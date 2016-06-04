'use strict';

angular.module('ozayApp')
    .controller('NotificationRecordController', function($scope, $state, $stateParams, NotificationRecord, Page, UserInformation) {
        $scope.button = true;
        if($stateParams.search !== undefined){
            $scope.searchKeyword = $stateParams.search;
        }
        $scope.data = {};
        var pageLoaded = false;
        var loadedPageNum = $stateParams.page === undefined ? 1 : $stateParams.page;

        // pagination
        $scope.data.currentPage = 1;
        if($stateParams.page === undefined){
            $scope.data.currentPage = 1;
        } else {
            $scope.data.currentPage = $stateParams.page;
        }

        $scope.searchBtnClicked = function(){
            $state.go('notification-record', {search:$scope.searchTrack});
        }

        $scope.pageChanged = function() {
            if(pageLoaded){
                $state.go('notification-record', {page:$scope.data.currentPage, search:$stateParams.search});
            }
        };

        $scope.data.maxSize = 8;

        Page.get({
            state: $state.current.name,
            page: $scope.data.currentPage,
            search:$stateParams.search
        }).$promise.then(function(data) {
            $scope.totalItems = data.totalNumOfPages/2;
            $scope.notifications = data.notificationRecords;
            $scope.data.currentPage = loadedPageNum;
            pageLoaded = true;
        });

    });
