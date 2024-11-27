package com.ssafy.campcino.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.campcino.service.GPTService;

@RestController
@RequestMapping("/chatbot")
public class ChatbotController {

    @PostMapping
    public ResponseEntity<Map<String, String>> getChatbotResponse(@RequestBody Map<String, String> payload) {
        String userMessage = payload.get("message");
        String reply = GPTService.getResponse(userMessage); // GPT 서비스 호출
        Map<String, String> response = new HashMap<>();
        response.put("reply", reply);
        return ResponseEntity.ok(response);
    }
}
