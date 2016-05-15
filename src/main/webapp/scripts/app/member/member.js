'use strict';

angular.module('ozayApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('member', {
                parent: 'default',
                url: '/member',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'MEMBER_GET'],
                    pageTitle: 'Directory'
                },
                views: {
                    'content@default': {
                        templateUrl: 'scripts/app/member/member.html',
                        controller: 'MemberController'
                    }
                },
                resolve: {

                }
            })
            .state('member-edit', {
                parent: 'default',
                url: '/member/edit/{memberId:int}',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'MEMBER_PUT', 'MEMBER_DELETE', 'MEMBER_GET'],
                },
                views: {
                    'content@default': {
                        templateUrl: 'scripts/app/member/member.edit.html',
                        controller: 'MemberEditController'
                    }
                },
                resolve: {

                }
            })
            .state('member-new', {
                parent: 'default',
                url: '/member/new',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'MEMBER_POST', 'MEMBER_GET'],
                },
                views: {
                    'content@default': {
                        templateUrl: 'scripts/app/member/member.edit.html',
                        controller: 'MemberEditController'
                    }
                },
                resolve: {

                }
            });
    });
