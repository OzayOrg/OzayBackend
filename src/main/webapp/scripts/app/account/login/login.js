'use strict';

angular.module('ozayApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('login', {
                url: '/login',
                data: {
                    authorities: [],
                    pageTitle: 'Sign in'
                },
                views: {
                    'login@': {
                        templateUrl: 'scripts/app/account/login/login.html',
                        controller: 'LoginController'
                    }
                },
                resolve: {

                }
            });
    });
