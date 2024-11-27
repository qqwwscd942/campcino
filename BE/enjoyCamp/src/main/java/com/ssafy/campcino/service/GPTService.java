package com.ssafy.campcino.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class GPTService {
	@Value("${spring.ai.openai.api-key}")
    private static String OPENAI_API_KEY;
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    // 고정 프롬프트 설정
    private static final String SYSTEM_PROMPT = "당신은 대한민국 캠핑 추천 도우미입니다. 사용자에게 캠핑장에 대한 정보나 캠핑 상품에 대한 정보를 제공합니다. 캠핑 관련 질문이 아니면 답변해주지 말아주세요.";

    public static String getResponse(String message) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(OPENAI_API_KEY);

        // 메시지 배열 생성
        String requestBody = "{"
                + "\"model\": \"gpt-3.5-turbo\","
                + "\"messages\": ["
                + "{\"role\": \"system\", \"content\": \"" + SYSTEM_PROMPT + "\"},"
                + "{\"role\": \"user\", \"content\": \"" + message + "\"}"
                + "]"
                + "}";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            String response = restTemplate.postForObject(OPENAI_API_URL, entity, String.class);

            // 응답에서 챗봇의 답변 추출
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            String reply = root.path("choices").get(0).path("message").path("content").asText();

            return reply.trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "죄송합니다. 답변을 가져올 수 없습니다.";
        }
    }
}