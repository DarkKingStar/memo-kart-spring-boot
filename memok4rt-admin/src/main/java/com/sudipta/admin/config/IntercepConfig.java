package com.sudipta.admin.config;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.*;

import com.sudipta.admin.response.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.*;


@Component
public class IntercepConfig implements HandlerInterceptor {
    
    private final RestTemplate restTemplate;
    public IntercepConfig(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        String jwt = request.getHeader("Authorization");
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwt);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:5000/users/role",HttpMethod.GET, entity, String.class);
        
        String role = responseEntity.getBody();
                
        if(role.equals("ROLE_ADMIN")) {
            System.out.println("User is admin");
            return true;
        }
        else {
            System.out.println("User is not admin");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());//401
            ApiResponse apiResponse = new ApiResponse( "User is not authorized to access this resource", false);
            String err = "User is not authorized to access this resource";
            response.getWriter().write(err);
            return false;
        }
    }
    // @Override
    // public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    //     System.out.println("In postHandle method");
    // }
}
