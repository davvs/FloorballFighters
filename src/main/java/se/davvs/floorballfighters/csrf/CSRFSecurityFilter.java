package se.davvs.floorballfighters.csrf;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

public class CSRFSecurityFilter implements Filter {

	Logger logger;	
	@Inject CSRFTokenManager csrfTokenManager;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;

	    if (request.getMethod().equalsIgnoreCase("POST") ) {
	      // This is a POST request - need to check the CSRF token
	        String sessionToken = csrfTokenManager.getTokenForSession(request.getSession());
	        String requestToken = csrfTokenManager.getTokenFromRequest(request);
	        if (sessionToken.equals(requestToken)) {
	     	   csrfTokenManager.generateTokenForSession(request.getSession());
	        } else {
	        	logger.info("Bad or missing CSRF Token");
	        	throw new CSRFInvalidException("Bad or missing CSRF Token");
	       }
	    } 

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
