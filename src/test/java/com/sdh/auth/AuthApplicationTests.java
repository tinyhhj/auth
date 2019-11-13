package com.sdh.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthApplicationTests {
	@Autowired
	MockMvc mockMvc;
	@Test
	void 클라이언트_인증() throws Exception {
		mockMvc.perform(post("/oauth/token")
				.param("grant_type","client_credentials")
				.param("scope","write")
				.with(httpBasic("test-client","test-client")))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void 클라이언트유저_인증() throws Exception {
		MockHttpServletRequestBuilder builder = get("/oauth/authorize")
				.param("grant_type","authorization_code")
				.param("response_type","code")
				.param("client_id","test-client")
				.param("state","12345");
		mockMvc.perform(builder)
				.andDo(print())
				.andExpect(status().isUnauthorized());

		builder.with(httpBasic("testuser","password"));
		mockMvc.perform(builder)
				.andDo(print())
				.andExpect(status().isOk());
	}

}
