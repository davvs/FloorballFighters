package se.davvs.floorballfighters.csrf;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class CSRFTokenManager {

	private static final String CSRF_TOKEN_SESSION_KEY = "__csrf_token";
	private static final String CSRF_ATTRIBUTE_NAME = "csrfToken";
	
	public String generateTokenForSession(HttpSession session){
		String csrf = UUID.randomUUID().toString();
		session.setAttribute(CSRF_TOKEN_SESSION_KEY, csrf);
		return csrf;
	}
	
	public String getTokenForSession(HttpSession session) {

		String csrf = (String) session.getAttribute(CSRF_TOKEN_SESSION_KEY);
		if (csrf == null){
			csrf = generateTokenForSession(session);
		}
		return csrf;
	}

	public String getTokenFromRequest(HttpServletRequest request) {
		String csrf = request.getParameter(CSRF_ATTRIBUTE_NAME);
		return csrf;
	}

	public void addCSRFTokenAttribute(ModelAndView modelAndView, HttpSession session){
		modelAndView.addObject(CSRF_ATTRIBUTE_NAME, getTokenForSession(session));
	}

	public String getCSRFTokenAttribute(HttpServletRequest request){
		return request.getParameter(CSRF_ATTRIBUTE_NAME);
	}

}
