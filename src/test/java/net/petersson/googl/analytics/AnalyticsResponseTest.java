package net.petersson.googl.analytics;

import com.google.gson.GsonBuilder;
import net.petersson.googl.GooGlException;
import net.petersson.googl.GooGlTest;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import static net.petersson.googl.TestConstants.LONG_URL;
import static net.petersson.googl.TestConstants.SHORT_URL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AnalyticsResponseTest {

	private static Logger logger = Logger.getLogger(GooGlTest.class.getName());

	@Test
	public void test_analytics() throws IOException, GooGlException {

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());

		String json = IOUtils.toString(getClass().getResourceAsStream("/googl_analytics.json"));

		AnalyticsResponse response = (AnalyticsResponse) gsonBuilder.create().fromJson(json, AnalyticsResponse.class);

		assertEquals("urlshortener#url", response.getKind());
		assertEquals(SHORT_URL, response.getId());
		assertEquals(LONG_URL, response.getLongUrl());
		assertNotNull(response.getCreated());

		assertNotNull(response.getAnalytics());

		// BEGIN: allTime
		assertNotNull(response.getAnalytics().getAllTime());
		assertEquals(17313L, response.getAnalytics().getAllTime().getShortUrlClicks());
		assertEquals(374703L, response.getAnalytics().getAllTime().getLongUrlClicks());

		assertNotNull(response.getAnalytics().getAllTime().getReferrers());

		assertEquals("Unknown/empty", response.getAnalytics().getAllTime().getReferrers().get(0).getId());
		assertEquals(13419L, response.getAnalytics().getAllTime().getReferrers().get(0).getCount());

		assertEquals("US", response.getAnalytics().getAllTime().getCountries().get(0).getId());
		assertEquals(4899L, response.getAnalytics().getAllTime().getCountries().get(0).getCount());

		assertEquals("Chrome", response.getAnalytics().getAllTime().getBrowsers().get(0).getId());
		assertEquals(4823L, response.getAnalytics().getAllTime().getBrowsers().get(0).getCount());

		assertEquals("Windows", response.getAnalytics().getAllTime().getPlatforms().get(0).getId());
		assertEquals(12329L, response.getAnalytics().getAllTime().getPlatforms().get(0).getCount());

		// BEGIN: month
		assertNotNull(response.getAnalytics().getMonth());
		assertEquals(1397L, response.getAnalytics().getMonth().getShortUrlClicks());
		assertEquals(2332L, response.getAnalytics().getMonth().getLongUrlClicks());

		assertEquals("Unknown/empty", response.getAnalytics().getMonth().getReferrers().get(0).getId());
		assertEquals(963L, response.getAnalytics().getMonth().getReferrers().get(0).getCount());

		// BEGIN: week
		assertNotNull(response.getAnalytics().getWeek());
		assertEquals(365L, response.getAnalytics().getWeek().getShortUrlClicks());
		assertEquals(663L, response.getAnalytics().getWeek().getLongUrlClicks());

		assertEquals("Unknown/empty", response.getAnalytics().getWeek().getReferrers().get(0).getId());
		assertEquals(216L, response.getAnalytics().getWeek().getReferrers().get(0).getCount());

		// BEGIN: day
		assertNotNull(response.getAnalytics().getDay());
		assertEquals(34L, response.getAnalytics().getDay().getShortUrlClicks());
		assertEquals(137L, response.getAnalytics().getDay().getLongUrlClicks());

		assertEquals("Unknown/empty", response.getAnalytics().getDay().getReferrers().get(0).getId());
		assertEquals(29L, response.getAnalytics().getDay().getReferrers().get(0).getCount());

		// BEGIN: twoHours
		assertNotNull(response.getAnalytics().getTwoHours());
		assertEquals(5L, response.getAnalytics().getTwoHours().getShortUrlClicks());
		assertEquals(39L, response.getAnalytics().getTwoHours().getLongUrlClicks());

		assertEquals("Unknown/empty", response.getAnalytics().getTwoHours().getReferrers().get(0).getId());
		assertEquals(2L, response.getAnalytics().getTwoHours().getReferrers().get(0).getCount());
	}
}
