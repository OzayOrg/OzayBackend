'use strict';

angular.module('ozayApp')
    .factory('Building', function ($resource) {
        return $resource('api/buildings', {}, {
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
    .service('SelectedBuilding', function() {
        var building = null ;
        var buildingList = [];
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
            if(this.getBuildingList().length){
                this.setBuilding(null);
                this.buildingList([]);
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
