'use strict';

angular.module('ozayApp')
    .controller('CollaborateTrackController', function($scope, $state, $stateParams, Page, UserInformation) {

        $scope.data = {};
        var pageLoaded = false;
        // Shared HTML file with collaborate.record.controller
        $scope.detailUrl = "collaborate-track-detail";
        $scope.track = true;
        $scope.data.currentPage = 1;


        $scope.pageChanged = function() {
            if(pageLoaded){
                $state.go('collaborate-track', {
                    page: $scope.data.currentPage
                });
            }
        };

        if ($stateParams.page !== undefined) {
            $scope.data.currentPage = 1;
        } else {
            $scope.data.currentPage = $stateParams.page;
        }
        $scope.data.maxSize = 8;
        Page.get({
            state: $state.current.name,
            page: $stateParams.page !== undefined ? $stateParams.page : 1
        }).$promise.then(function(data) {
            $scope.totalItems = data.numberOfRecords / 2;
            $scope.collaborates = data.collaborates; //this gets all the collaborates\
            $scope.data.currentPage = $stateParams.page !== undefined ? $stateParams.page : 1;
            pageLoaded = true;
        });
        $scope.setPage = function(pageNo) {
            $scope.data.currentPage = pageNo;
        };


    });
