package se.davvs.floorballfighters.authorization;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class AdminAuthenticationRoles {
	private SimpleGrantedAuthority roleAccess;
	private SimpleGrantedAuthority roleAdmin;
	private List<SimpleGrantedAuthority> allAvailableAuths;

	@PostConstruct
	void prepareAllRoles(){
		allAvailableAuths = new ArrayList<SimpleGrantedAuthority>();
		
		roleAccess = new SimpleGrantedAuthority("ROLE_ACCESS");
		roleAdmin = new SimpleGrantedAuthority("ROLE_ADMIN");
		
		allAvailableAuths.add(roleAccess);
		allAvailableAuths.add(roleAdmin);
	}
	
	public SimpleGrantedAuthority getRoleAccess() {
		return roleAccess;
	}

	public SimpleGrantedAuthority getRoleAdmin() {
		return roleAdmin;
	}

	public List<SimpleGrantedAuthority> getAllAvailableAuths() {
		return allAvailableAuths;
	}

}
