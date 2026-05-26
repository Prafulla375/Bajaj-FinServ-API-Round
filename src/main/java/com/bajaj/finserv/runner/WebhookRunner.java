package com.bajaj.finserv.runner;

import com.bajaj.finserv.dto.WebhookResponse;
import com.bajaj.finserv.service.WebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WebhookRunner implements CommandLineRunner {

    @Autowired
    private WebhookService webhookService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting Bajaj Finserv webhook flow...");

        try {
            // generate webhook and get token
            WebhookResponse response = webhookService.generateWebhook();

            if (response == null || response.getAccessToken() == null) {
                System.out.println("Error: did not get access token from webhook");
                return;
            }

            // submit the sql query
            webhookService.submitSolution(response);

            System.out.println("Done! Flow completed successfully.");

        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
