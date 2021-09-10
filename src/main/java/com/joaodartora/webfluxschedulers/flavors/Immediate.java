package com.joaodartora.webfluxschedulers.flavors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Immediate {

    private static final Logger logger = LoggerFactory.getLogger(Single.class);

    public static void main(String[] args) {
        Mono.just("hello")
                .publishOn(Schedulers.elastic())
                .doOnNext(hello -> logger.info(hello + " first published on: " + Thread.currentThread().getName()))
                .publishOn(Schedulers.immediate())
                .doOnNext(hello -> logger.info(hello + " second published using same thread: " + Thread.currentThread().getName()))
                .publishOn(Schedulers.newElastic("other-elastic"))
                .doOnNext(hello -> logger.info(hello + " third published using different thread: " + Thread.currentThread().getName()))
                .block(); // ONLY USE THIS FOR TESTS
    }
}
