package se.davvs.floorballfighters.authorization;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextHolderWrapper {

	public SecurityContext getContext(){
		return SecurityContextHolder.getContext();
	}

	public Authentication getAuthentication(){
		return getContext().getAuthentication();
	}

	public UserDetails getUserDetails(){
		return (UserDetails) getAuthentication().getDetails();
	}
}
