'use strict';

angular.module('ozayApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('collaborate-create', {
                parent: 'site',
                url: '/collaborate',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'COLLABORATE_POST'],
                    defaultBtn:true,
                    defaultBtnAuthorities: 'COLLABORATE_POST',
                    pageTitle: 'Collaborate Create',
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/components/layout/default.html',
                        controller: 'CollaborateController'
                    },
                    'view@collaborate-create': {
                         templateUrl: 'scripts/app/collaborate/collaborate.html',
                    }
                },
                resolve: {

                }
            })
            .state('collaborate-record', {
                parent: 'site',
                url: '/collaborate-archive?page',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'ROLE_USER'],
                    pageTitle: 'Collaborate Archive',
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/components/layout/default.html',
                        controller: 'CollaborateRecordController'
                    },
                    'view@collaborate-record': {
                         templateUrl: 'scripts/app/collaborate/collaborate-list.html',
                    }
                },
                resolve: {

                }
            })
            .state('collaborate-track', {
                parent: 'site',
                url: '/collaborate-track?page',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'ROLE_USER'],
                    pageTitle: 'Collaborate Tracker',
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/components/layout/default.html',
                        controller: 'CollaborateTrackController'
                    },
                    'view@collaborate-track': {
                         templateUrl: 'scripts/app/collaborate/collaborate-list.html',
                    }
                },
                resolve: {

                }
            })
            .state('collaborate-track-detail', {
                parent: 'site',
                url: '/collaborate-track/detail/{collaborateId:int}',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'ROLE_USER'],
                    pageTitle: 'Collaborate Tracker Detail',
                    collaborateTrackBtn:true,
                    collaborateTrackBtnAuthorities: 'ROLE_USER',
                    cancelUrl:"collaborate-track"
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/components/layout/default.html',
                        controller: 'CollaborateDetailController'
                    },
                    'view@collaborate-track-detail': {
                         templateUrl: 'scripts/app/collaborate/collaborate-detail.html',
                    }
                },
                resolve: {

                }
            })
            .state('collaborate-record-detail', {
                parent: 'site',
                url: '/collaborate-archive/detail/{collaborateId:int}',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'ROLE_USER'],
                    pageTitle: 'Collaborate Archive Detail',
                    cancelBtn:true,
                    cancelUrl:"collaborate-record"
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/components/layout/default.html',
                        controller: 'CollaborateDetailController'
                    },
                    'view@collaborate-record-detail': {
                         templateUrl: 'scripts/app/collaborate/collaborate-detail.html',
                    }
                },
                resolve: {

                }
            });
    });
