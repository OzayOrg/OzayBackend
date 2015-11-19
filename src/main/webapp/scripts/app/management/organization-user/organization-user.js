'use strict';

angular.module('ozayApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('organization-user', {
                parent: 'manage',
                url: '/management/organization/{organizationId:int}/building/{buildingId:int}/organization-user',
                data: {
                    authorities: ['ROLE_ADMIN', 'ORGANIZATION_HAS_ACCESS'],
                    pageTitle: 'Organization User List'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/management/organization-user/organization-user.html',
                        controller: 'OrganizationUserController'
                    }
                },
                resolve: {

                }
            })
            .state('role-edit', {
                parent: 'manage',
                url: '/management/organization/{organizationId:int}/building/{buildingId:int}/organization-user/edit/{organizationUserId:int}',
                data: {
                    authorities: ['ROLE_ADMIN', 'ORGANIZATION_HAS_ACCESS'],
                    pageTitle: 'Organization User Edit'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/management/organization-user/organization-user.edit.html',
                        controller: 'OrganizationUserEditController'
                    }
                },
                resolve: {

                }
            })
            .state('role-new', {
                parent: 'manage',
                url: '/management/organization/{organizationId:int}/building/{buildingId:int}/organization-user/new',
                data: {
                    authorities: ['ROLE_ADMIN', 'ORGANIZATION_HAS_ACCESS'],
                    pageTitle: 'Organization User New'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/management/organization-user/organization-user.edit.html',
                        controller: 'OrganizationUserEditController'
                    }
                },
                resolve: {

                }
            });
    });
