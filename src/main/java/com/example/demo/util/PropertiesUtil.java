package com.example.demo.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtil {
	
	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

	private static Properties properties;
	
	static {
		try {
		properties = new Properties();
		// 使用InPutStream流读取properties文件
		InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties");
		properties.load(in);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
}
