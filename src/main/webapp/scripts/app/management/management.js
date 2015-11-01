'use strict';

angular.module('ozayApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('manage', {
                abstract: true,
                parent: 'site',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
            });
    });
