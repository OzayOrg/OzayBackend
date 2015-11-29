'use strict';

angular.module('ozayApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('member', {
                parent: 'site',
                url: '/member',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Directory'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/member/member.html',
                        controller: 'MemberController'
                    }
                },
                resolve: {

                }
            })
            .state('member-edit', {
                parent: 'site',
                url: '/member/edit/{memberId:int}',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/member/member.edit.html',
                        controller: 'MemberEditController'
                    }
                },
                resolve: {

                }
            })
            .state('member-new', {
                parent: 'site',
                url: '/member/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/member/member.edit.html',
                        controller: 'MemberEditController'
                    }
                },
                resolve: {

                }
            });
    });
