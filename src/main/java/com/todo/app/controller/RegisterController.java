package com.todo.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.todo.app.entity.User;
import com.todo.app.entity.UserDto;
import com.todo.app.service.UserService;

@Controller
public class RegisterController {

	// UserServiceを注入（ユーザーの登録と検索を担当）
	@Autowired
	private UserService userService;

	/**
	 * 新規ユーザー登録フォームを表示
	 * 
	 * @return ModelAndView 登録フォームを表示するためのビュー
	 */
	@GetMapping("/register")
	public ModelAndView registerForm() {
		// ModelAndViewを使って、UserDtoオブジェクトをモデルに追加し、登録フォームページを設定
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", new UserDto()); // 空のUserDtoオブジェクトをモデルに追加
		mav.setViewName("register"); // "register"という名前のビューを返す
		return mav;
	}

	/**
	 * 新規ユーザーを登録
	 * 
	 * @param userDto ユーザー情報を格納したDTOオブジェクト
	 * @return String ログインページへのリダイレクト
	 */
	@PostMapping("/register")
	public String existingUser(@ModelAttribute UserDto userDto) {
		// ユーザー名が既に存在しているかを確認
		User existing = userService.findByUsername(userDto.getUsername());

		// もし既に存在していた場合、登録ページに戻る
		if (existing != null) {
			return "register"; // 重複ユーザーがいる場合、登録ページを再表示
		}

		// 新規ユーザーを保存
		userService.save(userDto);
		// 登録後、ログインページにリダイレクト
		return "login";
	}
}
