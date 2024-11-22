package com.xpanse.ims.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BusinessEventData{
	@NotBlank(message = "division is required")
	private String division;
	@NotBlank(message = "channel is required")
	private String channel;
	@NotNull(message = "trigger is required")
	private Trigger trigger;
	@NotBlank(message = "loanNumber is required")
	private String loanNumber;
}