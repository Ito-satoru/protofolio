package com.todo.app.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service // このクラスはサービス層のコンポーネントであることを示します
public class CalenderService {

	// 公共の祝日情報を取得するAPIのURLテンプレート
	private static final String API_URL = "https://date.nager.at/api/v3/PublicHolidays/{year}/{countryCode}";

	/**
	 * 指定した年と月のカレンダーを生成するメソッド
	 * 
	 * @param year  年
	 * @param month 月
	 * @return カレンダー（リスト）
	 */
	public List<List<LocalDate>> generateCalendar(int year, int month) {
		List<List<LocalDate>> calendar = new ArrayList<>();
		LocalDate firstDayOfMonth = LocalDate.of(year, month, 1); // 月初の日付
		int daysInMonth = firstDayOfMonth.lengthOfMonth(); // 月の日数
		int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue() % 7; // 月の初日の曜日を取得 (0-6)
		List<LocalDate> week = new ArrayList<>();

		// 月初の前に空白を埋める（前月の最終日曜日から始める）
		for (int i = 0; i < startDayOfWeek; i++) {
			week.add(null);
		}

		// 月の日付をカレンダーに追加
		for (int day = 1; day <= daysInMonth; day++) {
			week.add(LocalDate.of(year, month, day)); // 日付を追加
			if (week.size() == 7) { // 1週間が7日になったら
				calendar.add(new ArrayList<>(week)); // 1週間をカレンダーに追加
				week.clear(); // 新しい週を開始
			}
		}

		// 最後の週に余った日付を追加（週が7日未満の場合）
		if (!week.isEmpty()) {
			while (week.size() < 7) {
				week.add(null); // 空白の日付を埋める
			}
			calendar.add(new ArrayList<>(week)); // 余った週をカレンダーに追加
		}

		return calendar; // 生成したカレンダーを返す
	}

	/**
	 * 指定した年と月に該当する祝日を取得するメソッド
	 * 
	 * @param year  年
	 * @param month 月
	 * @return 祝日情報のマップ
	 */
	public Map<String, String> getHolidays(int year, int month) {
		RestTemplate restTemplate = new RestTemplate(); // HTTPリクエストを送るためのRestTemplateを作成
		String countryCode = "JP"; // 祝日データを取得する国コード（ここでは日本）
		String url = API_URL.replace("{year}", String.valueOf(year)).replace("{countryCode}", countryCode);

		Map<String, String> holidayMap = new HashMap<>(); // 祝日を格納するマップ

		try {
			List<Object> holidays = restTemplate.getForObject(url, List.class); // APIから祝日データを取得

			if (holidays != null) {
				// 祝日データが存在する場合、月ごとにフィルタリング
				for (Object holiday : holidays) {
					if (holiday instanceof java.util.Map) {
						// 祝日データがMapの場合、各祝日を解析
						java.util.Map<String, Object> holidayMapData = (java.util.Map<String, Object>) holiday;
						String dateString = (String) holidayMapData.get("date"); // 祝日の日付
						String name = (String) holidayMapData.get("name"); // 祝日名
						LocalDate holidayDate = LocalDate.parse(dateString); // 日付をLocalDateに変換
						if (holidayDate.getMonth() == Month.of(month)) { // 指定した月の祝日だけを追加
							String japaneseName = translateHolidayName(name); // 祝日名を日本語に変換
							holidayMap.put(holidayDate.toString(), japaneseName); // 日付と日本語の祝日名をマップに追加
						}
					}
				}
			}

		} catch (HttpClientErrorException e) {
			e.printStackTrace(); // APIリクエストが失敗した場合はエラーログを出力
		}

		return holidayMap; // 祝日マップを返す
	}

	/**
	 * 祝日名（英語）を日本語に変換するメソッド
	 * 
	 * @param englishName 英語の祝日名
	 * @return 日本語の祝日名
	 */
	private String translateHolidayName(String englishName) {
		switch (englishName) {
		case "New Year's Day":
			return "元日";
		case "Coming of Age Day":
			return "成人の日";
		case "National Foundation Day":
			return "建国記念の日";
		case "Shōwa Day":
			return "昭和の日";
		case "Constitution Memorial Day":
			return "憲法記念日";
		case "Greenery Day":
			return "みどりの日";
		case "Children's Day":
			return "こどもの日";
		case "Marine Day":
			return "海の日";
		case "Mountain Day":
			return "山の日";
		case "Respect for the Aged Day":
			return "敬老の日";
		case "Autumnal Equinox Day":
			return "秋分の日";
		case "Health and Sports Day":
			return "体育の日";
		case "Culture Day":
			return "文化の日";
		case "Labor Thanksgiving Day":
			return "勤労感謝の日";
		case "The Emperor's Birthday":
			return "天皇誕生日";
		case "Spring Equinox Day":
			return "春分の日";
		case "Showa Day":
			return "昭和の日";
		case "Constitution Day":
			return "憲法記念日";
		case "Labour Thanksgiving Day":
			return "勤労感謝の日";
		case "Vernal Equinox Day":
			return "春分の日";
		case "Sports Day":
			return "スポーツの日";
		case "Foundation Day":
			return "建国記念の日";
		default:
			return englishName; // 定義されていない祝日名はそのまま返す
		}
	}
}
