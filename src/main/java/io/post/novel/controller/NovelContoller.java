package io.post.novel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.post.novel.auth.UserForm;
import io.post.novel.dto.NovelRequest;
import io.post.novel.service.NovelService;

@Controller
public class NovelContoller {
	
	
	@Autowired NovelService novelService;

	@RequestMapping("/novel/write")
	public String toNovelPage(@ModelAttribute NovelRequest draft, Model model) {
		
		return "novel/new_novel";
	}
	
	@PostMapping("/novel/save/draft")
	public String saveDraft(@ModelAttribute NovelRequest draft , Model model,@AuthenticationPrincipal UserForm userForm) {
		
		long userId = userForm.getId();
		draft.setUserId(userId);
		System.out.println(draft);
		novelService.save(draft);
		
		return "novel/new_novel";
	}
}
