package com.techacademy.controller;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.techacademy.entity.User;
import com.techacademy.service.UserService;

@Controller
@RequestMapping("user")
public class UserController{
	private final UserService service;

	public UserController(UserService service){
		this.service = service;
	}

	/** 一覧画面を表示 */
	@GetMapping("/list")
	public String getList(Model model) {
		// 全件検索結果をModelに登録
		model.addAttribute("userlist", service.getUserList());
		// user/list.htmlに画面遷移
		return "user/list";
	}

	//Lesson 18Chapter 7追加
	/** User登録画面を表示 */
	@GetMapping("/register")
	public String getRegister(@ModelAttribute User user) { //@ModelAttributeとmodel
		return "user/register";
	}
	/** User登録処理 */
	@PostMapping("/register")
	public String postRegister(@Validated User user, BindingResult res, Model model) { //Lesson18Chapter10変更
		//Lesson18Chapter10追加
		if(res.hasErrors()) {
			//エラーあり
			return getRegister(user);
		}
		// User登録
		service.saveUser(user);
		// 一覧画面にリダイレクト
		return "redirect:/user/list";
	}

	//Lesson18 Chapter8追加
	/** User更新画面を表示 */
//	@GetMapping("/update/{id}/")
//	public String getUser(@PathVariable("id") Integer id, Model model) {
//		// Modelに登録
//		model.addAttribute("user", service.getUser(id));
//		return "user/update";
//	}
	//課題
	@GetMapping("/update/{id}/")
	public String getUser(User user,@PathVariable("id") Integer id, Model model) { //
		if(id == null) {
			//異常系・エラー
			model.addAttribute("user", user);
		}else {
			// Modelに登録
			model.addAttribute("user", service.getUser(id)); //サービスでDBに探している
		}
		return "user/update";
	}

	/** User更新処理 */
//	public String postUser(User user) {
//		service.saveUser(user);
//		return "redirect:/user/list";
//	}
	//課題
	@PostMapping("/update/{id}/")
	public String postUser(@Validated User user, BindingResult res, Model model) {
		if(res.hasErrors()) {
			//異常が起こった場合に辿り着く
//			model.addAttribute("id", null);
			return getUser(user, null, model) ; //過去にやってる
		}
		service.saveUser(user);
		return "redirect:/user/list";
	}

	//Lesson18 Chapter9追加
	/** User削除処理 */
	@PostMapping(path="list", params="deleteRun")
	public String deleteRun(@RequestParam(name="idck") Set<Integer> idck, Model model) {
		service.deleteUser(idck);
		return "redirect:/user/list";
	}
}