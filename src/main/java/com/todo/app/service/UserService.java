package com.todo.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.app.entity.User;
import com.todo.app.entity.UserDto;
import com.todo.app.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service // サービス層としての役割を持つクラス
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository; // Userエンティティを扱うリポジトリ

	@Autowired
	private PasswordEncoder passwordEncoder; // パスワードを暗号化するためのPasswordEncoder

	/**
	 * Spring SecurityのUserDetailsServiceインターフェースの実装
	 * ユーザー名に基づいてユーザー情報をロードします
	 * 
	 * @param username ユーザー名
	 * @return ユーザー情報（UserPrincipalとして）
	 * @throws UsernameNotFoundException ユーザが見つからない場合にスロー
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// ユーザー名でユーザを検索
		User user = userRepository.findByUsername(username);
		if (user == null) {
			// ユーザが見つからなかった場合、例外をスロー
			throw new UsernameNotFoundException("User not found");
		}
		// ユーザー情報をUserPrincipalとして返す
		return new UserPrincipal(user);
	}

	/**
	 * ユーザー名でユーザーを検索するメソッド
	 * 
	 * @param username ユーザー名
	 * @return Userエンティティ
	 */
	public User findByUsername(String username) {
		return userRepository.findByUsername(username); // リポジトリを使ってユーザーを検索
	}

	/**
	 * 新しいユーザーを保存するメソッド
	 * ユーザー登録処理で使用
	 * 
	 * @param userDto ユーザー情報（DTO）を受け取って保存
	 */
	@Transactional // トランザクション管理。データベースへの保存を1つのトランザクションとして扱う
	public void save(UserDto userDto) {
		// UserDtoからUserエンティティを作成
		User user = new User();
		user.setUsername(userDto.getUsername()); // ユーザ名を設定
		user.setPassword(passwordEncoder.encode(userDto.getPassword())); // パスワードを暗号化して設定
		user.setEmail(userDto.getEmail()); // メールアドレスを設定

		// ユーザーをリポジトリに保存
		userRepository.save(user);
	}
}
