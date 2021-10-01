package io.post.novel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.post.novel.dto.UserRequest;
import io.post.novel.service.UserSignUpService;

@Controller
public class UserSignUpController {
	
	@Autowired
	UserSignUpService userSignUpService;
	
	@Autowired
	PasswordEncoder encoder;
	
	/*
	 * @return トップ画面
	 */
	@GetMapping("/top")
	public String topDislay(){
		return "top/top";
	}
	
	
	/*
	 * @return サインアップフォーム
	 */
	@RequestMapping("/signup")
	public String signUpForm(@ModelAttribute UserRequest usesrRequest) {
		return "signup/sign_up_form";
	}
	
	/*
	 * @return 入力確認
	 */
	@PostMapping("/input/check")
	public String inputCheckDisplay(@ModelAttribute @Validated UserRequest userRequest, BindingResult result , Model model) {
	
		
		  if (result.hasErrors()) {
			  
			  return "signup/sign_up_form";
	        }
		
		model.addAttribute("user_input", userRequest);
		
		/*
		 * System.out.println(userRequest.getPenName() //コンソールの出力チェック
				+ userRequest.getEMail()
				+ userRequest.getPassword()
				+ "西暦" + userRequest.getBirthYear() + "年"
				+ userRequest.getBirthMonth() + "月"
				+ userRequest.getBirthDay() + "日"
				+ userRequest.getUserCategory());
		*/
		
		return "signup/input_check";
	}
	/*
	 * 登録処理実施＞トップ画面へリダイレクト
	 */
	
	@RequestMapping(value = "/signup/complete", method = RequestMethod.POST)
	public String signUp(@ModelAttribute UserRequest userAdd,Model model) {
		
		/*
		 * System.out.println(userAdd.getPenName() //コンソールの出力チェック
		 
				+ userAdd.getEMail()
				+ userAdd.getPassword()
				+ userAdd.getBirthYear() + "年"
				+ userAdd.getUserCategory());
		*/
		//String rawPassword = userAdd.getPassword();
		//userAdd.setPassword(encoder.encode(rawPassword));
		
		userSignUpService.create(userAdd);
		
		
		return "redirect:/top";
	}
	
	
	
	
	
}
