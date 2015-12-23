'use strict';

angular.module('ozayApp')
    .controller('NotificationTrackController', function($scope, $state, NotificationRecord, Page, UserInformation) {
        $scope.button = true;
        $scope.contentTitle = 'Notification Tracker';
        //$scope.role = [];
        //$scope.memberList = [];
        //$scope.returnedMemberList = [];
        $scope.selectedUsers = [];

        $scope.process = function(pageNumber) {
            Page.get({
                state: $state.current.name,
                page: pageNumber
            }).$promise.then(function(data) {
                $scope.totalItems = data.totalNumOfPages / 2;
                $scope.notifications = data.notificationRecords; //this gets all the notifications

                            $scope.individualList = [];

                            angular.forEach(data.notificationRecords, function(value, key) {
                                if (value.member.unit == null || value.member.unit == 0) {
                                    value.member.unit = "NA";
                                }
                                $scope.individualList.push({
                                    id: value.id,
                                    label: value.member.unit + " " + value.member.firstName + " " + value.member.lastName
                                });
                            });

            });
        }

        $scope.track = function(notificationRecord) {
            // call api
            notificationRecord.trackComplete = !notificationRecord.trackComplete;
            NotificationRecord.update(notificationRecord, function(data) {

                $scope.success = true;
            }, function(error) {
                $scope.errorTextAlert = "Error! Please try later.";
            }).$promise.finally(function() {
                $scope.button = true;
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

        $scope.onItemSelect = function(item) {
            $scope.checkWhichGroup(item.id);
        }

        $scope.onItemDeselect = function(item) {
            $scope.deselectModel(item.id);

        }
        $scope.onSelectAll = function() {
            angular.forEach($scope.roleList, function(value, key) {
                $scope.role[value.id] = true;
            });
        }
        $scope.onDeselectAll = function() {
            angular.forEach($scope.roleList, function(value, key) {
                $scope.role[value.id] = false;
            });
        }




                $scope.multiSelectSettings = {
                    enableSearch: true,
                    scrollableHeight: '300px',
                    scrollable: true,
                    //			groupByTextProvider: function(groupValue) { if (groupValue === '1') { return 'Management'; }else if (groupValue === '2') { return 'Staff'; } else if (groupValue === '3') { return 'Board'; } else { return 'Resident'; } }

                };

                $scope.eventSettings = {
                    onItemSelect: function(item) {
                        $scope.onItemSelect(item);
                    },
                    onItemDeselect: function(item) {
                        $scope.onItemDeselect(item);
                    },
                    onSelectAll: function() {
                        $scope.onSelectAll();
                    },
                    onDeselectAll: function() {
                        $scope.onDeselectAll();
                    }
                }






    });
