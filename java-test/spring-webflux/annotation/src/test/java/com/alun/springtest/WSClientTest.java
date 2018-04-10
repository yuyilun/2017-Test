package com.alun.springtest;

import java.net.URI;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import reactor.core.publisher.Mono;

public class WSClientTest {

	public static void main(String[] args) {
		WebSocketClient webSocketClient = new ReactorNettyWebSocketClient();
		webSocketClient.execute(
				URI.create("ws://localhost:8080/echo"), 
				session -> 
					session.send(Mono.just(session.textMessage("hello")))
					.thenMany(session.receive().take(1).map(WebSocketMessage::getPayloadAsText))
					.doOnNext(System.out::println)
					.then()
				).block();
	}
}
