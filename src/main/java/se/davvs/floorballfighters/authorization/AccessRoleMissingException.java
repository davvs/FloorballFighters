package se.davvs.floorballfighters.authorization;

import org.springframework.security.core.AuthenticationException;

public class AccessRoleMissingException extends AuthenticationException {
	private static final long serialVersionUID = 1L;

	public AccessRoleMissingException(String error) {
		super(error);
	}
}
