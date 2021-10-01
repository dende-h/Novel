package io.post.novel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserLoginController {
	
	@GetMapping("/sign_in")
	public String userLogin() {
	
		return "login/login";
	}
}
