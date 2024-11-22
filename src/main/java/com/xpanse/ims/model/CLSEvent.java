package com.xpanse.ims.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CLSEvent {
	@NotNull(message = "businessEventAttributes are required")
	@Valid
	private BusinessEventAttributes businessEventAttributes;
	@NotNull(message = "messageBody is required")
	private MessageBody messageBody;
	private List<AttachmentItem> attachment;
	@NotNull(message = "businessEventData is required")
	@Valid
	private BusinessEventData businessEventData;
	@NotNull(message = "attributes are required")
	@Valid
	private Attributes attributes;
}