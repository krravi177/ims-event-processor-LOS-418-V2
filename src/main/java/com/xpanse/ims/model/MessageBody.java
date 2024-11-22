package com.xpanse.ims.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageBody{
	@NotNull(message = "deal is required")
	private Deal deal;
}