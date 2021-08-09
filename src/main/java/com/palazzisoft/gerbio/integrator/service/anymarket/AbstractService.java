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
        log.info("Retrieving {} by id {}", clazz.getName(), id);
        return (T) webClient.get().uri(getURLBase().concat("/") + id).retrieve().bodyToMono(clazz).block();
    }

    public T save(T t) {
        log.info("Storing {} by with data {}", clazz.getName(), t.toString());
        return (T) webClient.post().uri(getURLBase()).body(Mono.just(t), clazz).retrieve().bodyToMono(clazz).block();
    }

}
