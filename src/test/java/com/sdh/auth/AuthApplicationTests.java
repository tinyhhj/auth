package com.sdh.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthApplicationTests {
	@Autowired
	MockMvc mockMvc;
	@Test
	void contextLoads() throws Exception {
		mockMvc.perform(post("/oauth/token")
				.param("grant_type","client_credentials")
				.param("scope","write")
				.with(httpBasic("test-client","test-client"))
		)
				.andDo(print())
				.andExpect(status().isOk());
	}

}
