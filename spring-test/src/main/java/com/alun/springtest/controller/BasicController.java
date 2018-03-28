package com.alun.springtest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class BasicController {
	@GetMapping("/hi")
	public Mono<String> syHello(){
		return Mono.just("hello world");	
	}
	
	
	
}
