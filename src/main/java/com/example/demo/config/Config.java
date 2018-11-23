package com.example.demo.config;

import com.example.demo.util.PropertiesUtil;

public class Config {

	
	public static final String ZK_HOST = PropertiesUtil.getProperty("zookeeper.addr");
	
	public static final String ROOT = "/server/";

}
