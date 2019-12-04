package com.sdh.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import javax.sql.DataSource;

@Configuration
public class AuthConfig implements AuthorizationServerConfigurer {
    @Autowired
    DataSource dataSource;

    AuthenticationManager authenticationManager;

    public AuthConfig(AuthenticationConfiguration config) throws Exception {
        authenticationManager = config.getAuthenticationManager();
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer authorizationServerSecurityConfigurer) throws Exception {
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clientDetailsServiceConfigurer) throws Exception {
        clientDetailsServiceConfigurer
                .inMemory()
                .withClient("test-client")
                .secret("{noop}test-client")
                .authorizedGrantTypes("authorization_code","password","client_credentials","implicit","refresh")
                .authorities("ROLE_TEST")
                .scopes("read","write")
                .accessTokenValiditySeconds(60*60*4)
                .refreshTokenValiditySeconds(60*60*24*30)
        .redirectUris("http://localhost:8080/welcome");
//        clientDetailsServiceConfigurer.jdbc()
//        clientDetailsServiceConfigurer
//                    .jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer authorizationServerEndpointsConfigurer) throws Exception {
        authorizationServerEndpointsConfigurer.authenticationManager(authenticationManager);
    }

}
