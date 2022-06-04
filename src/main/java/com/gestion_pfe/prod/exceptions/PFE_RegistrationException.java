package com.gestion_pfe.prod.exceptions;

public class PFE_RegistrationException extends RuntimeException {
	
	public PFE_RegistrationException(String msg, Exception e) {
		super(msg,e);
	}
	
	public PFE_RegistrationException(String msg) {
		super(msg);
	}

}
