package com.sdh.auth.dto;

import com.sdh.auth.domain.OauthClientDetails;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OauthClientDetailsDto {
    @NotNull
    private String webServerRedirectUri;

    public OauthClientDetails toDomain() {
        OauthClientDetails target = new OauthClientDetails();
        BeanUtils.copyProperties(this, target);
        return target;
    }

    public static OauthClientDetailsDto create(String url) {
        OauthClientDetailsDto dto = new OauthClientDetailsDto();
        dto.setWebServerRedirectUri(url);
        return dto;
    }
}
