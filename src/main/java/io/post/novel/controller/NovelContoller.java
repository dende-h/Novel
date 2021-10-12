package io.post.novel.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.support.SessionStatus;

import io.post.novel.auth.UserForm;
import io.post.novel.dto.NovelRequest;
import io.post.novel.entity.NovelEntity;
import io.post.novel.service.NovelService;

@Controller
public class NovelContoller {
	
	
	@Autowired NovelService novelService;
	@Autowired HttpSession session;
	
	
	/*
	 * フォームへ遷移
	 */
	@RequestMapping("/novel/write")
	public String toNovelPage( @ModelAttribute NovelRequest draft, Model model, HttpServletRequest request) {
		try { 
			boolean req = session.getAttribute("genre").equals(0);
			
			if(req == false){
				
				int sessionGenre = (int)session.getAttribute("genre");
				int sessionlLength = (int)session.getAttribute("length");
				String sessionTitle = (String)session.getAttribute("title");
				String sessionText = (String)session.getAttribute("text");
				
				draft.setGenre(sessionGenre);
				draft.setNovelLength(sessionlLength);
				draft.setTitle(sessionTitle);
				draft.setText(sessionText);
				System.out.println(draft);
				
				model.addAttribute("draft",draft);
				
				return "novel/new_novel";
			} 
		}
		//System.out.println(req);
		//int draftSession = principal.getGenre();
		//System.out.println(draftSession);
		catch (NullPointerException e){
			
			return "novel/new_novel";
		}

		return "novel/new_novel";
	}
	
	/*
	 * 下書き保存＝>下書きリストへリダイレクト
	 */
	@PostMapping(value = "/novel/save/draft",params = "draft_save" )
	public String saveDraft(@ModelAttribute @Validated NovelRequest draft , BindingResult result, Model model,@AuthenticationPrincipal UserForm userForm,SessionStatus sessionStatus) {
		
		/*
		 * タイトル、ジャンル、長さは入力必須（メッセージ未実装）
		 */
		
		 if (result.hasErrors()) {
			  
			  return "novel/new_novel";//エラー時は自画面遷移
	        }
		
		long userId = userForm.getId();
		draft.setUserId(userId);
		//System.out.println(draft);出力確認
		
		//下書き保存
		novelService.save(draft);
		
		//下書き一覧取得
		//List<NovelEntity> novelList = novelService.draftList(draft.getUserId());
		//model.addAttribute("novel_list",novelList);
		sessionStatus.setComplete();
		
		return "redirect:/novel/draft/list";
	}
	
	
	@PostMapping(value = "/novel/save/draft",params = "draft_session" )
	public String setSession(@ModelAttribute @Validated NovelRequest novelSession, BindingResult result,Model model) {
		 if (result.hasErrors()) {
			  
			  return "novel/new_novel";//エラー時は自画面遷移
	        }
		session.setAttribute("genre", novelSession.getGenre());
		session.setAttribute("length", novelSession.getNovelLength());
		session.setAttribute("title", novelSession.getTitle());
		session.setAttribute("text", novelSession.getText());
		//System.out.println(novelSession);
		return "redirect:/novel/write";
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
