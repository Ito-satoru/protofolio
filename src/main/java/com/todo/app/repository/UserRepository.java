package com.todo.app.repository;

import org.springframework.data.jpa.repository.JpaRepository; // JpaRepositoryを継承することで、基本的なCRUD操作が提供される
import org.springframework.stereotype.Repository; // Springのコンポーネントスキャンでリポジトリとして認識されるようにする

import com.todo.app.entity.User; // ユーザー情報を管理するエンティティ

@Repository // このインターフェースはリポジトリであることを示すアノテーション
public interface UserRepository extends JpaRepository<User, Long> {

	// ユーザー名（username）に基づいてユーザー情報を検索するメソッド
	// 引数としてユーザー名を受け取り、そのユーザーに対応するUserエンティティを返す
	User findByUsername(String username);
}
