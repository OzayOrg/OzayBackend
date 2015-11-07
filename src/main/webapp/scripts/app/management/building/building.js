'use strict';

angular.module('ozayApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('building-edit', {
                parent: 'manage',
                url: '/management/organization/:organizationId/building/:buildingId',
                data: {
                    authorities: ['ROLE_ADMIN', 'ORGANIZATION_HAS_ACCESS'],
                    pageTitle: 'Organization Top'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/management/building/building.edit.html',
                        controller: 'BuildingEditController'
                    }
                },
                resolve: {

                }
            })
          .state('building-new', {
                  parent: 'manage',
                  url: '/management/organization/:organizationId/building/new',
                  data: {
                      authorities: ['ROLE_ADMIN', 'ORGANIZATION_HAS_ACCESS'],
                      pageTitle: 'Organization Detail'
                  },
                  views: {
                      'content@': {
                          templateUrl: 'scripts/app/management/organization/organization.detail.html',
                          controller: 'BuildingEditController'
                      }
                  },
                  resolve: {

                  }
              });
    });
