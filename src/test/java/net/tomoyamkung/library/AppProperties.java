package net.tomoyamkung.library;

import java.io.IOException;
import java.util.Properties;

public class AppProperties {

	private static final String APP_PROPERTIES = "app.properties";

	private static AppProperties instance = new AppProperties();

	private Properties prop;

	private AppProperties() {
		prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader()
					.getResourceAsStream(APP_PROPERTIES));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static synchronized AppProperties getInstance() {
		return instance;
	}

	public String get(String key) {
		String value = prop.getProperty(key);
		if (value == null) {
			throw new IllegalArgumentException("value is not found.");
		}

		return value.trim();
	}

}
