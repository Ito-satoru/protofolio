package com.todo.app.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.todo.app.entity.User;

public class UserPrincipal implements UserDetails {

	private User user; // Userエンティティのインスタンス。ログインユーザの情報を格納

	/**
	 * コンストラクタ。Userエンティティを受け取ってUserPrincipalを作成
	 * 
	 * @param user Userエンティティ（ログインユーザ情報）
	 */
	public UserPrincipal(User user) {
		this.user = user;
	}

	/**
	 * ユーザの権限を取得するメソッド
	 * 現在は単純に「USER」というロールを持つことにしている
	 * 
	 * @return ユーザの権限（"USER"）
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("USER")); // 権限を「USER」に設定
	}

	/**
	 * ユーザのパスワードを取得するメソッド
	 * 
	 * @return ユーザのパスワード
	 */
	@Override
	public String getPassword() {
		return user.getPassword(); // Userエンティティからパスワードを取得
	}

	/**
	 * ユーザ名を取得するメソッド
	 * 
	 * @return ユーザ名
	 */
	@Override
	public String getUsername() {
		return user.getUsername(); // Userエンティティからユーザ名を取得
	}

	/**
	 * アカウントが期限切れでないかを判定するメソッド
	 * 
	 * @return 常に「アカウント期限切れでない」とする（true）
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true; // 常に「アカウント期限切れでない」とする
	}

	/**
	 * アカウントがロックされていないかを判定するメソッド
	 * 
	 * @return 常に「アカウントがロックされていない」とする（true）
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true; // 常に「アカウントがロックされていない」とする
	}

	/**
	 * 認証情報（パスワード）が期限切れでないかを判定するメソッド
	 * 
	 * @return 常に「認証情報が期限切れでない」とする（true）
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true; // 常に「認証情報が期限切れでない」とする
	}

	/**
	 * ユーザが有効かを判定するメソッド
	 * 
	 * @return 常に「ユーザが有効」とする（true）
	 */
	@Override
	public boolean isEnabled() {
		return true; // 常に「ユーザが有効」とする
	}
}
