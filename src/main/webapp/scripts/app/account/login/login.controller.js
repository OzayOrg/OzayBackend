'use strict';

angular.module('ozayApp')
    .controller('LoginController', function ($rootScope, $scope, $state, $timeout, $cookies, Auth, Building, UserInformation) {
        $scope.user = {};
        $scope.errors = {};

        $scope.rememberMe = true;
        $timeout(function (){angular.element('[ng-model="username"]').focus();});
        $scope.login = function (event) {
            event.preventDefault();
            Auth.login({
                username: $scope.username,
                password: $scope.password,
                rememberMe: $scope.rememberMe
            }).then(function () {
                $scope.authenticationError = false;

                Building.query().$promise.then(function(list) {
                        UserInformation.process(list, $cookies);
                    }, function(error){
                })
                .finally(function() {
                    if ($rootScope.previousStateName === 'register') {
                        $state.go('home');
                    } else {
                        $rootScope.back();
                    }
              });

            }).catch(function () {
                $scope.authenticationError = true;
            });
        };
    });
