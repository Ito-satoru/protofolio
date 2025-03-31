package com.todo.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.todo.app.form.TodoForm;
import com.todo.app.service.SaveTodoSerive;

import jakarta.servlet.http.HttpSession;

@Controller
public class NewTodoController {

	// SaveTodoServiceを注入（ToDoの保存を担当）
	@Autowired
	private SaveTodoSerive saveTodoService;

	/**
	 * 新しいToDo作成用のフォームを表示
	 * 
	 * @param model モデル
	 * @return "NewTodo.html" 新規ToDo作成ページ
	 */
	@GetMapping("/newTodo")
	public String newTodo(Model model) {
		// 新しいToDoのフォームを表示するため、TodoFormオブジェクトをモデルに追加
		model.addAttribute("TodoForm", new TodoForm());
		// 新規Todo作成用のHTMLページを返す
		return "NewTodo.html"; // 新規ToDo作成ページ
	}

	/**
	 * フォームから送信されたToDoデータを保存
	 * 
	 * @param todoForm ToDoのフォームデータ
	 * @param session セッション
	 * @return "redirect:/index" インデックスページにリダイレクト
	 */
	@PostMapping("/newtodo")
	public String saveTodo(@ModelAttribute TodoForm todoForm, HttpSession session) {
		// フォームから送信されたToDoデータを保存
		saveTodoService.save(todoForm, session);
		// 保存後、インデックスページにリダイレクト
		return "redirect:/index"; // インデックスページへリダイレクト
	}
}
