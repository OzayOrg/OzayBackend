'use strict';

angular.module('ozayApp')
    .factory('User', function ($resource) {
        return $resource('api/users/:login', {}, {
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


angular.module('ozayApp')
    .service('UserInformation', function UserInformation($q, $cookies, Building) {
        var building;
        var buildingList = [];
        var organization = null;

        return {
            getBuilding:function(){
                return building;
            },
            getBuildingList : function(){
                return buildingList;
            },
            process: function () {
                var deferred = $q.defer();

                if (angular.isDefined(building)) {
                    deferred.resolve(building);
                    return deferred.promise;
                }
                // retrieve the identity data from the server, update the identity object, and then resolve.
                Building.query().$promise
                    .then(function (list) {
                        if(list.length == 0){
                            building = undefined;
                            buildingList = [];
                            deferred.resolve(building);
                            return deferred.promise;
                        }

                        buildingList = list;

                        var cookieBuildingId = $cookies.get('selectedBuilding');

                        if(building === undefined){
                            building = buildingList[0].id;
                        }


                        deferred.resolve(building);
                    })
                    .catch(function() {
                        building = undefined;
                        buildingList = [];
                        organization = null;
                        deferred.resolve(building);
                    });
                return deferred.promise;
            }
        };
    });




//angular.module('ozayApp')
//    .service('UserInformation', function UserInformation($q, $cookies, Building) {
//        var building = null ;
//        var buildingList = [];
//        var organization = null;
//        return {
//            clear:function(){
//                building = null;
//                buildingList = [];
//                organization = null;
//            },
//            getOrganization: function(){
//                return organization;
//            },
//            setOrganization: function(organizationId){
//                organization = organizationId;
//            },
//            getBuilding: function(){
//                return building;
//            },
//            setBuilding : function(buildingId){
//                building = buildingId;
//            },
//            getBuildingList: function(){
//                return buildingList;
//            },
//            setBuildingList:function(list){
//                buildingList = list;
//            },
//
//
//            processcopy: function (force) {
//                var deferred = $q.defer();
//
//                if (force === true) {
//                    building = undefined;
//                }
//
//                // check and see if we have retrieved the identity data from the server.
//                // if we have, reuse it by immediately resolving
//                if (angular.isDefined(building)) {
//                    deferred.resolve(building);
//                    return deferred.promise;
//                }
//
//                // retrieve the identity data from the server, update the identity object, and then resolve.
//                 Building.query().$promise
//                    .then(function (list) {
//                        building = 1;
//                        buildingList = list;
//                        deferred.resolve(building);
//                    })
//                    .catch(function() {
//                        building = undefined;
//                        buildingList = [];
//                        deferred.resolve(building);
//                    });
//                return deferred.promise;
//            },
//
//            copy : function(){
//                var deferred = $q.defer();
//
//
//
//                if(buildingList.length == 0){
//                    building = undefined;
//                    buildingList = [];
//                    $cookies.remove('selectedBuilding');
//                }
//                if (angular.isDefined(building)) {
//                    deferred.resolve(building);
//                    return deferred.promise;
//                }
//
//                var cookieBuildingId = $cookies.get('selectedBuilding');
//
//                Building.query().$promise
//                    .then(function (list) {
//                        buildingList = list;
//                        if(cookieBuildingId === undefined || cookieBuildingId == null){
//                            if(buildingList.length > 0){
//                                building = list[0].id;
//                                $cookies.put('selectedBuilding', building);
//                            }
//                        }else{
//                            for(var i = 0; i < this.getBuildingList().length;i++){
//                                if(buildingId[i].id == cookieBuildingId){
//                                    building = buildingId[i].id;
//                                    $cookies.put('selectedBuilding', building);
//                                    break;
//                                }
//                            }
//                        }
//                    })
//                    .catch(function() {
//                        building = null;
//                        buildingList = [];
//                        $cookies.remove('selectedBuilding');
//                    });
//            console.log(deferred.promise);
//            return deferred.promise;
//            }
//        };
//    });
