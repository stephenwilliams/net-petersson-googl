package net.petersson.googl;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class GooGlResponse {

	private static Logger logger = Logger.getLogger(GooGlResponse.class);

	private JSONObject jsonObject;

	/**
	 * 
	 * @param response
	 * @throws GooGlException
	 */
	public GooGlResponse(String response) throws GooGlException {

		try {
			this.jsonObject = new JSONObject(response);
		} catch (JSONException e) {
			logger.error("GooGlResponse(String) JSONException", e);
			throw new GooGlException("failed to parse JSON response: " + response);
		}

	}

	public String getLongUrl() throws GooGlException {

		try {
			return this.jsonObject.getString("longUrl");
		} catch (JSONException e) {
			logger.error("GooGlResponse(String) JSONException", e);
			throw new GooGlException("failed to parse \"longUrl\" from JSON");
		}

	}

	public String getShortUrl() throws GooGlException {
		try {
			return this.jsonObject.getString("id");
		} catch (JSONException e) {
			logger.error("GooGlResponse(String) JSONException", e);
			throw new GooGlException("failed to parse \"id\" (shortUrl) from JSON");
		}

	}
}
