'use strict';

angular.module('ozayApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('search', {
                parent: 'site',
                url: '/search?key',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'template@': {
                        templateUrl: 'scripts/app/search/search.html',
                        controller: 'SearchController'
                    }
                },
                resolve: {

                }
            });
    });
