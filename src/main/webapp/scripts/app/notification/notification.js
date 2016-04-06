'use strict';

angular.module('ozayApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('xsectional', {
                parent: 'site',
                //url: '/notification',
                url:'/xsectional',

                views: {
                    'content@': {
                        //templateUrl: 'scripts/app/notification/notification.html',
                        //controller: 'NotificationController'
                        templateUrl: 'scripts/app/notification/xsectional.html'
                    }
                },
                resolve: {

                }
            })

            .state('mean-reversion', {
                parent: 'site',
                //url: '/notification',
                url:'/mean-reversion',

                views: {
                    'content@': {
                        //templateUrl: 'scripts/app/notification/notification.html',
                        //controller: 'NotificationController'
                        templateUrl: 'scripts/app/notification/mean-reversion.html'
                    }
                },
                resolve: {

                }
            })

            .state('historical', {
                parent: 'site',
                //url: '/notification',
                url:'/historical',

                views: {
                    'content@': {
                        //templateUrl: 'scripts/app/notification/notification.html',
                        //controller: 'NotificationController'
                        templateUrl: 'scripts/app/notification/historical.html'
                    }
                },
                resolve: {

                }
            })

            .state('short-interest', {
                parent: 'site',
                //url: '/notification',
                url:'/short-interest',

                views: {
                    'content@': {
                        //templateUrl: 'scripts/app/notification/notification.html',
                        //controller: 'NotificationController'
                        templateUrl: 'scripts/app/notification/short-interest.html'
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
