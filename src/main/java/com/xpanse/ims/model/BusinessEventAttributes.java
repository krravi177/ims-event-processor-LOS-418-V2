package com.xpanse.ims.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BusinessEventAttributes{
	@NotBlank(message = "dataSchema is required")
	private String dataSchema;
	@NotBlank(message = "dataContentType is required")
	private String dataContentType;
}