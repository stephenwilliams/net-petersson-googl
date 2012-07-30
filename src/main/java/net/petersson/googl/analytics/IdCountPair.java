package net.petersson.googl.analytics;

import java.util.Map;

public final class IdCountPair {

	private String id;
	private long count;
	private Map<String, String> map;

	public IdCountPair(Map<String, String> map) {
		this.map = map;
		this.count = Long.parseLong(map.get("count"));
		this.id = map.get("id");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public String toString() {
		return new StringBuffer().append(this.getClass().getName()).append(":map=").append(this.map).append(",id=").append(this.id).append(",count=").append(this.count).toString();
	}
}
