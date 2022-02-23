package com.axisb.ews.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PublicController {
	

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public Object redirectPage(HttpServletRequest request) {

		String redirectUrl = "/public/login";
        return "redirect:" + redirectUrl;
	}
	
	@RequestMapping(value = { "/public/login" }, method = RequestMethod.GET)
	public String login() {
		return "index";
	}
	
	@RequestMapping(value = { "/public/home" }, method = RequestMethod.GET)
	public String home() {
		
		return "home";
	}
	
	@RequestMapping(value = { "/public/admin" }, method = RequestMethod.GET)
	public String admin() {
		
		return "admin";
	}

}
