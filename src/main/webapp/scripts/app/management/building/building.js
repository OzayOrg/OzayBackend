'use strict';

angular.module('ozayApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('building-edit', {
                parent: 'manage',
                url: '/management/organization/{organizationId:int}/building/{buildingId:int}',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_ORGANIZATION_SUBSCRIBER', 'BUILDING_PUT'],
                    pageTitle: 'Building Edit',
                    defaultBtn: true,
                    defaultBtnAuthorities: 'BUILDING_PUT',
                    cancelUrl:"organization-detail({organizationId:$stateParams.organizationId})"
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/components/layout/default.html',
                        controller: 'BuildingEditController'
                    },
                    'view@building-edit': {
                        templateUrl: 'scripts/app/management/building/building.edit.html',
                    }
                },
                resolve: {

                }
            })
            .state('building-new', {
                parent: 'manage',
                url: '/management/organization/{organizationId:int}/building/new',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_ORGANIZATION_SUBSCRIBER', 'BUILDING_POST'],
                    pageTitle: 'Building New',
                    defaultBtn: true,
                    defaultBtnAuthorities: 'BUILDING_POST',
                    cancelUrl:"organization-detail({organizationId:$stateParams.organizationId})"
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/components/layout/default.html',
                        controller: 'BuildingEditController'
                    },
                    'view@building-new': {
                        templateUrl: 'scripts/app/management/building/building.edit.html',
                    }
                },
                resolve: {

                }
            });
    });
