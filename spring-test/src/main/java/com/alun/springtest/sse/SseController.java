package com.alun.springtest.sse;

import java.time.Duration;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.netty.util.internal.ThreadLocalRandom;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;
@RestController
@RequestMapping("/sse")
public class SseController {
	@GetMapping("/randomNumbers")
	public Flux<ServerSentEvent<Integer>> randomNumbers(){
		return Flux.interval(Duration.ofSeconds(1))
				.map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt())) //生成一个元组，<Long,Integer>
				.map(data -> ServerSentEvent.<Integer>builder()
						.event("random")
						.id(Long.toString(data.getT1()))
						.build());
	}
	
}
