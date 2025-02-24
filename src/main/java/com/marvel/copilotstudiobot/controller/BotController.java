package com.marvel.copilotstudiobot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marvel.copilotstudiobot.service.BotService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bot")
public class BotController {
    
    @Autowired
    private BotService botService;

    @PostMapping("/send")
    public Mono<String> sendMessage(@RequestParam String message) {
        return botService.sendMessageToBot(message);
    }
}
