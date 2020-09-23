package com.joaodartora.webfluxschedulers.flavors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Elastic {

    /**
     * - ELASTIC: Creates new Worker pool as needed and reuses the idle ones
     * - ELASTIC: Spawns threads on-demand without a limit (BE CAREFUL!)
     * - BOUNDED ELASTIC: Creates a new Worker pool with limited size
     * - BOUNDED ELASTIC: Reuses the iddle Workers from the pool
     * - Good for more long-lived tasks (eg. blocking IO tasks)
     * - Idle threads (default 60s) are disposed of
     */

    private static final Logger logger = LoggerFactory.getLogger(Elastic.class);

    public static void main(String[] args) {
        Mono.just("hello")
                .doOnNext(hello -> logger.info(hello + " just on " + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.elastic())
                .doOnNext(hello -> logger.info(hello + " subscribed before publishing on " + Thread.currentThread().getName()))
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(hello -> logger.info(hello + " published on " + Thread.currentThread().getName()))
                .block();
    }
}
