package com.todo.app.form;

import java.time.LocalDate;

import com.todo.app.config.Status; // Todoの状態を管理する列挙型

import jakarta.validation.constraints.NotBlank; // フィールドが空でないことを検証するアノテーション
import jakarta.validation.constraints.Size; // フィールドの長さ制限を検証するアノテーション

public class TodoForm {

	@NotBlank(message = "タイトルを入力してください") // タイトルが空でないことを検証
	@Size(max = 20, message = "タイトルを20文字以内で入力してください") // タイトルの最大文字数を制限（20文字以内）
	private String title; // Todoのタイトル

	private LocalDate dueDate; // Todoの期日（期限）

	@Size(max = 100, message = "詳細を100文字以内で入力してください") // 詳細の最大文字数を制限（100文字以内）
	private String detail; // Todoの詳細情報

	private Status status; // Todoの状態（列挙型）

	// 以下はゲッターとセッター。これによりフィールドにアクセス可能

	/**
	 * タイトルを取得するゲッターメソッド
	 * 
	 * @return title Todoのタイトル
	 */
	public String getTitle() {
		return this.title; // タイトルを取得
	}

	/**
	 * タイトルを設定するセッターメソッド
	 * 
	 * @param title Todoのタイトル
	 */
	public void setTitle(String title) {
		this.title = title; // タイトルを設定
	}

	/**
	 * Todoの期日を取得するゲッターメソッド
	 * 
	 * @return dueDate Todoの期日（期限）
	 */
	public LocalDate getDueDate() {
		return dueDate; // Todoの期日を取得
	}

	/**
	 * Todoの期日を設定するセッターメソッド
	 * 
	 * @param dueDate Todoの期日（期限）
	 */
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate; // Todoの期日を設定
	}

	/**
	 * Todoの詳細情報を取得するゲッターメソッド
	 * 
	 * @return detail Todoの詳細情報
	 */
	public String getDetail() {
		return this.detail; // 詳細情報を取得
	}

	/**
	 * Todoの詳細情報を設定するセッターメソッド
	 * 
	 * @param detail Todoの詳細情報
	 */
	public void setDetail(String detail) {
		this.detail = detail; // 詳細情報を設定
	}

	/**
	 * Todoの状態を取得するゲッターメソッド
	 * 
	 * @return status Todoの状態（列挙型）
	 */
	public Status getStatus() {
		return this.status; // Todoの状態を取得
	}

	/**
	 * Todoの状態を設定するセッターメソッド
	 * 
	 * @param status Todoの状態（列挙型）
	 */
	public void setStatus(Status status) {
		this.status = status; // Todoの状態を設定
	}
}
