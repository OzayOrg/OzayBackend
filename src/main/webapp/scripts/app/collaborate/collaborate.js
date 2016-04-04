'use strict';

angular.module('ozayApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('collaborate', {
                parent: 'site',
                url: '/collaborate',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'COLLABORATE_POST']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/collaborate/collaborate.html',
                        controller: 'CollaborateController'
                    }
                },
                resolve: {

                }
            })
            .state('collaborate-record', {
                parent: 'site',
                url: '/collaborate-archive/:pageId',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'COLLABORATE_GET']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/collaborate/collaborate-record.html',
                        controller: 'CollaborateRecordController'
                    }
                },
                resolve: {

                }
            })
            .state('collaborate-track', {
                parent: 'site',
                url: '/collaborate-track/:pageId?search',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'COLLABORATE_GET']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/collaborate/collaborate-track.html',
                        controller: 'CollaborateTrackController'
                    }
                },
                resolve: {

                }
            })
            .state('collaborate-record-detail', {
                parent: 'site',
                url: '/collaborate-archive/detail/{collaborateId:int}',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'COLLABORATE_GET']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/collaborate/collaborate-record-detail.html',
                        controller: 'CollaborateRecordDetailController'
                    }
                },
                resolve: {

                }
            });
    });
