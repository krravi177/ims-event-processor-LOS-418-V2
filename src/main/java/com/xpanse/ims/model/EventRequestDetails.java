package com.xpanse.ims.model;

import lombok.Data;

@Data
public class EventRequestDetails {
    private String eventType;
    private String eventCode;
    private String transactionCode;
    private MessageBody messageBody;
}
