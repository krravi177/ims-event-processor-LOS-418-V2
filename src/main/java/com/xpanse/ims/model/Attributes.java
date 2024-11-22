package com.xpanse.ims.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Attributes{
	@NotBlank(message = "specVersion is required")
	private String specVersion;
	@NotBlank(message = "sourceUserName is required")
	private String sourceUserName;
	@NotBlank(message = "sourcetime is required")
	private String sourceTime;
	@NotBlank(message = "eventType is required")
	private String eventType;
	@NotBlank(message = "source is required")
	private String source;
	@NotBlank(message = "submitterTransactionCode is required")
	private String submitterTransactionCode;
}