/* globals $ */
'use strict';

angular.module('ozayApp')
    .directive('page-title', function() {
        return {
            restrict: 'E',
                templateUrl: '/scripts/components/page/page-title.html'
        };
    });
