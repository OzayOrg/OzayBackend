'use strict';

angular.module('ozayApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('notification', {
                parent: 'default',
                url: '/notification',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'NOTIFICATION_POST']
                },
                views: {
                    'content@default': {
                        templateUrl: 'scripts/app/notification/notification.html',
                        controller: 'NotificationController'
                    }
                },
                resolve: {

                }
            })
            .state('notification-record', {
                parent: 'default',
                url: '/notification-archive/:pageId',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'NOTIFICATION_GET']
                },
                views: {
                    'content@default': {
                        templateUrl: 'scripts/app/notification/notification-record.html',
                        controller: 'NotificationRecordController'
                    }
                },
                resolve: {

                }
            })
            .state('notification-track', {
                parent: 'default',
                url: '/notification-track/:pageId?search',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'NOTIFICATION_GET']
                },
                views: {
                    'content@default': {
                        templateUrl: 'scripts/app/notification/notification-track.html',
                        controller: 'NotificationTrackController'
                    }
                },
                resolve: {

                }
            })
            .state('notification-record-detail', {
                parent: 'default',
                url: '/notification-archive/detail/{notificationId:int}',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'NOTIFICATION_GET']
                },
                views: {
                    'content@default': {
                        templateUrl: 'scripts/app/notification/notification-record-detail.html',
                        controller: 'NotificationRecordDetailController'
                    }
                },
                resolve: {

                }
            });
    });
