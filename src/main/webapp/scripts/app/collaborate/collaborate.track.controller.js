'use strict';

angular.module('ozayApp')
    .controller('CollaborateTrackController', function($scope, $state, $stateParams, CollaborateRecord, Page, UserInformation) {
        $scope.button = true;
        $scope.contentTitle = 'Collaborate Tracker';

        $scope.selectedUsers = [];
        if($stateParams.search !== undefined){
            $scope.searchKeyword = $stateParams.search;
        }

        $scope.track = function(collaborateRecord) {
            collaborateRecord.trackComplete = !collaborateRecord.trackComplete;
            collaborateRecord.update(collaborateRecord, function(data) {
                collaborateRecord = data;
                $scope.success = true;
            }, function(error) {
                $scope.errorTextAlert = "Error! Please try later.";
            }).$promise.finally(function() {
                $scope.button = true;
            });
        }
            $scope.searchBtnClicked = function(){
            $state.go('collaborate-track', {search:$scope.searchTrack});
        }

        $scope.pageChanged = function() {
            $state.go('collaborate-track', {pageId:$scope.currentPage, search:$stateParams.search});
        };

        $scope.maxSize = 8;
         Page.get({
            state: $state.current.name,
            page: $stateParams.pageId,
            search:$stateParams.search
        }).$promise.then(function(data) {
            $scope.totalItems = data.numberOfRecords / 2;
            $scope.collaborate = data.collaborateRecords;
            $scope.notes = data.collaborateRecords;
            $scope.currentPage = $stateParams.pageId;
        });

        $scope.onSelect = function(item) {
                    $scope.collaborate.note = item.note;
                }

    });
