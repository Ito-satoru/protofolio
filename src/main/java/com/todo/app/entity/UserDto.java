package com.todo.app.entity;

import jakarta.validation.constraints.NotEmpty; // 空でないことを検証するためのアノテーション

// ユーザー登録時に使用するデータ転送オブジェクト（DTO）クラス
public class UserDto {

	// ユーザー名が空でないことを検証
	@NotEmpty
	private String username;

	// パスワードが空でないことを検証
	@NotEmpty
	private String password;

	// メールアドレスが空でないことを検証
	@NotEmpty
	private String email;

	// ゲッターとセッター

	/**
	 * ユーザー名を取得するメソッド
	 * 
	 * @return username ユーザー名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * ユーザー名を設定するメソッド
	 * 
	 * @param username ユーザー名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * パスワードを取得するメソッド
	 * 
	 * @return password パスワード
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * パスワードを設定するメソッド
	 * 
	 * @param password パスワード
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * メールアドレスを取得するメソッド
	 * 
	 * @return email メールアドレス
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * メールアドレスを設定するメソッド
	 * 
	 * @param email メールアドレス
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
