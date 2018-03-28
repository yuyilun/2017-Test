package com.alun.springtest;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.alun.springtest.domain.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RESTClientTest {
	public static void main(String[] args) {
		
		User user = new User();
		user.setId("1");
		user.setName("Test");
		user.setEmail("yu10081008@126.com");
		
		String url ="http://localhost:8080/user";
		WebClient webClient = WebClient.create(url);
		
		Mono<Object> createdUser = webClient.post()
			.uri("")
			.accept(MediaType.APPLICATION_JSON)
			.body(Flux.just(user),User.class)
			.exchange()
			.flatMap(response -> response.bodyToMono(User.class));
		
		System.out.println(createdUser.block());
		
	}
}
