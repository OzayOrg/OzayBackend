'use strict';

angular.module('ozayApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('management', {
                parent: 'manage',
                url: '/management',
                data: {
                    authorities: ['ROLE_ADMIN', 'ORGANIZATION_HAS_ACCESS'],
                    pageTitle: 'Organization Top'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/management/landing/landing.html',
                        controller: 'OrganizationController'
                    }
                },
                resolve: {

                }
            });
    });
