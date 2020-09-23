package com.joaodartora.webfluxschedulers.flavors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Immediate {

    /**
     * - Allocate the actual thread to execute the task
     * - Can be used as a null object for when an API requires a Scheduler but you don’t want to change threads
     * - Run submitted Runnable instead of scheduling them
     */

    private static final Logger logger = LoggerFactory.getLogger(Single.class);

    public static void main(String[] args) {
        Mono.just("hello")
                .doOnNext(hello -> logger.info(hello + " just on " + Thread.currentThread().getName()))
                .doOnNext(hello -> logger.info(hello + " subscribed using same " + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.immediate())
                .doOnNext(hello -> logger.info(hello + " subscribed using same " + Thread.currentThread().getName()))
                .map(hello -> hello.concat(", world"))
                .doOnNext(hello -> logger.info(hello + " subscribed using same " + Thread.currentThread().getName()))
                .block();
    }
}
