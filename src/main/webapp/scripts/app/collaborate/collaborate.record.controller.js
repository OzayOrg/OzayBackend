'use strict';

angular.module('ozayApp')
    .controller('CollaborateRecordController', function($scope, $state, $stateParams, CollaborateRecord, Page, UserInformation) {
        $scope.button = true;
        $scope.contentTitle = 'Collaborate Archive';

        $scope.selectedUsers = [];
        if($stateParams.search !== undefined){
            $scope.searchKeyword = $stateParams.search;
        }
        $scope.setPage = function(pageNo) {
            $scope.currentPage = pageNo;
        };

        if($stateParams.pageId !== undefined){
            $scope.currentPage = 1;
        } else {
            $scope.currentPage = $stateParams.pageId;
        }

        $scope.searchBtnClicked = function(){
            $state.go('collaborate-record', {search:$scope.searchTrack});
        }

        $scope.pageChanged = function() {
            $state.go('collaborate-record', {pageId:$scope.currentPage, search:$stateParams.search});
        };

        $scope.maxSize = 8;

        Page.get({
            state: $state.current.name,
            page:$stateParams.pageId,
            search:$stateParams.search
        }).$promise.then(function(data) {
            $scope.totalItems = data.totalNumOfPages/2;
            $scope.collaborate = data.collaborateRecords;
            $scope.currentPage = $stateParams.pageId;
        });

    });
