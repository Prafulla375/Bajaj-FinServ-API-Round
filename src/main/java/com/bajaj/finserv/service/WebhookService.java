package com.bajaj.finserv.service;

import com.bajaj.finserv.dto.SqlSubmission;
import com.bajaj.finserv.dto.WebhookRequest;
import com.bajaj.finserv.dto.WebhookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${bajaj.name}")
    private String name;

    @Value("${bajaj.regNo}")
    private String regNo;

    @Value("${bajaj.email}")
    private String email;

    @Value("${bajaj.generateWebhookUrl}")
    private String generateWebhookUrl;

    @Value("${bajaj.testWebhookUrl}")
    private String testWebhookUrl;

    @Value("${bajaj.finalQuery}")
    private String finalQuery;

    // step 1 - call generateWebhook and get the token + webhook url
    public WebhookResponse generateWebhook() {
        WebhookRequest reqBody = new WebhookRequest(name, regNo, email);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<WebhookRequest> entity = new HttpEntity<>(reqBody, headers);

        ResponseEntity<WebhookResponse> resp = restTemplate.postForEntity(
                generateWebhookUrl, entity, WebhookResponse.class);

        System.out.println("Webhook generated successfully");
        System.out.println("Webhook URL: " + resp.getBody().getWebhook());
        System.out.println("Token received: " + (resp.getBody().getAccessToken() != null ? "yes" : "no"));

        return resp.getBody();
    }

    // step 2 - submit the sql query with jwt token
    public void submitSolution(WebhookResponse webhookResp) {
        SqlSubmission body = new SqlSubmission(finalQuery);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", webhookResp.getAccessToken());

        HttpEntity<SqlSubmission> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> resp = restTemplate.postForEntity(
                testWebhookUrl, entity, String.class);

        System.out.println("Solution submitted!");
        System.out.println("Status: " + resp.getStatusCode());
        System.out.println("Response: " + resp.getBody());
    }
}
