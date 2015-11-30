package com.ozay.backend.security;

import com.ozay.backend.repository.PermissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RequestInterceptor extends HandlerInterceptorAdapter {

    private final Logger log = LoggerFactory.getLogger(RequestInterceptor.class);

    @Inject
    private PermissionRepository permissionRepository;

    private Long buildingId;
    private Long organizationId;
    private String method;
    private HttpServletRequest request;

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("!!!!!INTERCEPTOR!!!!");


        if(SecurityUtils.getCurrentLogin() == null){
            System.out.println("!!!!!NOT LOGGED IN!!!!");
            return false;
        }
        else if(SecurityUtils.isUserInRole("ROLE_ADMIN")){
            System.out.println("!!!!!ADMIN!!!!");
            return true;
        }

        if(request.getServletPath().equals("/api/building") && request.getMethod().toUpperCase().equals("GET")){
            System.out.println("!!!!!Building GET!!!!");
            return true;
        }

        this.request = request;
        boolean result = this.validation();

        if(result == false){
            System.out.println("!!!!!Interceptor false!!!!");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
        System.out.println("!!!!!Interceptor true!!!!");
        return true;
    }

    private boolean validation(){

        if(this.parameterCheck(this.request) == false){
            return false;
        }

        this.method = this.request.getMethod().toLowerCase();
        System.out.println("Method: " + this.method);
        String path = this.request.getServletPath();
        System.out.println(path);
        String[] pathComponents = path.split("/");
        if(pathComponents.length < 2){
            return false;
        }
        for(String item : pathComponents){
            System.out.println(item);
        }
        String apiName = pathComponents[2];
        System.out.println("Api Name: " + apiName);

        if(apiName.equals("page") == true && this.method.equals("GET")){
            if(pathComponents.length < 4){
                return false;
            }
            System.out.println("Page true");
            return validatePageRequest(pathComponents[3]);
        } else {
            System.out.println("Else true");
            return true;
        }
//        return true;
    }

    private boolean validatePageRequest(String path){
        if(path.equals("organization") || path.equals("organization-detail")){
            System.out.println("!!!!!Organization page special access!!!!");
        }
        if(path.contains("-") == true){
            String[] splits = path.split("-");
            path = splits[0];
        }
        // notification exception
        if(path.equals("notification") == true){
            if(this.request.getServletPath().equals("/api/page/notification") == true){
                return this.checkPermission("NOTIFICATION_POST");
            } else {
                return this.checkPermission("NOTIFICATION_GET");
            }
        }

        return true;
    }

    private boolean checkPermission(String permission) {
        if(organizationId != null){
            System.out.println("!!!!!Organization key null!!!!" + permission);
            return true;
        } else {
            System.out.println("!!!!!building Interceptor check!!!!" + permission);
            return this.memberPermissionCheck(this.buildingId, permission);
        }
    }

    private boolean memberPermissionCheck(Long buildingId, String permission) {
        System.out.println("!!!!!Interceptor key!!!!" + permission);
        return permissionRepository.validateMemberInterceptor(buildingId, permission);
    }

    private boolean parameterCheck(HttpServletRequest request) {
        Long buildingId = this.parseNumber(request.getParameter("building"));
        Long organizationId = this.parseNumber(request.getParameter("organization"));

        if((buildingId == null || buildingId == 0) && (organizationId == null || organizationId == 0 )){
            log.debug("False Intercepting BuildingID is null: " + request.getServletPath());
            return false;
        }
        this.buildingId = buildingId;
        this.organizationId = organizationId;
        return true;
    }

    private Long parseNumber(String value){
        Long temp = null;
        try {
            temp = Long.parseLong(value);

        } catch(Exception e){

        }
        return temp;
    }


}
