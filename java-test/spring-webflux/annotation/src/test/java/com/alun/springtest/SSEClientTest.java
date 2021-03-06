package com.alun.springtest;

import java.util.Objects;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;

public class SSEClientTest {
	public static void main(String[] args) {
       
		WebClient webClient = WebClient.create();
		webClient.get()
			.uri("http://localhost:8080/sse/randomNumbers")
			.accept(MediaType.TEXT_EVENT_STREAM)
			.exchange()
			.flatMapMany(response -> response.body(BodyExtractors.toFlux(
					new ParameterizedTypeReference<ServerSentEvent<Integer>>() {})))
			.filter(sse -> Objects.nonNull(sse.data()))
			.map(ServerSentEvent::data)
			.buffer(1)
			.doOnNext(System.out::println)
			.blockFirst();
    }
}
