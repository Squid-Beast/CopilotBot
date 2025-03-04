package com.marvel.copilotstudiobot.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/copilot")
public class BotController {
    
    private static final String DIRECT_LINE_URL = "https://directline.botframework.com/v3/directline/conversations";

    private final RestTemplate restTemplate= new RestTemplate();

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestParam String message, @AuthenticationPrincipal Jwt jwt) {
        String token = jwt.getTokenValue();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = Map.of("text", message);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(DIRECT_LINE_URL, HttpMethod.POST, request, String.class);

        return response;
    }
}
