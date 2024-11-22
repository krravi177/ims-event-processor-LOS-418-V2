package com.xpanse.ims.model;

import lombok.Data;

import java.util.List;

@Data
public class RolesItem{
	private String partyRoleType;
	private Borrower borrower;
	private List<PartyRoleIdentifierItem> partyRoleIdentifiers;
	private String id;
}