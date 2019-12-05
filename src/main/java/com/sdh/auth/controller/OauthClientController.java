package com.sdh.auth.controller;

import com.sdh.auth.domain.OauthClient;
import com.sdh.auth.repository.OauthClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/clients")
@Slf4j
public class OauthClientController {
    @Autowired
    OauthClientRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @PostMapping
    public ResponseEntity<?> addClient(@RequestBody OauthClient client) {
        String randomString = new RandomValueStringGenerator(32).generate();
        client.setClientSecret(passwordEncoder.encode(randomString));
        repository.save(client);
        log.debug("[CREATED] client id : {}, client password: {}", client.getClientId(), randomString);
        return ResponseEntity.ok(client);
    }
}
