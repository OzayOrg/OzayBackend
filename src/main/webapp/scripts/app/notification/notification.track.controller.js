'use strict';

angular.module('ozayApp')
    .controller('NotificationTrackController', function($scope, $state, $stateParams, NotificationRecord, Page, UserInformation) {
        $scope.button = true;
        $scope.contentTitle = 'Notification Tracker';
        $scope.data = {};

        $scope.selectedUsers = [];
        if($stateParams.search !== undefined){
            $scope.data.searchKeyword = $stateParams.search;
            $scope.data.searchTrack = $stateParams.search;
        }

        $scope.track = function(notificationRecord) {
            // call api
            notificationRecord.trackComplete = !notificationRecord.trackComplete;
           /* NotificationRecord.update(notificationRecord, function(data) {
                notificationRecord = data;
                $scope.success = true;
            }, function(error) {
                $scope.errorTextAlert = "Error! Please try later.";
            }).$promise.finally(function() {
                $scope.button = true;
            });*/
        }

        $scope.update = function(notificationRecord) {
            // call api
            notificationRecord.edited = !notificationRecord.edited;
            notificationRecord.disabled = !notificationRecord.disabled;
            NotificationRecord.update(notificationRecord, function(data) {
                notificationRecord = data;
                $scope.success = true;
            }, function(error) {
                $scope.errorTextAlert = "Error! Please try later.";
            }).$promise.finally(function() {
                $scope.button = true;
            });
        }

        // pagination

        $scope.searchBtnClicked = function(){
            if($scope.data.searchTrack !== undefined){
                $state.go('notification-track', {search:$scope.data.searchTrack});
            }
        }

        $scope.pageChanged = function() {
            $state.go('notification-track', {pageId:$scope.data.currentPage, search:$stateParams.search});
        };

        $scope.maxSize = 8;
         Page.get({
            state: $state.current.name,
            page: $stateParams.page,
            search:$stateParams.search
        }).$promise.then(function(response) {
            $scope.totalItems = response.numberOfRecords / 2;
            $scope.notifications = response.notificationRecords; //this gets all the notifications\
            $scope.notes = response.notificationsRecords;
            $scope.currentPage = $stateParams.page;
        });

        $scope.onSelect = function(item) {
            $scope.notification.note = item.note;
        }

    });
