'use strict';

angular.module('ozayApp')
    .controller('collaborateRecordDetailController', function($scope, $state, $stateParams, Page) {
        $scope.button = true;
        $scope.contentTitle = 'Collaborate Archives';

        Page.get({
            state: $state.current.name,
            id:$stateParams.collaborateId
        }).$promise.then(function(data) {
            $scope.collaborate = data.collaborate;
            $scope.collaborateRecords = data.collaborateRecords;
        });
    });
