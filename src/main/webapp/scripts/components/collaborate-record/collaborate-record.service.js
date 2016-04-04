'use strict';

angular.module('ozayApp')
    .factory('CollaborateRecord', function ($resource) {
        return $resource('api/collaborate-record/:id', {}, {
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
