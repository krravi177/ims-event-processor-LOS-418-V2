package com.xpanse.ims.listener;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpanse.ims.model.*;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
//import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
public class EventProcessorListener {

    @Value("${aws.sqs.queue-url}")
    private String queueUrl;

    ObjectMapper objectMapper;



    // Handle the message
    @SqsListener(value = "ims-voie-events-dev")
    public void handleMessage2(CLSSQSEvent message) {
        // Here you can process the message

        try {
            System.out.println("Received message from event bus  "+ message);

            processMessage(message);
        } catch (Exception e) {
            // Add error handling or retries here
        }
    }

    private void processMessage(CLSSQSEvent message) {
        IncomeRequest request = new IncomeRequest();
        CLSEvent event = message.getDetail();
        EventRequestDetails details = new EventRequestDetails();
        details.setEventType("PlaceOrder");
        details.setEventCode("30");
        details.setTransactionCode(event.getAttributes().getSubmitterTransactionCode());

        MessageBody mismo = new MessageBody();
        Deal deal = new Deal();
        deal.setLoans(event.getMessageBody().getDeal().getLoans());

        List<PartiesItem> parties = event.getMessageBody().getDeal().getParties();
        RolesItem rolesItem = new RolesItem();
        rolesItem.setPartyRoleType("XPANSQA");
        PartiesItem item = PartiesItem.builder().sequenceNumber(2).id("party3").legalEntity(LegalEntity.builder().fullName("XPANSQA").build()).roles(List.of(rolesItem)).build();

        RolesItem rolesItem2 = new RolesItem();
        rolesItem.setPartyRoleType("SubmittingParty");
        PartiesItem item2 = PartiesItem.builder().sequenceNumber(3).id("party4").legalEntity(LegalEntity.builder().fullName("Freedom Mortgage").build()).roles(List.of(rolesItem2)).build();


        parties.add(item);
        parties.add(item2);

        deal.setParties(parties);
        mismo.setDeal(deal);

        details.setMessageBody(mismo);
        request.setEventRequestDetails(details);
        TokenResponse tokenresponse = getToken();
        sendPostRequest(request, tokenresponse);
        // Your message processing logic
    }


    public String sendPostRequest(IncomeRequest requestBody, TokenResponse tokenresponse) {

        String token = "Bearer eyJraWQiOiJza3dqMWVQQ2p1OU1wRTV5ems4ZG1WUGlyN0U1cVpIYnVrMmpjT1wvMTJiMD0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI5NGY4YjRlOC1jMGYxLTcwNDQtNjc5Zi00MjBhOTZmYTIyMjkiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9wcXV6NlZUaXoiLCJjb2duaXRvOnVzZXJuYW1lIjoidGVzdF8wMSIsImN1c3RvbTp0ZW5hbnRfaWQiOiJUSUQwMDAwMSIsIm9yaWdpbl9qdGkiOiI2MTZiN2U0ZS1lYjM3LTQwMWUtODU2Zi1hNzBkNmZmNmY5NTYiLCJhdWQiOiI2bDFpbTFtaTk0ZG0wM2h2azlicGk4NGdmcCIsImV2ZW50X2lkIjoiYzg1MmFhMTktZmExZi00OThiLTg0OGItMDEzYTU1YjBmNzI0IiwidG9rZW5fdXNlIjoiaWQiLCJhdXRoX3RpbWUiOjE3MzA5OTM1NjMsImV4cCI6MTczMDk5NzE2MywiaWF0IjoxNzMwOTkzNTYzLCJqdGkiOiJkNzY2OTY3NC0wMGViLTQ4NTgtYmQwYi1lYjc2YzIzMDBhYWQiLCJlbWFpbCI6InJpc2hpLnBvbWFsQHhwYW5zZS5jb20ifQ.m5w3j4huEYLsZ5edpZ68H-bAT9HHB_RpSwHfa5v7O4Ja3vM-EIJEDRaKL65eXu_UDWI71-nWnbaiLgd_snIkKkDcxNY74k4G04sO40opN70eFGur57yTCvWlEXIIRhTCSxVZeolEEbiAIp92tWaP_Zi30mRPcJ6ekH7u_aeY54xaNFNpZc4V37J6OpBefHKgKwYkFDA-0m2DnXwpZzZMOT-SiHiWCYNNF16dk0ArzUjn50P8jMxFzRggiIzL2sI6GdBHhwMfoW_Jiz0u5y7Hm14FpZPtnp6cUeDp5bGCk8XIRbAtuwMQc0eBET3Zn5TkYx5RspHIVMmIWZRCGbFoJw";

        if(null != tokenresponse && null != tokenresponse.getResponseData()
                && null != tokenresponse.getResponseData().getAuthenticationResult() && null != tokenresponse.getResponseData().getAuthenticationResult().getIdToken()){
            System.out.println("Preparing token");
            token = "Bearer " + tokenresponse.getResponseData().getAuthenticationResult().getIdToken();
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            HttpClient httpClient = HttpClient.newHttpClient();
            // The URL of the API endpoint you want to hit
            String url = "https://d3ywvgmhi0.execute-api.us-east-1.amazonaws.com/VOIE_POC/income-request-api";

            // Create the request body

            // Convert the request body to JSON
            String jsonBody = mapper.writeValueAsString(requestBody);

            // Create the POST request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json").header("Authorization", token)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Extract response body and status code
            String responseBody = response.body();
            int statusCode = response.statusCode();

            // You can also extract headers
            HttpHeaders headers = response.headers();
            System.out.println("Response Headers: " + headers.map());

            // Output the response details
            System.out.println("Response Status Code: " + statusCode);
            System.out.println("Response Body: " + responseBody);

            return responseBody;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public TokenResponse getToken() {
        String jsonBody = """
                {
                    "clientId": "test_01",
                    "clientSecret" : "Test123#"
                }
                """;
        try {
            ObjectMapper mapper = new ObjectMapper();
            HttpClient httpClient = HttpClient.newHttpClient();
            // The URL of the API endpoint you want to hit
            String url = "https://d3ywvgmhi0.execute-api.us-east-1.amazonaws.com/VOIE_POC/get-authentication-token";

            // Create the request body


            // Create the POST request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Extract response body and status code
            String responseBody = response.body();
            int statusCode = response.statusCode();

            // You can also extract headers
            HttpHeaders headers = response.headers();
            System.out.println("Response Headers: " + headers.map());


            TokenResponse tokenResponse = mapper.readValue(responseBody, TokenResponse.class);
            // Output the response details
            System.out.println("Response Status Code: " + statusCode);
//            System.out.println("Response Body: " + tokenResponse);


            return tokenResponse;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
