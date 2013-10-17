//package com.javakart.springsecurity.controller;
package se.davvs.floorballfighters.user;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController
{
	@RequestMapping(value="login", method = RequestMethod.GET)
	public String showLoginPage(ModelMap model, @RequestParam(value="error", required=false) String error)
	{
		 if (error != null){
			 if (error.equals("role")){
				 model.addAttribute("error", "Access deined. Access role missing");
			 } else {
				 model.addAttribute("error", "Authentication failed");
			 }
		 }
		 return "user/login";
	}
	
	@RequestMapping(value="details", method = RequestMethod.GET)
	public String showUserDetails(HttpServletRequest request, ModelMap model)
	{
		return "user/userDetails";
	}

}