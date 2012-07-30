package net.petersson.googl.analytics;

import java.util.Map;

import org.apache.log4j.Logger;

public class AnalyticsResponse {

	private transient static Logger logger = Logger.getLogger(AnalyticsResponse.class);

	private String kind;
	private String id;
	private String longUrl;
	private String status;
	private java.util.Date created;

	private Map<String, Map<String, Object>> analytics;

	public String getKind() {
		return kind;
	}

	public String getId() {
		return id;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public String getStatus() {
		return status;
	}

	public java.util.Date getCreated() {
		return created;
	}

	public Analytics getAnalytics() {
		return new Analytics(analytics);
	}

}
