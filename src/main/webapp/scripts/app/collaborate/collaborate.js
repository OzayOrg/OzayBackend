'use strict';

angular.module('ozayApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('collaborate', {
                parent: 'site',
                url: '/collaborate',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'NOTIFICATION_POST'],
                    collaborateBtn:true,
                    pageTitle: 'Collaborate Create',
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/components/layout/default.html',
                        controller: 'CollaborateController'
                    },
                    'view@collaborate': {
                         templateUrl: 'scripts/app/collaborate/collaborate.html',
                    }
                },
                resolve: {

                }
            })
            .state('collaborate-record', {
                parent: 'site',
                url: '/collaborate-archive/:pageId',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'NOTIFICATION_GET'],
                    pageTitle: 'Notification Archive',
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/components/layout/default.html',
                        controller: 'NotificationRecordController'
                    },
                    'view@collaborate-record': {
                         templateUrl: 'scripts/app/collaborate/collaborate-record.html',
                    }
                },
                resolve: {

                }
            })
            .state('collaborate-track', {
                parent: 'site',
                url: '/collaborate-track/:pageId?search',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'NOTIFICATION_GET'],
                    pageTitle: 'Notification Tracker',
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/components/layout/default.html',
                        controller: 'NotificationTrackController'
                    },
                    'view@collaborate-track': {
                         templateUrl: 'scripts/app/collaborate/collaborate-track.html',
                    }
                },
                resolve: {

                }
            })
            .state('collaborate-record-detail', {
                parent: 'site',
                url: '/collaborate-archive/detail/{collaborateId:int}',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'NOTIFICATION_GET'],
                    pageTitle: 'Notification Archive Detail',
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/components/layout/default.html',
                        controller: 'NotificationRecordDetailController'
                    },
                    'view@collaborate-record-detail': {
                         templateUrl: 'scripts/app/collaborate/collaborate-record-detail.html',
                    }
                },
                resolve: {

                }
            });
    });
