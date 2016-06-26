'use strict';

angular.module('ozayApp')
    .controller('CollaborateTrackController', function($scope, $state, $stateParams, Page, UserInformation) {

        $scope.data = {};
        var pageLoaded = false;
        // Shared HTML file with collaborate.record.controller
        $scope.detailUrl = "collaborate-track-detail";
        $scope.track = true;
        $scope.data.currentPage = 1;



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
            $scope.collaborates = data.collaborateRecordDTOs; //this gets all the collaborates\
            $scope.data.currentPage = $stateParams.page !== undefined ? $stateParams.page : 1;
            pageLoaded = true;
        });
        $scope.setPage = function(pageNo) {
            $scope.data.currentPage = pageNo;
        };


    });
