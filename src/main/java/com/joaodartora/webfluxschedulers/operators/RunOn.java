package com.joaodartora.webfluxschedulers.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

public class RunOn {

    private static final Logger logger = LoggerFactory.getLogger(RunOn.class);

    public static void main(String[] args) {
        Flux.fromIterable(List.of("A", "B", "C"))
                .parallel()
                .doOnNext(letter -> logger.info(letter + " on " + Thread.currentThread().getName() + " thread"))
                .runOn(Schedulers.newParallel("first-parallel"))
                .doOnNext(letter -> logger.info(letter + " on " + Thread.currentThread().getName() + " thread"))
                .map(letter -> letter.concat("*****"))
                .runOn(Schedulers.newParallel("second-parallel"))
                .doOnNext(letter -> logger.info(letter + " on " + Thread.currentThread().getName() + " thread"))
                .map(letter -> letter.concat("&&&&&"))
                .runOn(Schedulers.newParallel("third-parallel"))
                .doOnNext(letter -> logger.info(letter + " on " + Thread.currentThread().getName() + " thread"))
                .sequential()
                .subscribe();
    }

}
