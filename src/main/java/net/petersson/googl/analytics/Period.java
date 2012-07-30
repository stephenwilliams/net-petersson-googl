package net.petersson.googl.analytics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class Period {

	private static Logger logger = Logger.getLogger(Period.class);

	private long shortUrlClicks;
	private long longUrlClicks;
	private List<IdCountPair> referrers;
	private List<IdCountPair> countries;
	private List<IdCountPair> browsers;
	private List<IdCountPair> platforms;

	public Period(Map<String, Object> allTime) {

		this.shortUrlClicks = Long.parseLong((String) allTime.get("shortUrlClicks"));
		this.longUrlClicks = Long.parseLong((String) allTime.get("longUrlClicks"));

		this.referrers = getIdCountPairs("referrers", allTime);
		this.countries = getIdCountPairs("countries", allTime);
		this.browsers = getIdCountPairs("browsers", allTime);
		this.platforms = getIdCountPairs("platforms", allTime);
	}

	/**
	 * 
	 * @param arrayName
	 * @param allTime
	 * @return
	 */
	private List<IdCountPair> getIdCountPairs(String arrayName, Map<String, Object> allTime) {

		@SuppressWarnings("unchecked")
		List<Map<String, String>> raw = (List<Map<String, String>>) allTime.get(arrayName);
		List<IdCountPair> idCountPairs = new ArrayList<IdCountPair>();
		for (Map<String, String> map : raw) {
			idCountPairs.add(new IdCountPair(map));
		}
		return idCountPairs;

	}

	public long getShortUrlClicks() {
		return shortUrlClicks;
	}

	public long getLongUrlClicks() {
		return longUrlClicks;
	}

	public List<IdCountPair> getReferrers() {
		return this.referrers;
	}

	public List<IdCountPair> getCountries() {
		return this.countries;
	}

	public List<IdCountPair> getBrowsers() {
		return this.browsers;
	}

	public List<IdCountPair> getPlatforms() {
		return this.platforms;
	}

}