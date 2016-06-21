'use strict';

angular.module('ozayApp')
    .controller('CollaborateRecordController', function($scope, $state, $stateParams, NotificationRecord, Page, UserInformation) {


        $scope.calculateProcess = function(collaborateObj){

            var total = collaborateObj.collaborateFields[0].collaborateMembers.length;
            var repliedUserList = [];
            for(var i = 0; i < collaborateObj.collaborateFields.length; i++){
                var date = collaborateObj.collaborateFields[i];
                for(var j = 0; j<  date.collaborateMembers.length;j++){
                    var cMember = date.collaborateMembers[j];
                    if(repliedUserList.indexOf(cMember.member.id) === -1){
                        if(cMember.selected != null){
                            repliedUserList.push(cMember.member.id);
                        }
                    }
                }
            }
            return repliedUserList.length + " of " +total;

        }

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
