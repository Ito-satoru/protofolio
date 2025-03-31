package com.todo.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.todo.app.entity.UserTodoDetail;
import com.todo.app.form.TodoForm;
import com.todo.app.repository.TodoRepository;

@Controller
public class DetailController {

	// TodoRepositoryを依存性注入
	@Autowired
	private TodoRepository todoRepository;

	/**
	 * Todoアイテムの詳細を表示するメソッド
	 * 
	 * @param id TodoアイテムのID
	 * @param model モデル
	 * @return "todoDetail" テンプレート
	 */
	@GetMapping("/todoDetail/{id}")
	public String todoDetail(@PathVariable("id") Long id, Model model) {
		// Todo IDに対応する詳細情報を取得
		UserTodoDetail todo = todoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid todo ID: " + id));
		model.addAttribute("todo", todo); // Todo情報をモデルに追加
		System.out.println("デバック日付:" + todo.getDueDate());
		return "todoDetail";// todoDetailテンプレートを返す
	}
	//	}

	/**
	 * Todoアイテムの更新フォームを表示するメソッド
	 * 
	 * @param id TodoアイテムのID
	 * @param model モデル
	 * @return "detailUpdate" テンプレート
	 */
	@GetMapping("/detailUpdate/{id}")
	public String detailUpdateForm(@PathVariable("id") Long id, Model model) {
		// Todo IDに対応するTodo情報を取得し、更新フォームを表示
		UserTodoDetail todo = todoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid todo ID: " + id));
		model.addAttribute("todo", todo); // 更新するTodo情報をモデルに追加
		return "detailUpdate"; // detailUpdateテンプレートを返す
	}

	/**
	 * Todoアイテムの情報を更新するメソッド
	 * 
	 * @param id TodoアイテムのID
	 * @param todoForm 更新されたTodoのフォームデータ
	 * @return "redirect:/index" リダイレクト先
	 */
	@PostMapping("/detailUpdate/{id}")
	public String detailUpdate(@PathVariable("id") Long id, @ModelAttribute TodoForm todoForm) {
		// Todo情報を更新して保存
		UserTodoDetail todo = todoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid todo ID: " + id));
		todo.setTitle(todoForm.getTitle()); // タイトル更新
		todo.setDetail(todoForm.getDetail()); // 詳細更新
		todo.setStatus(todoForm.getStatus()); // ステータス更新
		todo.setDueDate(todoForm.getDueDate()); // 期限更新
		todoRepository.save(todo); // 更新内容を保存
		return "redirect:/index"; // 更新後、一覧ページにリダイレクト
	}

	/**
	 * Todoアイテムを削除するメソッド
	 * 
	 * @param id TodoアイテムのID
	 * @return "redirect:/index" リダイレクト先
	 */
	@GetMapping("/detailDelete/{id}")
	public String detailDelete(@PathVariable("id") Long id) {
		// Todoを削除
		if (todoRepository.existsById(id)) {
			todoRepository.deleteById(id); // Todo削除
		}
		return "redirect:/index"; // 削除後、一覧ページにリダイレクト
	}
}
