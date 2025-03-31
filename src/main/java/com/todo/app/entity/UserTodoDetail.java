package com.todo.app.entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.todo.app.config.Status; // Todoのステータスを管理する列挙型

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity // このクラスはデータベースのエンティティであることを示します
@Table(name = "to_detail") // このクラスが対応するテーブルは "to_detail" です
public class UserTodoDetail {

	// ユーザTodo詳細を一意に識別するためのID
	@Id // このフィールドがエンティティの一意の識別子であることを示します
	@Column(name = "id") // データベースの "id" カラムに対応
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自動的にIDを生成
	private Integer id;

	// 多対一のリレーションを持つUserエンティティ
	@ManyToOne // Userエンティティと多対一の関係を持つことを示します
	@JoinColumn(name = "user_id", nullable = false) // "user_id" カラムを使用して関連付け
	private User user;

	// Todoのタイトルを保存するカラム
	@Column(name = "title")
	private String title;

	// Todoの詳細情報を保存するカラム
	@Column(name = "detail")
	private String detail;

	// Todoの期限を保存するカラム
	@Column(name = "duedate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dueDate;

	// Todoの状態を保存するカラム
	@Column(name = "status")
	@Enumerated(EnumType.STRING) // 列挙型を文字列として保存
	private Status status;

	// ゲッターとセッター

	/**
	 * IDを取得するメソッド
	 * 
	 * @return id ユーザTodo詳細のID
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * IDを設定するメソッド
	 * 
	 * @param id ユーザTodo詳細のID
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ユーザ情報を取得するメソッド
	 * 
	 * @return user ユーザ情報
	 */
	public User getUser() {
		return user;
	}

	/**
	 * ユーザ情報を設定するメソッド
	 * 
	 * @param user ユーザ情報
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Todoのタイトルを取得するメソッド
	 * 
	 * @return title Todoのタイトル
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Todoのタイトルを設定するメソッド
	 * 
	 * @param title Todoのタイトル
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Todoの詳細情報を取得するメソッド
	 * 
	 * @return detail Todoの詳細情報
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * Todoの詳細情報を設定するメソッド
	 * 
	 * @param detail Todoの詳細情報
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * Todoの期限を取得するメソッド
	 * 
	 * @return dueDate Todoの期限
	 */
	public LocalDate getDueDate() {
		return dueDate;
	}

	/**
	 * Todoの期限を設定するメソッド
	 * 
	 * @param dueDate Todoの期限
	 */
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * Todoの状態を取得するメソッド
	 * 
	 * @return status Todoの状態
	 */
	public Enum getStatus() {
		return status;
	}

	/**
	 * Todoの状態を設定するメソッド
	 * 
	 * @param status Todoの状態
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
}
