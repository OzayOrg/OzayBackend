'use strict';

angular.module('ozayApp')
    .factory('Collaborate', function ($resource) {
        return $resource('api/collaborate/:id', {}, {
                'query': {method: 'GET', isArray: true},
                'get': {
                    method: 'GET',
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                },
                'update': { method:'PUT' }
            });
        });
