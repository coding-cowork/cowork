package com.william.core.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class WeatherServiceImpl implements WeatherService {

	private Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

	private HttpURLConnection client;

	public WeatherServiceImpl() {

	}

	/**
	 * get current weather status
	 */
	@SuppressWarnings({ "finally" })
	@Override
	public String getWeatherStatus() {
		InputStream input = null;
		String weather = "";
		try {
			URL url = new URL(
					"http://weather.yahooapis.com/forecastrss?w=2306179&u=c");
			client = (HttpURLConnection) url.openConnection();
			client.setRequestMethod("GET");
			input = client.getInputStream();
			SAXReader reader = new SAXReader();
			Document doc = reader.read(input);
			Element condition = doc.getRootElement().element("channel")
					.element("item").element("condition");
			weather = convert(Integer
					.parseInt(condition.attributeValue("code")));
			logger.info("~~~>>" + weather);
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Weather error" + e);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Weather error" + e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Weather error" + e);
		} finally {
			client.disconnect();
			return weather;
		}
	}

	/**
	 * convert weather to chinese
	 */
	private String convert(int code) {
		String weather = "";
		switch (code) {
		// cloud day
		case 26:
		case 27:
		case 28:
		case 29:
		case 30:
		case 44:
			weather = "多雲";
			break;
		// rain day
		case 5:
		case 6:
		case 10:
		case 11:
		case 14:
		case 12:
		case 40:
		case 45:
		case 46:
		case 47:
			weather = "雨天";
			break;
		// sunny day
		case 32:
		case 36:
		case 31:
			weather = "晴天";
			break;
		default:
			weather = "晴天";
			break;
		}
		return weather;
	}
}
