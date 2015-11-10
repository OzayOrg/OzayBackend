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
    .service('UserInformation', function() {
        var building = null ;
        var buildingList = [];
        var organization = null;

        this.clear = function(){
            building = null;
            buildingList = [];
            organization = null;
        }
        this.getOrganization = function(){
            return organization;
        }
        this.setOrganization = function(organizationId){
            organization = organizationId;
        }

        this.getBuilding = function(){
            return building;
        }
        this.setBuilding = function(buildingId){
            building = buildingId;
        }
        this.getBuildingList = function(){
            return buildingList;
        }
        this.setBuildingList = function(list){
            buildingList = list;
        }
        this.process = function(list, cookie){
            if(this.getBuildingList().length == 0){
                this.setBuilding(null);
                this.setBuildingList([]);
                cookie.remove('selectedBuilding');
            }
            var cookieBuildingId = cookie.get('selectedBuilding');
            this.setBuildingList(list);

            if(cookieBuildingId === undefined || cookieBuildingId == null){
                if(this.getBuildingList().length > 0){
                    this.setBuilding(list[0].id);
                    cookie.put('selectedBuilding', building);
                }
            }else{
                for(var i = 0; i < this.getBuildingList().length;i++){
                    if(this.getBuildingList()[i].id == cookieBuildingId){
                        this.setBuilding(this.getBuildingList()[i].id);
                        cookie.put('selectedBuilding', building);
                        break;
                    }
                }
            }
        }
    });
