package com.joaodartora.webfluxschedulers.flavors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Single {

    private static final Logger logger = LoggerFactory.getLogger(Single.class);

    public static void main(String[] args) {
        Mono.just("hello")
                .doOnNext(hello -> logger.info(hello + " just on: " + Thread.currentThread().getName()))
                .publishOn(Schedulers.newSingle("First Single"))
                .doOnNext(hello -> logger.info(hello + " published on new: " + Thread.currentThread().getName()))
                .publishOn(Schedulers.newSingle("Second Single"))
                .doOnNext(hello -> logger.info(hello + " published on new: " + Thread.currentThread().getName()))
                .publishOn(Schedulers.single())
                .doOnNext(hello -> logger.info(hello + " published on default single Scheduler: " + Thread.currentThread().getName()))
                .publishOn(Schedulers.single())
                .doOnNext(hello -> logger.info(hello + " published on same single Scheduler: " + Thread.currentThread().getName()))
                .block();
    }

}
