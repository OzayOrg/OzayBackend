'use strict';

angular.module('ozayApp')
    .controller('NotificationTrackController', function($scope, $state, NotificationTrack, Page, UserInformation) {
        $scope.button = true;
        $scope.contentTitle = 'Notification Tracker';

        $scope.process = function(pageNumber){
            Page.get({
                state: $state.current.name,
                page:pageNumber
            }).$promise.then(function(data) {
                $scope.totalItems = data.totalNumOfPages/2;
                $scope.notifications = data.notificationRecords; //this gets all the notifications

                $scope.checkboxModel = {
                                       value1 : data.totalNumOfPages-1, //find in path :totalNumOfPages
                };


            });
        }

        $scope.checkboxModel = {
                       value1 : true,
        };


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
