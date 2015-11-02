'use strict';

angular.module('ozayApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('notification', {
                parent: 'site',
                url: '/notification',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/notification/notification.html',
                        controller: 'NotificationController'
                    }
                },
                resolve: {

                }
            });
    });
