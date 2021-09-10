package com.joaodartora.webfluxschedulers.flavors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.IntStream;

public class Parallel {

    private static final Logger logger = LoggerFactory.getLogger(Single.class);
    private static final Integer DELAY_SECONDS = 1;

    public static void main(String[] args) {
        Flux.fromStream(IntStream.range(0, 10).boxed())
                .doOnNext(integer -> logger.info(integer + " just on " + Thread.currentThread().getName()))
                .delayElements(Duration.ofSeconds(DELAY_SECONDS)) // Delay elements uses parallel scheduler by default
                .doOnNext(integer -> logger.info(integer + " subscribed on " + Thread.currentThread().getName()))
                .collectList()
                .block(); // ONLY USE THIS FOR TESTS
    }
}
