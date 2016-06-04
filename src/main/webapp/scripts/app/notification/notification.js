'use strict';

angular.module('ozayApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('notification', {
                parent: 'site',
                url: '/notification',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'NOTIFICATION_POST'],
                    notificationBtn:true,
                    pageTitle: 'Notification',
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/components/layout/default.html',
                        controller: 'NotificationController'
                    },
                    'view@notification': {
                         templateUrl: 'scripts/app/notification/notification.html',
                    }
                },
                resolve: {

                }
            })
            .state('notification-record', {
                parent: 'site',
                url: '/notification-archive?page',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'NOTIFICATION_GET'],
                    pageTitle: 'Notification Archive',
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/components/layout/default.html',
                        controller: 'NotificationRecordController'
                    },
                    'view@notification-record': {
                         templateUrl: 'scripts/app/notification/notification-record.html',
                    }
                },
                resolve: {

                }
            })
            .state('notification-track', {
                parent: 'site',
                url: '/notification-track?page&search',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'NOTIFICATION_GET'],
                    pageTitle: 'Notification Tracker',
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/components/layout/default.html',
                        controller: 'NotificationTrackController'
                    },
                    'view@notification-track': {
                         templateUrl: 'scripts/app/notification/notification-track.html',
                    }
                },
                resolve: {

                }
            })
            .state('notification-record-detail', {
                parent: 'site',
                url: '/notification-archive/detail/{notificationId:int}',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'NOTIFICATION_GET'],
                    pageTitle: 'Notification Archive Detail',
                    cancelBtn:true,
                    cancel_url:""
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/components/layout/default.html',
                        controller: 'NotificationRecordDetailController'
                    },
                    'view@notification-record-detail': {
                         templateUrl: 'scripts/app/notification/notification-record-detail.html',
                    }
                },
                resolve: {

                }
            });
    });
