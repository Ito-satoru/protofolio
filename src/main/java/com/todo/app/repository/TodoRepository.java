package com.todo.app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository; // JpaRepositoryを継承することで、基本的なCRUD操作が提供される
import org.springframework.stereotype.Repository; // Springのコンポーネントスキャンでリポジトリとして認識されるようにする

import com.todo.app.entity.UserTodoDetail; // Todoの詳細情報を管理するエンティティ

@Repository // このインターフェースはリポジトリであることを示すアノテーション
public interface TodoRepository extends JpaRepository<UserTodoDetail, Long> {

	// ユーザーIDと期限日付の範囲に基づいてTodoリストを検索するメソッド
	// 引数としてユーザーID、開始日、終了日を受け取る
	List<UserTodoDetail> findByUserIdAndDueDateBetween(Long userId, LocalDate start, LocalDate end);
}
