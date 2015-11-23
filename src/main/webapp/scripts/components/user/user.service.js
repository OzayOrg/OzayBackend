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
        var organizationId;

        return {
            getBuilding:function(){
                return building;
            },
            getBuildingList : function(){
                return buildingList;
            },
            getOrganizationId:function(){
                return organizationId;
            },
            setBuilding:function(newBuildingId){
                for(var i = 0; i<buildingList.length;i++){
                    if(newBuildingId ==buildingList[i].id){
                        building = buildingList[i];
                    }
                }
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

                        for(var i = 0; i<buildingList.length;i++){
                            if(cookieBuildingId ==buildingList[i].id){
                                building = buildingList[i];
                            }
                        }

                        if(building === undefined){
                            building = buildingList[0];
                        }
                        if(building !== undefined){
                            organizationId = building.organizationId;
                            $cookies.get('selectedOrganization', organizationId);
                        }


                        deferred.resolve(building);
                    })
                    .catch(function() {
                        building = undefined;
                        buildingList = [];
                        organizationId = null;
                        deferred.resolve(building);
                    });
                return deferred.promise;
            }
        };
    });
