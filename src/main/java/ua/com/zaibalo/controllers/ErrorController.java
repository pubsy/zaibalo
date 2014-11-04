package ua.com.zaibalo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

	@RequestMapping("/error")
	public String customError() {
		return "error/error";
	}
	
	@RequestMapping("/404")
	public String notFound() {
		return "error/404";
	}
	
	
	@RequestMapping("/400")
	public String badRequest() {
		return "error/400";
	}
}
