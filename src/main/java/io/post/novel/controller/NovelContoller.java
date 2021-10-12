package io.post.novel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.post.novel.auth.UserForm;
import io.post.novel.dto.NovelRequest;
import io.post.novel.entity.NovelEntity;
import io.post.novel.service.NovelService;

@Controller
public class NovelContoller {
	
	
	@Autowired NovelService novelService;

	@RequestMapping("/novel/write")
	public String toNovelPage(@ModelAttribute NovelRequest draft, Model model) {
		
		return "novel/new_novel";
	}
	
	@PostMapping("/novel/save/draft")
	public String saveDraft(@ModelAttribute @Validated NovelRequest draft , BindingResult result, Model model,@AuthenticationPrincipal UserForm userForm) {
		
		 if (result.hasErrors()) {
			  
			  return "novel/new_novel";//エラー時は自画面遷移
	        }
		
		long userId = userForm.getId();
		draft.setUserId(userId);
		System.out.println(draft);
		
		//下書き保存
		novelService.save(draft);
		
		//下書き一覧取得
		//List<NovelEntity> novelList = novelService.draftList(draft.getUserId());
		//model.addAttribute("novel_list",novelList);
		
		return "redirect:/novel/draft/list";
	}
	
	@RequestMapping("novel/draft/list")
	public String draftList(Model model,@AuthenticationPrincipal UserForm userForm) {
		List<NovelEntity> novelList = novelService.draftList(userForm.getId());
		model.addAttribute("novel_list",novelList);
		return "novel/draft_list";
	}
	
	/*
	 * comingsoonへの遷移
	 */
	@GetMapping("novel/in_preparation")
	public String comingsoon(Model model,@AuthenticationPrincipal UserForm userForm) {
		String loginUser = userForm.getPenName();
		
		model.addAttribute("login_user",loginUser);
		return"novel/in_preparation";
	}
}
