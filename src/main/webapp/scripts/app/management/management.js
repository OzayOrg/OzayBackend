'use strict';

angular.module('ozayApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('manage', {
                abstract: true,
                parent: 'default',
                views: {
                    'title@': {
                        templateUrl: 'scripts/components/layout/title.html',
                    }
                }
            });
    });
