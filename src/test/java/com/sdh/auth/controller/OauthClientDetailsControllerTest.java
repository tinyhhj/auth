package com.sdh.auth.controller;

import com.sdh.auth.config.RootConfig;
import com.sdh.auth.domain.OauthClientDetails;
import com.sdh.auth.repository.OauthClientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.sql.DataSource;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({RootConfig.class})
@RunWith(SpringRunner.class)
public class OauthClientDetailsControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    OauthClientRepository repository;
    @MockBean
    DataSource dataSource;
    @Test
    public void test() throws Exception {
        OauthClientDetails client = new OauthClientDetails();
        String webServerRedirectUri = "http://localhost:8080/redirect";
        String id = UUID.randomUUID().toString();
        client.setWebServerRedirectUri(webServerRedirectUri);
        client.setClientId(id);

        given(repository.save(any())).willReturn(client);

        ResultActions actions = mockMvc.perform(post("/api/v1/auth/clients").param("webServerRedirectUri",webServerRedirectUri))
                .andExpect(status().isOk())
                .andDo(print());

        actions.andExpect(jsonPath("clientId").value(id))
                .andExpect(jsonPath("webServerRedirectUri").value(webServerRedirectUri));
    }

    @Test
    public void test_password() throws Exception {
        ResultActions actions = mockMvc.perform(post("/oauth/token")
                .param("grant_type", "password")
                .param("username","testuser")
                .param("password", "password")
                .with(httpBasic("test-client","test-client"))
        ).andDo(print());

        actions.andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());
    }
}
