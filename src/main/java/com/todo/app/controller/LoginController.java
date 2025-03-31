package com.todo.app.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todo.app.entity.User;
import com.todo.app.entity.UserTodoDetail;
import com.todo.app.repository.TodoRepository;
import com.todo.app.repository.UserRepository;
import com.todo.app.service.CalenderService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	// ユーザーリポジトリ、Todoリポジトリ、カレンダーサービスを注入
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private CalenderService calenderService;

	/**
	 * ログインページを返す
	 * 
	 * @return "login" ログインテンプレート
	 */
	@GetMapping("/login")
	public String login() {
		return "login"; // ログインページを返す
	}

	/**
	 * 認証後、インデックスページへリダイレクト
	 * 
	 * @param session セッション
	 * @return インデックスページ、またはログインページ
	 */
	@GetMapping("/")
	public String redirectToIndex(HttpSession session) {
		// ユーザーが認証されているか確認
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			User user = userRepository.findByUsername(username);
			if (user != null) {
				session.setAttribute("userId", user.getId().longValue()); // ユーザーIDをセッションに保存
			} else {
				System.out.println("ログインユーザーが見つかりません！");
			}
			return "redirect:/index"; // インデックスページへリダイレクト
		}
		return "redirect:/login"; // 認証されていない場合、ログインページへリダイレクト
	}

	/**
	 * インデックスページ（カレンダー）を表示
	 * 
	 * @param year 年（オプション）
	 * @param month 月（オプション）
	 * @param session セッション
	 * @param model モデル
	 * @return "index" インデックステンプレート
	 */
	@GetMapping("/index")
	public String index(
			@RequestParam(name = "year", required = false) Integer year,
			@RequestParam(name = "month", required = false) Integer month,
			HttpSession session,
			Model model) {

		// 認証されたユーザーの情報を取得
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = userRepository.findByUsername(username);

		if (user != null) {
			model.addAttribute("username", user.getUsername()); // ユーザー名をモデルに追加
		} else {
			model.addAttribute("username", "ゲスト"); // 認証されていない場合「ゲスト」と表示
		}

		// 年月が指定されていない場合、現在の年月を使用
		if (year == null || month == null) {
			LocalDate today = LocalDate.now();
			year = today.getYear();
			month = today.getMonthValue();
		}

		// カレンダーを生成
		List<List<LocalDate>> calendar = calenderService.generateCalendar(year, month);

		// セッションからユーザーIDを取得
		Long userId = (Long) session.getAttribute("userId");

		// ToDoアイテムを取得（指定された年月）
		List<UserTodoDetail> todos = todoRepository.findByUserIdAndDueDateBetween(userId,
				LocalDate.of(year, month, 1),
				LocalDate.of(year, month, YearMonth.of(year, month).lengthOfMonth()));

		// ToDoアイテムを日付ごとにマップに整理
		Map<String, List<UserTodoDetail>> todoMap = new HashMap<>();
		for (UserTodoDetail todo : todos) {
			todoMap.computeIfAbsent(todo.getDueDate().toString(), k -> new ArrayList<>()).add(todo);
		}

		// 前月と翌月の情報を計算
		YearMonth currentMonth = YearMonth.of(year, month);
		YearMonth prevMonth = currentMonth.minusMonths(1);
		YearMonth nextMonth = currentMonth.plusMonths(1);

		// モデルにカレンダーや年月、ToDo情報などを追加
		model.addAttribute("calendar", calendar);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("todoMap", todoMap);
		model.addAttribute("prevYear", prevMonth.getYear());
		model.addAttribute("prevMonth", prevMonth.getMonthValue());
		model.addAttribute("nextYear", nextMonth.getYear());
		model.addAttribute("nextMonth", nextMonth.getMonthValue());

		// 祝日情報を取得してモデルに追加
		Map<String, String> holidays = calenderService.getHolidays(year, month);
		model.addAttribute("holidays", holidays);

		// indexページを返す
		return "index"; // インデックステンプレートを返す
	}

	/**
	 * ログアウトページを返す
	 * 
	 * @return "logout" ログアウトテンプレート
	 */
	@GetMapping("/logout")
	public String logout() {
		return "logout"; // ログアウトページを返す
	}
}
