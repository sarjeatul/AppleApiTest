package applestorecheckapi.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import helper.HttpApiCaller;
import helper.ResponseParser;

public class AppleStoreStatusApiTest {
	public static final String url = "https://istheapplestoredown.com/api/v1/status/worldwide";

	private static int successStatusCode = 200;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
    
	
	@Test(description = "Verify that apple stores are Up i.e. No country has status y")
	public void countryStatusTest() {
		HttpResponse istheapplestoredownResponse = null;
		JSONObject jsonObject = null;
		HttpApiCaller httpGet = new HttpApiCaller();
		ResponseParser parseResponseGet = new ResponseParser();
		try {			
			istheapplestoredownResponse = httpGet.getAppleStoreApiResponse(url);
			if (istheapplestoredownResponse.getStatusLine().getStatusCode() == successStatusCode) {
				jsonObject = parseResponseGet.parseResponse(istheapplestoredownResponse);
				checkIfStatusIsY(istheapplestoredownResponse, jsonObject);
			}

		} catch (Exception e) {
			logger.error("Exception occured in AppTest ::countryStatusTest : " + e.getMessage());
			logger.debug("Exception occured in AppTest ::countryStatusTest, Full stack trace : " + e);
		}
	}

	/**
	 * 
	 * @param response
	 * @param jsonObject
	 * @throws IOException
	 * @throws ParseException
	 */
	public void checkIfStatusIsY(HttpResponse response, JSONObject jsonObject) throws IOException, ParseException {
		Iterator iterator = null;
		ArrayList<String> countriesWithStatusY = new ArrayList<String>();
		if (jsonObject != null) {
			iterator = jsonObject.values().iterator();
			while (iterator.hasNext()) {
				JSONObject jsonObjectTemp = (JSONObject) iterator.next();
				String status = jsonObjectTemp.get("status").toString();
				String nameOfCountry = jsonObjectTemp.get("name").toString();
				if (status.equals("y")) {
					countriesWithStatusY.add(nameOfCountry);
				}
			}
			if (countriesWithStatusY.size() > 0) {
				Assert.fail("Country with status y" + countriesWithStatusY);
				countriesWithStatusY.stream().forEach(System.out::println);
			} else {
				logger.info("All Apple stores are up");
			}
		}
	}

	
}
