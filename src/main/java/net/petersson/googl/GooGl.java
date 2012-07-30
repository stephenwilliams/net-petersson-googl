package net.petersson.googl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import net.petersson.googl.analytics.AnalyticsResponse;
import net.petersson.googl.analytics.DateDeserializer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.GsonBuilder;

public class GooGl {

	private static final String BASE_URL = "https://www.googleapis.com/urlshortener/v1/url";

	private static Logger logger = Logger.getLogger(GooGl.class);

	private String apiKey;

	/**
	 * 
	 * @param apiKey
	 */
	public GooGl(String apiKey) {
		if (StringUtils.isEmpty(apiKey)) {
			throw new IllegalArgumentException("APIKey must be specified, see the Google URL Shortener API docs: http://goo.gl/2rfGn");
		}
		this.apiKey = apiKey;
	}

	/**
	 * 
	 * @param strLongURL
	 * @return
	 * @throws IOException
	 * @throws GooGlException
	 */
	protected URL shorten(URL longURL) throws IOException, GooGlException {

		String postData = "{\"longUrl\": \"" + longURL.toExternalForm() + "\"}";
		logger.debug("shorten() postData=" + postData);

		final String strGooGlUrl = BASE_URL + "?key=" + this.apiKey;

		URL gooGlURL = new URL(strGooGlUrl);
		logger.debug("shorten() gooGlURL=" + gooGlURL);

		HttpURLConnection httpURLConnection = (HttpURLConnection) gooGlURL.openConnection();
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.addRequestProperty("Content-Type", "application/json");
		httpURLConnection.setRequestProperty("Content-Length", "" + Integer.toString(postData.getBytes().length));
		httpURLConnection.setUseCaches(false);
		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true);
		httpURLConnection.connect();

		// Send request
		DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
		wr.writeBytes(postData);
		wr.flush();
		wr.close();

		// Get response
		GooGlResponse response = new GooGlResponse(getResponse(httpURLConnection));

		logger.debug("shortUrl=" + response.getShortUrl());

		return new URL(response.getShortUrl());
	}

	/**
	 * throws IOException, GooGlException
	 * 
	 * @param shortUrl
	 * @return
	 * @throws IOException
	 * @throws GooGlException
	 */
	public URL expand(URL shortUrl) throws IOException, GooGlException {

		String strGooGlUrl = BASE_URL + "?shortUrl=" + shortUrl.toExternalForm();
		URL gooGlURL = new URL(strGooGlUrl);

		HttpURLConnection httpURLConnection = (HttpURLConnection) gooGlURL.openConnection();
		httpURLConnection.setUseCaches(false);
		httpURLConnection.connect();

		GooGlResponse response = new GooGlResponse(getResponse(httpURLConnection));

		httpURLConnection.disconnect();

		return new URL(response.getLongUrl());
	}

	/**
	 * 
	 * @param shortUrl
	 * @return
	 * @throws IOException
	 * @throws GooGlException
	 */
	public AnalyticsResponse getAnalytics(String shortUrl) throws IOException, GooGlException {

		String strGooGlUrl = BASE_URL + "?shortUrl=" + shortUrl + "&projection=FULL";

		URL gooGlURL = new URL(strGooGlUrl);

		HttpURLConnection httpURLConnection = (HttpURLConnection) gooGlURL.openConnection();
		httpURLConnection.setUseCaches(false);
		httpURLConnection.connect();
		String json = getResponse(httpURLConnection);
		httpURLConnection.disconnect();

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());

		return (AnalyticsResponse) gsonBuilder.create().fromJson(json, AnalyticsResponse.class);

	}

	/**
	 * 
	 * @param httpURLConnection
	 * @return
	 * @throws IOException
	 * @throws GooGlException
	 */
	private String getResponse(HttpURLConnection httpURLConnection) throws IOException, GooGlException {

		boolean isGotErrorResponse = false;
		InputStream is = null;
		if (httpURLConnection.getResponseCode() == 200) {
			is = httpURLConnection.getInputStream();
		} else {
			isGotErrorResponse = true;
			is = httpURLConnection.getErrorStream();
		}
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuffer response = new StringBuffer();
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\n');
		}
		rd.close();

		logger.debug("responseCode=" + httpURLConnection.getResponseCode());
		logger.debug("response=" + response);
		httpURLConnection.disconnect();

		if (isGotErrorResponse) {
			throw new GooGlException(response);
		}
		return response.toString();
	}

}
