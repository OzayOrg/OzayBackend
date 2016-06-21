'use strict';

angular.module('ozayApp')
    .controller('CollaborateDetailController', function($scope, $state, $stateParams, MessageService, Page, UserInformation, Collaborate) {

        $scope.button = true;
        $scope.data = {};
        $scope.data.rsvpPost;
        $scope.status = '';
        $scope.completed = false;
        $scope.scheduledDate = null;

        var successMessage = MessageService.getSuccessMessage();
        if(successMessage !== undefined){
            UserInformation.process();
            $scope.successTextAlert = successMessage;
        }

        var response = 1;
        var archived = false;

        $scope.rsvpList = [{
            label: "Yes",
            value: true,
            counter : 0
        }, {
            label: "No",
            value: false,
            counter : 0
        }, ]

        $scope.getData = function() {
            Page.get({
                state: "collaborate-detail",
                id: $stateParams.collaborateId
            }).$promise.then(function(data) {

                if(data.archived == false && $state.current.name == 'collaborate-record-detail'){
                    $state.go('collaborate-track-detail', {collaborateId:data.collaborate.id});
                } else if(data.archived == true && $state.current.name == 'collaborate-track-detail'){
                    $state.go('collaborate-record-detail', {collaborateId:data.collaborate.id});
                }
                response = data.collaborate.response;

                for (var j = 0; j < data.collaborate.collaborateDates.length; j++) {
                    var yesCounter = 0;
                    var noCounter = 0;
                    var date = data.collaborate.collaborateDates[j];
                    for (var i = 0; i < data.selectedIds.length; i++) {
                        var selectedId = data.selectedIds[i];
                        if (date.id === selectedId) {
                            data.collaborate.collaborateDates[j].selected = true;
                        }
                    }

                    for (var k = 0; k < date.collaborateMembers.length; k++) {
                        var selected = date.collaborateMembers[k].selected;

                        if (selected === true) {
                            yesCounter++
                        } else if (selected === false) {
                            noCounter++
                        }
                    }

                    if(response == 1){
                         $scope.rsvpList[0].counter = yesCounter;
                         $scope.rsvpList[1].counter = noCounter;
                         if(data.firstEdit === false){
                            $scope.data.rsvp = data.selectedIds.length ? true : false;
                         }

                         if(data.collaborate.collaborateDateId !== undefined && data.collaborate.collaborateDateId != null){
                            $scope.data.rsvpPost = true;
                            $scope.completed = true;
                         }
                         $scope.scheduledDate = data.collaborate.collaborateDates[0].issueDate;
                    }
                    else if(response == 2){
                        if(data.collaborate.collaborateDateId ){
                            $scope.completed = true;
                            $scope.scheduledDate = date.issueDate;
                        }
                        data.collaborate.collaborateDates[j].yesCounter = yesCounter;
                        data.collaborate.collaborateDates[j].noCounter = noCounter;
                    }
                }
                $scope.collaborate = data.collaborate;
                archived = data.archived;
                $scope.data.archived = archived;

                $scope.data.creator = data.creator;
                if(archived == false && data.creator && data.collaborate.status != 2){
                    $scope.showCancelBtn = true;
                }

                if(archived == false && data.collaborate.status == 1){
                    $scope.status = 'Ongoing';
                } else if(archived == true && data.collaborate.status == 1){
                    $scope.status = 'Expired';
                }else if(data.collaborate.status == 2){
                    $scope.status = 'Completed';
                }else if(data.collaborate.status == 3){
                    $scope.status = 'Canceled';
                }
            });
        }
        $scope.rsvpCheck = function(value) {

            if(archived == false && $scope.completed == false){
            //data.rsvp = obj.value;
                $scope.data.rsvp = value;
            }
        }

        $scope.calendarCheck = function(obj){
            if(archived == false && $scope.collaborate.status == 0){
                obj.selected = !obj.selected;
            }
        }

        $scope.getData();

        $scope.updateStatus = function(method){
            if(method == 'complete' && response == 1){
                if($scope.data.rsvpPost === undefined){
                    alert('Please select Yes or No');
                    return false;
                }
                else {
                    if($scope.data.rsvpPost !== true){
                        method = 'cancel';
                    }
                }
            }
            if(method == 'complete' && response == 2){
               $scope.collaborate.collaborateDateId = $scope.data.postDate;

            }

            $scope.successTextAlert = null;
            $scope.errorTextAlert = null;
            if (archived == true) {
                return false;
            }

            if (confirm("Would you like to proceed?")) {
                Collaborate.update({method:method}, $scope.collaborate, function(data) {
                    if(method == 'cancel'){
                        MessageService.setSuccessMessage('Successfully updated');
                        $state.go('collaborate-record-detail', {collaborateId:$stateParams.collaborateId});
                    } else {
                        MessageService.setSuccessMessage('Successfully updated');
                        $state.reload();
                    }

                }, function(error) {

                    if(error.data.message !== undefined){
                        $scope.errorTextAlert = error.data.message;
                    } else {
                        $scope.errorTextAlert = "Error! Please try later.";
                    }

                }).$promise.finally(function() {
                    $scope.button = true;
                });
            }
        }



        $scope.submit = function() {
            if($scope.completed == true){
                alert("The status is completed. Cannot proceed.");
                return false;
            }
            $scope.successTextAlert = null;
            $scope.errorTextAlert = null;

            if (archived == true) {
                return false;
            }

            if (confirm("Would you like to proceed?")) {
                $scope.button = false;
                var tracks = [];
                if (response == 1) {
                    tracks.push({
                        collaborateDateId: $scope.collaborate.collaborateDates[0].id,
                        selected: $scope.data.rsvp
                    });
                } else if (response == 2) {

                    for (var i = 0; i < $scope.collaborate.collaborateDates.length; i++) {
                        var item = $scope.collaborate.collaborateDates[i];
                        if (item.selected === undefined) {
                            item.selected = null;
                        }
                        tracks.push({
                            collaborateDateId: item.id,
                            selected: item.selected
                        });
                    }
                }

                var form = {
                    collaborateId: $scope.collaborate.id,
                    response: $scope.collaborate.response,
                    collaborateTrackField: tracks
                };
                Collaborate.update(form, function(data) {
                    $scope.getData();
                    $scope.successTextAlert = "Successfully updated";
                    $scope.success = true;
                }, function(error) {

                    if(error.data.message !== undefined){
                        $scope.errorTextAlert = error.data.message;
                    } else {
                        $scope.errorTextAlert = "Error! Please try later.";
                    }

                }).$promise.finally(function() {
                    $scope.button = true;
                });

            }
        }
    });
