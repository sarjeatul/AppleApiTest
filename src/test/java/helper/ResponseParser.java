package helper;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ResponseParser {

	/**
	 * 
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public JSONObject parseResponse(HttpResponse response) throws ParseException, IOException {
		JSONParser parser = new JSONParser();
		HttpEntity entity = response.getEntity();
		String responseString = EntityUtils.toString(entity, "UTF-8");
		JSONObject jsonObject = (JSONObject) parser.parse(responseString);
		return jsonObject;
	}

}
