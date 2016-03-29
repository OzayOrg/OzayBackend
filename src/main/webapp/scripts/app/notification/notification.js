'use strict';

angular.module('ozayApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('AnalyticsPage', {
                parent: 'site',
                //url: '/notification',
                url:'/AnalyticsPage',

                views: {
                    'content@': {
                        //templateUrl: 'scripts/app/notification/notification.html',
                        //controller: 'NotificationController'
                        templateUrl: 'scripts/app/notification/AnalyticsPage.html'
                    }
                },
                resolve: {

                }
            })

            .state('notification-record', {
                parent: 'site',
                url: '/notification-archive/:pageId',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'NOTIFICATION_GET']
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
            .state('notification-track', {
                parent: 'site',
                url: '/notification-track',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'NOTIFICATION_GET']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/notification/notification-track.html',
                        controller: 'NotificationTrackController'
                    }
                },
                resolve: {

                }
            })
            .state('notification-record-detail', {
                parent: 'site',
                url: '/notification-archive/{notificationId:int}',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'NOTIFICATION_GET']
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
