'use strict';

angular.module('ozayApp')
    .config(function($stateProvider, MANAGE_ROLES) {
        $stateProvider
            .state('member', {
                parent: 'site',
                url: '/member',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'MEMBER_GET'],
                    pageTitle: 'Directory',
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/components/layout/default.html',
                        controller: 'MemberController'
                    },
                    'view@member': {
                        templateUrl: 'scripts/app/member/member.html',
                    }
                },
                resolve: {

                }
            })
            .state('member-edit', {
                parent: 'site',
                url: '/member/edit/{memberId:int}',
                data: {
                    pageTitle: 'Directory Edit',
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'MEMBER_PUT', 'MEMBER_DELETE', 'MEMBER_GET'],
                    memberBtn: true,
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/components/layout/default.html',
                        controller: 'MemberEditController'
                    },
                    'view@member-edit': {
                        templateUrl: 'scripts/app/member/member.edit.html',
                    }
                },
                resolve: {

                }
            })
            .state('member-new', {
                parent: 'site',
                url: '/member/new',
                data: {
                    pageTitle: 'Directory New',
                    authorities: ['ROLE_ADMIN', 'ROLE_SUBSCRIBER', 'MEMBER_POST', 'MEMBER_GET'],
                    memberBtn: true,
                },
                views: {
                    'content@':{
                        templateUrl: 'scripts/components/layout/default.html',
                        controller: 'MemberEditController'
                    },
                    'view@member-new': {
                        templateUrl: 'scripts/app/member/member.edit.html',
                    }
                },
                resolve: {

                }
            });
    });
