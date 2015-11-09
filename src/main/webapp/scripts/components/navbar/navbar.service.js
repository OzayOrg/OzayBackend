'use strict';

angular.module('ozayApp')
    .service('MenuSearchState', function() {
        var searchBtnState = false;
        this.getState = function(){
            return searchBtnState;
        }
        this.changeState = function(){
            if(searchBtnState == false){
                searchBtnState = true;
            } else {
                searchBtnState = false;
            }
        }
    });


angular.module('ozayApp')
    .service('SelectedBuilding', function() {
        var building = null ;
        var buildingList = [];

        this.clear = function(){
            building = null;
            buildingList = [];
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
