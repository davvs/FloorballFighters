package se.davvs.floorballfighters.csrf;

import static org.springframework.web.servlet.view.UrlBasedViewResolver.REDIRECT_URL_PREFIX;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class ExhibitCSRFAttrbuteInterceptor extends HandlerInterceptorAdapter  {

	@Inject CSRFTokenManager csrfTokenManager;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception {
		if (!modelAndView.getViewName().startsWith(REDIRECT_URL_PREFIX)){
			csrfTokenManager.addCSRFTokenAttribute(modelAndView, request.getSession());
		}
	}

}
