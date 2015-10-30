'use strict';

angular.module('ozayApp')
    .service('ozaySearchState', function() {
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
