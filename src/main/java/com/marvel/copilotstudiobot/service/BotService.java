package com.marvel.copilotstudiobot.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import reactor.core.publisher.Mono;

@Service
public class BotService {
    
    private final WebClient webClient;

    public BotService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://default219e75858447473a94f1fb35b10c71.8f.environment.api.powerplatform.com/powervirtualagents/botsbyschema/crf5a_employEzGeneralHelpAgent/directline/token?api-version=2022-03-01-preview").build();
    }

    public Mono<String> sendMessageToBot(String message) {
        return webClient.post()
                .bodyValue(Map.of("message", message))
                .retrieve()
                .bodyToMono(String.class);
    }
}
