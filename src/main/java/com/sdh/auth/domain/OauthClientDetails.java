package com.sdh.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class OauthClientDetails {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String clientId;

    private String resourceIds;
    private String clientSecret;
    private String scope;
    private String authorizedGrantTypes;
    @NotNull
    private String webServerRedirectUri;
    private String authorities;
    private Integer accessTokenValidity;
    private Integer refreshTokenValidity;
    private String additionalInformation;
    private String autoapprove;

    public OauthClientDetails() {
        //default
        setAuthorizedGrantTypes("authorization_code, client_credentials, refresh, password");
    }
}
