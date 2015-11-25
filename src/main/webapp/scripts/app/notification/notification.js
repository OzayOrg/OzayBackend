'use strict';

angular.module('ozayApp')
    .config(function($stateProvider) {
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
            })
            .state('notification-record', {
                parent: 'site',
                url: '/notification-archive',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/notification/notification-record.html',
                        controller: 'NotificationRecordController'
                    }
                },
                resolve: {

                }
            })
            .state('notification-record-detail', {
                parent: 'site',
                url: '/notification-archive/{notificationId:int}',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/notification/notification-record-detail.html',
                        controller: 'NotificationRecordDetailController'
                    }
                },
                resolve: {

                }
            });
    });
