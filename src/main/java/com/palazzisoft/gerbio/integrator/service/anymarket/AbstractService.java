package com.palazzisoft.gerbio.integrator.service.anymarket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
public abstract class AbstractService<T> {

    protected WebClient webClient;
    private Class clazz;


    public AbstractService(WebClient webClient, Class clazz) {
        this.webClient = webClient;
        this.clazz = clazz;
    }

    protected abstract String getURLBase() ;

    public T getById(Long id) {
        log.debug("Retrieving {} by id {}", clazz.getName(), id);
        return (T) webClient.get().uri(getURLBase().concat("/") + id).retrieve().bodyToMono(clazz).block();
    }

    public T save(T t) {
        log.debug("Storing {} by with data {}", clazz.getName(), t.toString());
        return (T) webClient.post().uri(getURLBase()).body(Mono.just(t), clazz).retrieve().bodyToMono(clazz)
                .doOnEach(data ->  log.debug(data.toString()))
                .doOnError(error -> log.error(error.toString()))
                .block();
    }

    public T update(T t, Long id) {
        log.debug("Updating {} with data {} ", clazz.getName(), t.toString());
        return (T) webClient.put().uri(getURLBase().concat("/") + id).body(Mono.just(t), clazz).retrieve().bodyToMono(clazz)
                .doOnEach(data ->  log.debug(data.toString()))
                .doOnError(error -> log.error(error.toString()))
                .block();
    }

    public T update(T t) {
        log.debug("Updating {} with data {} ", clazz.getName());

        return (T) webClient.put().uri(getURLBase().concat("/")).body(Mono.just(t), clazz).retrieve().bodyToMono(clazz)
                .doOnEach(data ->  log.debug("Updating " + data.toString()))
                .doOnError(error -> log.error("Error Updating " + error.toString()))
                .block();
    }

    public T delete(Long id) {
        log.debug("Removing {} with id {}", clazz.getName(), id);
        return (T) webClient.delete().uri(getURLBase().concat("/") + id).retrieve().bodyToMono(clazz)
                .doOnEach(data -> log.debug("Removing : {}" + data.toString()))
                .doOnError(error -> log.error("Error Removing " + error.toString()))
                .block();
    }
}
