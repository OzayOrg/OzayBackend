'use strict';

angular.module('ozayApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('manage', {
                abstract: true,
                parent: 'site',
            });
    });
