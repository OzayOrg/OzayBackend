'use strict';

angular.module('ozayApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('role', {
                parent: 'manage',
                url: '/management/organization/organizationId{organizationId:int}/building/{buildingId:int}/role',
                data: {
                    authorities: ['ROLE_ADMIN', 'ORGANIZATION_HAS_ACCESS'],
                    pageTitle: 'Role List'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/management/role/role.html',
                        controller: 'RoleController'
                    }
                },
                resolve: {

                }
            })
           .state('role-edit', {
                          parent: 'manage',
                          url: '/management/organization/organizationId{organizationId:int}/building/{buildingId:int}/role/edit/{roleId:int}',
                          data: {
                              authorities: ['ROLE_ADMIN', 'ORGANIZATION_HAS_ACCESS'],
                              pageTitle: 'Role Edit'
                          },
                          views: {
                              'content@': {
                                  templateUrl: 'scripts/app/management/role/role.edit.html',
                                  controller: 'RoleEditController'
                              }
                          },
                          resolve: {

                          }
                      })
          .state('role-new', {
                  parent: 'manage',
                  url: '/management/organization/organizationId{organizationId:int}/building/{buildingId:int}/role/new',
                  data: {
                      authorities: ['ROLE_ADMIN', 'ORGANIZATION_HAS_ACCESS'],
                      pageTitle: 'Role New'
                  },
                  views: {
                      'content@': {
                          templateUrl: 'scripts/app/management/role/role.edit.html',
                          controller: 'RoleEditController'
                      }
                  },
                  resolve: {

                  }
              });
    });
