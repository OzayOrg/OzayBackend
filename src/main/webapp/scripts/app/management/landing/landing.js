'use strict';

angular.module('ozayApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('management', {
                parent: 'manage',
                url: '/management',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'Health checks'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/management/landing/landing.html',
                        controller: 'HealthController'
                    }
                },
                resolve: {

                }
            });
    });
