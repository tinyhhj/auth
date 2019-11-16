package com.sdh.auth.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class TestController {
    @GetMapping("/test")
    public void test(HttpServletResponse response) {
        response.setStatus(401);
        response.addHeader("WWW-Authenticate", "Bearer");
    }

    @GetMapping("/test/endpoint")
    public Map<String, Object> endpoint(HttpServletRequest request) {
        String code = request.getParameter("code");
        String clientId = "client";
        String password = "password";
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, password);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("grant_type","authorization_code");

        return rest.exchange("http://localhost:8080/oauth/token", HttpMethod.POST, new HttpEntity<>(body, headers), new ParameterizedTypeReference<Map<String,Object>>() {
        }).getBody();
    }
}
