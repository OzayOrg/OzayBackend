'use strict';

angular.module('ozayApp')
    .controller('NotificationTrackController', function($scope, $state, $stateParams, NotificationRecord, Page, UserInformation) {
        $scope.button = true;
        $scope.contentTitle = 'Notification Tracker';

        $scope.selectedUsers = [];
        if($stateParams.search !== undefined){
            $scope.searchKeyword = $stateParams.search;
        }

        $scope.track = function(notificationRecord) {
            // call api
            notificationRecord.trackComplete = !notificationRecord.trackComplete;
			
            NotificationRecord.update(notificationRecord, function(data) {
                notificationRecord = data;
                $scope.success = true;
            }, function(error) {
                $scope.errorTextAlert = "Error! Please try later.";
            }).$promise.finally(function() {
                $scope.button = true;
            });
			
			var date=new Date();
			notificationRecord.trackCompletedDate = date.getFullYear() + '-' + ('0' + (date.getMonth() + 1)).slice(-2) + '-' + ('0' + date.getDate()).slice(-2);;
        }
		
		$scope.checkTimeStamp = function(notificationRecord) {
            // call api
			if(notificationRecord.note==null && notificationRecord.trackComplete==false)
			return false;
			else
			return true;
        }
		
		$scope.toggle = function(notificationRecord) {
            // call api
			notificationRecord.success=!(notificationRecord.success);
			//checkTimeStamp(notificationRecord);
            //notificationRecord.editComplete = !notificationRecord.editComplete;
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
            $state.go('notification-track', {search:$scope.searchTrack});
        }

        $scope.pageChanged = function() {
            $state.go('notification-track', {pageId:$scope.currentPage, search:$stateParams.search});
        };

        $scope.maxSize = 8;
         Page.get({
            state: $state.current.name,
            page: $stateParams.pageId,
            search:$stateParams.search
        }).$promise.then(function(data) {
            $scope.totalItems = data.numberOfRecords / 2;
            $scope.notifications = data.notificationRecords; //this gets all the notifications\
            $scope.notes = data.notificationsRecords;
            $scope.currentPage = $stateParams.pageId;
        });

        $scope.onSelect = function(item) {
                    $scope.notification.note = item.note;
                }

    });
