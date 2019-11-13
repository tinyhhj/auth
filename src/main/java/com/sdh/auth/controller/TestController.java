package com.sdh.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class TestController {
    @GetMapping("/test")
    public void test(HttpServletResponse response) {
        response.setStatus(401);
        response.addHeader("WWW-Authenticate", "Basic realm=\"" + "Realm" + "\"");
    }
}
