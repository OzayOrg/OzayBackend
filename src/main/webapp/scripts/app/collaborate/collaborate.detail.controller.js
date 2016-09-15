'use strict';

angular.module('ozayApp')
    .controller('CollaborateDetailController', function(COLLABORATE_STATUS_CREATED, COLLABORATE_STATUS_COMPLETED, COLLABORATE_STATUS_CANCELED,  COLLABORATE_CALENDAR, COLLABORATE_MULTIPLE_CHOICE, COLLABORATE_RADIO,$scope, $state, $stateParams, MessageService, Page, UserInformation, Collaborate) {

        $scope.button = true;
        $scope.data = {};
        $scope.data.rsvpPost;
        $scope.status = '';
        $scope.completed = false;
        $scope.scheduledDate = null;
        $scope.collaborateFields = [];
        $scope.COLLABORATE_RADIO = COLLABORATE_RADIO;
        $scope.COLLABORATE_MULTIPLE_CHOICE = COLLABORATE_MULTIPLE_CHOICE;
        $scope.COLLABORATE_CALENDAR = COLLABORATE_CALENDAR;
        $scope.COLLABORATE_STATUS_CREATED = COLLABORATE_STATUS_CREATED;
        $scope.COLLABORATE_STATUS_COMPLETED = COLLABORATE_STATUS_COMPLETED;
        $scope.COLLABORATE_STATUS_CANCELED = COLLABORATE_STATUS_CANCELED;
        $scope.data.radio = null;
        //$scope.createDate=Collaborate.createdDate

        var successMessage = MessageService.getSuccessMessage();
        if(successMessage !== undefined){
            UserInformation.process();
            $scope.successTextAlert = successMessage;
        }

        var response = COLLABORATE_RADIO;
        var archived = false;


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

                for (var j = 0; j < data.collaborateFieldDTOs.length; j++) {
                    var date = data.collaborateFieldDTOs[j];
                    for (var i = 0; i < data.selectedIds.length; i++) {
                        var selectedId = data.selectedIds[i];
                        if (date.id === selectedId) {
                            data.collaborateFieldDTOs[j].selected = true;
                            if(data.collaborate.response == COLLABORATE_RADIO){
                                $scope.data.radio = date.id;
                            }
                        }
                    }
                }

                $scope.collaborateFields = data.collaborateFieldDTOs;

                $scope.collaborate = data.collaborate;
                archived = data.archived;
                $scope.data.archived = archived;

                $scope.data.creator = data.creator;
                if(archived == false && data.creator && data.collaborate.status != COLLABORATE_STATUS_COMPLETED){
                    $scope.showCancelBtn = true;
                }


                if(archived == false && data.collaborate.status == COLLABORATE_STATUS_CREATED){
                    $scope.status = 'Ongoing';
                } else if(archived == true && data.collaborate.status == COLLABORATE_STATUS_CREATED){
                    $scope.status = 'Expired';
                }else if(data.collaborate.status == COLLABORATE_STATUS_COMPLETED){
                    $scope.status = 'Completed';
                    if(data.collaborate.response == COLLABORATE_CALENDAR){
                        if(data.collaborateFieldDTO != null){
                            $scope.scheduledDate = data.collaborateFieldDTO.issueDate;
                        }
                    }
                }else if(data.collaborate.status == COLLABORATE_STATUS_CANCELED){
                    $scope.status = 'Canceled';
                }
            });
        }
        $scope.radioCheck = function(value) {

            if(archived == false && $scope.collaborate.status == COLLABORATE_STATUS_CREATED){
                $scope.data.radio = value;
            }
        }

        $scope.multiCheck = function(obj){
            if(archived == false && $scope.collaborate.status == COLLABORATE_STATUS_CREATED){
                obj.selected = !obj.selected;
            }
        }

        $scope.getData();

        $scope.updateStatus = function(method){

            if(method == 'complete' && response == COLLABORATE_CALENDAR){
               $scope.collaborate.collaborateFieldId = $scope.data.postDate;
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
            if(archived == true || $scope.collaborate.status !== COLLABORATE_STATUS_CREATED){
                alert("You cannot take action to this item anymore.");
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
                if (response == COLLABORATE_RADIO) {
                    for(var i = 0; i < $scope.collaborateFields.length; i++){
                        if($scope.collaborateFields[i].id == $scope.data.radio ){
                            tracks.push({
                                collaborateFieldId: $scope.data.radio,
                                selected: $scope.data.radio
                            });
                        } else {
                            tracks.push({
                                collaborateFieldId: $scope.collaborateFields[i].id,
                                selected: false
                            });
                        }
                    }

                } else if (response == COLLABORATE_MULTIPLE_CHOICE || response == COLLABORATE_CALENDAR) {

                    for (var i = 0; i < $scope.collaborateFields.length; i++) {
                        var item = $scope.collaborateFields[i];
                        if (item.selected === undefined) {
                            item.selected = null;
                        }
                        tracks.push({
                            collaborateFieldId: item.id,
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
