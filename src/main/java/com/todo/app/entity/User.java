package com.todo.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// ユーザ登録用エンティティクラス
@Entity // データベースの "Users" テーブルにマッピングされるクラス
@Table(name = "Users") // 対応するテーブル名
public class User {

	// ユーザーを一意に識別するためのID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // IDは自動的に増加
	@Column(name = "id") // "id" カラムに対応
	private Integer id;

	// "username" カラム。ユーザー名はユニークでNULLを許容しない
	@Column(name = "username", nullable = false, unique = true)
	private String username;

	// "password" カラム。パスワードはNULLを許容しない
	@Column(name = "password", nullable = false)
	private String password;

	// "email" カラム。メールアドレスはユニークでNULLを許容しない
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	// ゲッターとセッター

	/**
	 * ユーザーIDを取得するゲッターメソッド
	 * 
	 * @return id ユーザーID
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ユーザー名を取得するゲッターメソッド
	 * 
	 * @return username ユーザー名
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * パスワードを取得するゲッターメソッド
	 * 
	 * @return password ユーザーパスワード
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * メールアドレスを取得するゲッターメソッド
	 * 
	 * @return email ユーザーのメールアドレス
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * ユーザーIDを設定するセッターメソッド
	 * 
	 * @param id ユーザーID
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ユーザー名を設定するセッターメソッド
	 * 
	 * @param username ユーザー名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * パスワードを設定するセッターメソッド
	 * 
	 * @param password ユーザーパスワード
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * メールアドレスを設定するセッターメソッド
	 * 
	 * @param email ユーザーのメールアドレス
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
