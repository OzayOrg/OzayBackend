'use strict';

angular.module('ozayApp')
    .controller('EventsController', function ($scope, $state) {
        $scope.pageTitle = 'Events';
        $scope.contentTitle = 'Events';
        $scope.example1model = [];
        $scope.events = [{id: 1, label: "David"}, {id: 2, label: "Jhon"}, {id: 3, label: "Danny"}];
    });

