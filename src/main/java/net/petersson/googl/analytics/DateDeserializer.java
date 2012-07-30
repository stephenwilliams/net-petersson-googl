package net.petersson.googl.analytics;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.petersson.googl.GooGl;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateDeserializer implements JsonDeserializer<java.util.Date> {

	private static Logger logger = Logger.getLogger(GooGl.class.getName());

	public Date deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

		if (jsonElement == null || StringUtils.isEmpty(jsonElement.getAsString())) {
			logger.log(Level.SEVERE, "json is empty");
			return null;
		}

		String json = jsonElement.getAsString();
		json = json.replaceAll("\\+0([0-9]){1}\\:00", "+0$100"); // remove : from timezone to conform to standard
		logger.finer("json=" + json);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
		try {
			return sdf.parse(json);
		} catch (ParseException e) {
			logger.log(Level.SEVERE, "failed to parse date");
			return null;
		}
		// 2009-12-13T07:22:55.000+00:00
	}
}
