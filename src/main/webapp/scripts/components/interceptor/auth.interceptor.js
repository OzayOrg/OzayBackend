'use strict';

angular.module('ozayApp')
    .factory('authInterceptor', function ($rootScope, $q, $location, $stateParams, localStorageService) {
        return {
            // Add authorization token to headers
            request: function (config) {
                config.headers = config.headers || {};
                var token = localStorageService.get('token');

                if (token && token.expires_at && token.expires_at > new Date().getTime()) {
                    config.headers.Authorization = 'Bearer ' + token.access_token;
                }
                var str = config.url;

                if(str.indexOf("api/building") == -1){
                    if($stateParams.organizationId !== undefined){
                        config.url+=config.url.indexOf('?') === -1 ? '?' : '&'
                        config.url += 'organization=' + $stateParams.organizationId;
                    }
                    if($stateParams.buildingId !== undefined){
                        config.url+=config.url.indexOf('?') === -1 ? '?' : '&'
                        config.url += 'building=' + $stateParams.buildingId;
                    }
                }
                return config;
            }
        };
    })
    .factory('authExpiredInterceptor', function ($rootScope, $q, $injector, localStorageService) {
        return {
            responseError: function (response) {
                // token has expired
                if (response.status === 401 && (response.data.error == 'invalid_token' || response.data.error == 'Unauthorized')) {
                    localStorageService.remove('token');
                    var Principal = $injector.get('Principal');
                    if (Principal.isAuthenticated()) {
                        var Auth = $injector.get('Auth');
                        Auth.authorize(true);
                    }
                }
                return $q.reject(response);
            }
        };
    });
