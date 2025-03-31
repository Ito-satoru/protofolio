package com.todo.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.app.entity.User;
import com.todo.app.entity.UserTodoDetail;
import com.todo.app.form.TodoForm;
import com.todo.app.repository.TodoRepository;
import com.todo.app.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service // このクラスはサービス層のコンポーネントであり、ビジネスロジックを処理します
public class SaveTodoSerive {

	@Autowired
	private TodoRepository todoRepository; // Todoのデータを操作するためのリポジトリ

	@Autowired
	private UserRepository userRepository; // ユーザ情報を操作するためのリポジトリ

	/**
	 * TodoFormから受け取ったデータを元にTodo情報を保存するメソッド
	 * 
	 * @param todoForm ユーザが入力したTodoフォームのデータ
	 * @param session  現在のセッション情報（ログイン中のユーザIDを取得）
	 */
	public void save(TodoForm todoForm, HttpSession session) {
		// セッションから現在ログインしているユーザのIDを取得
		Long userId = (Long) session.getAttribute("userId");

		// ユーザがログインしていない場合、例外をスロー
		if (userId == null) {
			throw new IllegalStateException("ユーザがログインしていません");
		}

		// ユーザIDに対応するユーザ情報をデータベースから取得
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalStateException("ユーザが存在しません"));

		// 新しいTodo詳細を作成
		UserTodoDetail userTodoDetail = new UserTodoDetail();
		userTodoDetail.setTitle(todoForm.getTitle()); // タイトルを設定
		userTodoDetail.setDueDate(todoForm.getDueDate()); // 期限を設定
		userTodoDetail.setStatus(todoForm.getStatus()); // ステータスを設定
		userTodoDetail.setDetail(todoForm.getDetail()); // 詳細情報を設定
		userTodoDetail.setUser(user); // ユーザ情報を設定

		// Todo詳細をデータベースに保存
		todoRepository.save(userTodoDetail);
	}
}
