'use strict';

angular.module('ozayApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('organization', {
                parent: 'manage',
                url: '/management/organization',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'Management'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/management/management.html',
                        controller: 'AuditsController'
                    }
                },
                resolve: {

                }
            });
    });
