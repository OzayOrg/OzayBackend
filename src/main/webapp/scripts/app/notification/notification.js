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




            .state('alpha-theory', {
                parent: 'site',
                //url: '/notification',
                url:'/alpha-theory',

                views: {
                    'content@': {
                        //templateUrl: 'scripts/app/notification/notification.html',
                        //controller: 'NotificationController'
                        templateUrl: 'scripts/app/notification/alpha-theory.html'
                    }
                },
                resolve: {

                }
            })

            .state('light-keeper', {
                parent: 'site',
                //url: '/notification',
                url:'/light-keeper',

                views: {
                    'content@': {
                        //templateUrl: 'scripts/app/notification/notification.html',
                        //controller: 'NotificationController'
                        templateUrl: 'scripts/app/notification/light-keeper.html'
                    }
                },
                resolve: {

                }
            })

            .state('portfolio-tearsheets', {
                parent: 'site',
                //url: '/notification',
                url:'/portfolio-tearsheets',

                views: {
                    'content@': {
                        //templateUrl: 'scripts/app/notification/notification.html',
                        //controller: 'NotificationController'
                        templateUrl: 'scripts/app/notification/portfolio-tearsheets.html'
                    }
                },
                resolve: {

                }
            })

             .state('earnings-tracker', {
                     parent: 'site',
                     //url: '/notification',
                     url:'/earnings-tracker',

                     views: {
                         'content@': {
                             //templateUrl: 'scripts/app/notification/notification.html',
                             //controller: 'NotificationController'
                             templateUrl: 'scripts/app/notification/earnings-tracker.html'
                         }
                     },
                     resolve: {

                     }
                 })
    });
