package com.sdh.auth;

import com.google.gson.Gson;
import com.sdh.auth.domain.OauthClientDetails;
import com.sdh.auth.dto.OauthClientDetailsDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestAuthConfig.class)
class AuthApplicationTests {
	@Autowired
	MockMvc mockMvc;
	Gson gson= new Gson();
	@Test
	void 클라이언트_인증() throws Exception {
		mockMvc.perform(post("/oauth/token")
				.param("grant_type","client_credentials")
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
				.with(httpBasic("testuser","password"))
				.param("state","12345");
		mockMvc.perform(builder)
				.andDo(print())
				.andExpect(status().isUnauthorized());

		builder.with(httpBasic("testuser","password"));
		mockMvc.perform(builder)
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void add_client() throws Exception {
		OauthClientDetails client = add_client("test");
		authorization_client(client.getClientId(), client.getClientSecret());
	}

	private OauthClientDetails add_client(String url) throws Exception {
		return gson.fromJson(mockMvc.perform(post("/api/v1/auth/clients")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(OauthClientDetailsDto.create(url)))
				.with(httpBasic("testuser","password")))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString(), OauthClientDetails.class);
	}

	private void authorization_client(String id, String password) throws Exception {
		mockMvc.perform(post("/oauth/token")
				.param("grant_type","client_credentials")
				.with(httpBasic(id,password)))
				.andDo(print())
				.andExpect(status().isOk());
	}
}
