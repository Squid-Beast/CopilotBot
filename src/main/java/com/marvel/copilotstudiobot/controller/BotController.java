package com.marvel.copilotstudiobot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marvel.copilotstudiobot.service.BotService;

@RestController
@RequestMapping("/api/bot")
public class BotController {

    @Autowired
    private BotService botService;

}
