package com.joaodartora.webfluxschedulers.flavors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Elastic {

    private static final Logger logger = LoggerFactory.getLogger(Elastic.class);

    public static void main(String[] args) {
        Mono.just("hello")
                .doOnNext(hello -> logger.info(hello + " subscribed on " + Thread.currentThread().getName()))
                .publishOn(Schedulers.elastic())
                .doOnNext(hello -> logger.info(hello + " published on " + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.newBoundedElastic(5, 1000, "personal-bounded-elastic"))
                .doOnNext(hello -> logger.info(hello + " published on " + Thread.currentThread().getName()))
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(hello -> logger.info(hello + " published on " + Thread.currentThread().getName()))
                .block(); // ONLY USE THIS FOR TESTS
    }
}
