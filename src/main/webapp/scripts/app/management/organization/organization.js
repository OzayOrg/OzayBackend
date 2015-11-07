'use strict';

angular.module('ozayApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('management', {
                parent: 'manage',
                url: '/management',
                data: {
                    authorities: ['ROLE_ADMIN', 'ORGANIZATION_HAS_ACCESS'],
                    pageTitle: 'Organization Top'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/management/organization/organization.html',
                        controller: 'OrganizationController'
                    }
                },
                resolve: {

                }
            }).state('management-new', {
                  parent: 'manage',
                  url: '/management/organization/new',
                  data: {
                      authorities: ['ROLE_ADMIN', 'ORGANIZATION_HAS_ACCESS'],
                      pageTitle: 'Organization Top'
                  },
                  views: {
                      'content@': {
                          templateUrl: 'scripts/app/management/organization/organization.edit.html',
                          controller: 'OrganizationEditController'
                      }
                  },
                  resolve: {

                  }
              })
              .state('management-edit', {
                    parent: 'manage',
                    url: '/management/organization/edit/:organizationId',
                    data: {
                        authorities: ['ROLE_ADMIN', 'ORGANIZATION_HAS_ACCESS'],
                        pageTitle: 'Organization Edit'
                    },
                    views: {
                        'content@': {
                            templateUrl: 'scripts/app/management/organization/organization.edit.html',
                            controller: 'OrganizationEditController'
                        }
                    },
                    resolve: {

                    }
                })
                .state('organization-detail', {
                      parent: 'manage',
                      url: '/management/organization/:organizationId',
                      data: {
                          authorities: ['ROLE_ADMIN', 'ORGANIZATION_HAS_ACCESS'],
                          pageTitle: 'Organization Detail'
                      },
                      views: {
                          'content@': {
                              templateUrl: 'scripts/app/management/organization/organization.detail.html',
                              controller: 'OrganizationDetailController'
                          }
                      },
                      resolve: {

                      }
                  });
    });
