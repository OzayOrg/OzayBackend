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
            }).state('management_new', {
                              parent: 'manage',
                              url: '/management/organization/new',
                              data: {
                                  authorities: ['ROLE_ADMIN', 'ORGANIZATION_HAS_ACCESS'],
                                  pageTitle: 'Organization Top'
                              },
                              views: {
                                  'content@': {
                                      templateUrl: 'scripts/app/management/organization/organization.edit.html',
                                      controller: 'OrganizationController'
                                  }
                              },
                              resolve: {

                              }
                          });
    });
