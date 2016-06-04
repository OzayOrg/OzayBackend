'use strict';

angular.module('ozayApp')
    .controller('CollaborateRecordController', function($scope, $state, $stateParams, NotificationRecord, Page, UserInformation) {


        // Shared HTML file with collaborate.track.controller
        $scope.detailUrl = "collaborate-record-detail";
        $scope.data = {};
        $scope.track = false;
        var pageLoaded = false;

        $scope.data.currentPage = 1;
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

        $scope.pageChanged = function() {
            if(pageLoaded){
                $state.go('collaborate-track', {
                    page: $scope.data.currentPage
                });
            }
        };


    });
