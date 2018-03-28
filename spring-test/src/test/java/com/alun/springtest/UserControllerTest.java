package com.alun.springtest;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.alun.springtest.domain.User;

import reactor.core.publisher.Mono;

public class UserControllerTest {

	private final WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
	
	@Test
	public void testCreateUser() {
		
		final User user = new User();
		user.setId("2");
		user.setName("yuyilun");
		user.setEmail("yu10081008@126.com");
		
		client.post().uri("/user")
			.contentType(MediaType.APPLICATION_JSON)
			.body(Mono.just(user),User.class)
			.exchange()
			.expectStatus().isOk()
			.expectBody().jsonPath("name").isEqualTo("Test");
		
		
	}

}
