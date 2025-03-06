package com.marvel.copilotstudiobot.controller;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.marvel.copilotstudiobot.service.CopilotService;

@RestController
@RequestMapping("/copilot")
@CrossOrigin(origins = "*")
public class CopilotController {

    @Autowired
    private CopilotService copilotService;

    // @Value("${smarty.auth-id}")
    // private String authId;

    // @Value("${smarty.auth-token}")
    // private String authToken;

    @Value("${opencage.api-key}")
    private String apiKey;

    private static final String TOKEN_URL = "https://default70de199207c6480fa318a1afcba039.83.environment.api.powerplatform.com/powervirtualagents/botsbyschema/cre88_stockMarketHelpService/directline/token?api-version=2022-03-01-preview";
    
    private static final String DIRECT_LINE_CONVERSATION_URL = "https://default70de199207c6480fa318a1afcba039.83.environment.api.powerplatform.com/powervirtualagents/botsbyschema/cre88_stockMarketHelpService/directline/conversations/";

    private final RestTemplate restTemplate= new RestTemplate();

    // This is the controller that will handle the chat messages sent by the user. 
    // The chat method will send the message to the Direct Line API using the user's JWT token for authentication. 
    // The message is sent as a JSON object with the text property containing the user's message. 
    // The response from the Direct Line API is returned to the user.
    // @PostMapping("/chat")
    // public ResponseEntity<String> chat(@RequestParam String message, @AuthenticationPrincipal Jwt jwt) {
    //     String token = jwt.getTokenValue();

    //     HttpHeaders headers = new HttpHeaders();
    //     headers.set("Authorization", "Bearer " + token);
    //     headers.setContentType(MediaType.APPLICATION_JSON);

    //     Map<String, String> body = Map.of("text", message);

    //     HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
    //     ResponseEntity<String> response = restTemplate.exchange(DIRECT_LINE_URL, HttpMethod.POST, request, String.class);

    //     return response;
    // }

    //Here it starts with No Authentication of Copilot Agent
    
    @PostMapping("/getToken")
    public ResponseEntity<String> getToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("{}", headers);
        ResponseEntity<String> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, entity, String.class);

        return response;
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<String> sendMessage(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String conversationId = request.get("conversationId");
        String message = request.get("message");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        Map<String, Object> body = new HashMap<>();
        body.put("type", "message");
        body.put("from", Map.of("id", "user1"));
        body.put("text", message);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        String url = DIRECT_LINE_CONVERSATION_URL + conversationId + "/activities";
        
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        return response;

    }

    // @GetMapping("/getBotUrl")
    // public Map<String, String> getBotUrl() {
    //     Map<String, String> response = new HashMap<>();
    //     response.put("botUrl", "https://default70de199207c6480fa318a1afcba039.83.environment.api.powerplatform.com/powervirtualagents/botsbyschema/cre88_stockMarketHelpService/directline/token?api-version=2022-03-01-preview");
    //     return response;
    // }

    @GetMapping("/api/bot-url")
    public String getBotUrl() {
        return copilotService.getBotUrl();
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<String> getAutocomplete(@RequestParam String query) {
        String url = "https://api.opencagedata.com/geocode/v1/json?q=" + query + "&key=" + apiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return ResponseEntity.ok(response.getBody());
    }
    
}
