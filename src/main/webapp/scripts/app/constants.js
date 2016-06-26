"use strict";
// DO NOT EDIT THIS FILE, EDIT THE GRUNT TASK NGCONSTANT SETTINGS INSTEAD WHICH GENERATES THIS FILE
angular.module('ozayApp')

.constant('ADMIN_ROLES', 'ROLE_ADMIN')

.constant('MANAGE_ROLES', ['ROLE_ADMIN', 'ROLE_SUBSCRIBER'])
.constant('COLLABORATE_RADIO', 1)
.constant('COLLABORATE_MULTIPLE_CHOICE', 2)
.constant('COLLABORATE_CALENDAR', 3)
.constant('COLLABORATE_STATUS_CREATED', 1)
.constant('COLLABORATE_STATUS_COMPLETED', 2)
.constant('COLLABORATE_STATUS_CANCELED', 3)
;
