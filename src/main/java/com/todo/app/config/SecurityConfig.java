package com.todo.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/**
	 * パスワードをBCryptでハッシュ化するためのエンコーダーを提供
	 * @return BCryptPasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // BCryptを使用してパスワードをハッシュ化
	}

	/**
	 * セキュリティフィルターの設定
	 * - 認証なしでアクセス可能なページ（ログインページ、登録ページ、CSSファイル）を設定
	 * - それ以外のページへのアクセスは認証が必要
	 * - ログイン後、トップページにリダイレクト
	 * - ログアウト処理の設定
	 *
	 * @param http HttpSecurity
	 * @return SecurityFilterChain
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/login", "/register", "/css/**").permitAll() // ログインページ、登録ページ、CSSファイルは認証なしでアクセス可能
						.anyRequest().authenticated() // その他のリクエストは認証必須
				)
				.formLogin(form -> form
						.loginPage("/login") // ログインページのURL
						.defaultSuccessUrl("/", true) // ログイン成功時にトップページへリダイレクト
						.permitAll())
				.logout(logout -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // "/logout"でログアウト
						.logoutUrl("/logout") // ログアウト処理のURL
						.logoutSuccessUrl("/logout") // ログアウト成功後に指定したURLへリダイレクト
						.permitAll());

		return http.build();
	}
}
