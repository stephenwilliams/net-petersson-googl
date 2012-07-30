package net.petersson.googl.analytics;

import java.util.Map;

public class Analytics {

	private Period allTime;
	private Period month;
	private Period week;
	private Period day;
	private Period twoHours;

	public Analytics(Map<String, Map<String, Object>> analytics) {
		this.allTime = new Period(analytics.get("allTime"));
		this.month = new Period(analytics.get("month"));
		this.week = new Period(analytics.get("week"));
		this.day = new Period(analytics.get("day"));
		this.twoHours = new Period(analytics.get("twoHours"));
	}

	public Period getAllTime() {
		return this.allTime;
	}

	public Period getMonth() {
		return this.month;
	}

	public Period getWeek() {
		return this.week;
	}

	public Period getDay() {
		return this.day;
	}

	public Period getTwoHours() {
		return this.twoHours;
	}
}