package com.hklk.oplatform.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 获取配置文件工具类
 */
public class PropUtil {
    private static Properties properties = new Properties();

    private PropUtil() {
    }
    
	public static void initFile(String fileName) {

		try {
			properties = PropertiesLoaderUtils.loadAllProperties(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties properties) {
        PropUtil.properties = properties;
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        return value;
    }
}
