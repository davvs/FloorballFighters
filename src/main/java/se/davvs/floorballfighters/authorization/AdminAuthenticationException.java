package se.davvs.floorballfighters.authorization;

import org.springframework.security.core.AuthenticationException;

public class AdminAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = 2445795128349217922L;

	public AdminAuthenticationException(String msg, Throwable t) {
		super(msg, t);
	}

	public AdminAuthenticationException(String msg) {
		super(msg);
	}

}
