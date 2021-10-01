package io.post.novel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserSignOutController {

	/*
	 * ログアウトボタンでトップページにリダイレクト
	 */
	
	@GetMapping("/signout")
	public String signout() {
		
		return "redirect:/top";
		
	}
}
