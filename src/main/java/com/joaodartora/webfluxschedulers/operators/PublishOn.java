package com.joaodartora.webfluxschedulers.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

public class PublishOn {

    private static final Logger logger = LoggerFactory.getLogger(PublishOn.class);

    public static void main(String[] args) {
        Flux.fromIterable(List.of("A", "B"))
                .doOnNext(letter -> logger.info(letter + " on " + Thread.currentThread().getName() + " thread"))
                .publishOn(Schedulers.newSingle("first-single"))
                .doOnNext(letter -> logger.info(letter + " on " + Thread.currentThread().getName() + " thread"))
                .map(letter -> letter.concat("*****"))
                .publishOn(Schedulers.newSingle("second-single"))
                .doOnNext(letter -> logger.info(letter + " on " + Thread.currentThread().getName() + " thread"))
                .subscribe();
    }

}
