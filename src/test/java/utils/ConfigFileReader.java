package utils;
/*
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigFileReader {
	public Properties properties;
	public final String filePath=System.getProperty("user.dir")+"/src/test/java/Resources/config.properties";
	
	public ConfigFileReader() {
		properties=new Properties();
		try {
			FileInputStream fis=new FileInputStream(filePath);
			properties.load(fis);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}	
	
	
	public String getBrowserName() {
		return properties.getProperty("browserName"); 
	}
	

	public String getBaseUrl() {
		return properties.getProperty("baseUrl"); 
	}
}
*/

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {
    private Properties properties;

    public ConfigFileReader() {
        properties = new Properties();
        String filePath = System.getProperty("user.dir") + "/src/test/resources/config.properties";
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("config.properties not found at " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage());
        }
    }

    public String getProperty(String key) {
        String property = properties.getProperty(key);
        if (property != null) {
            return property;
        } else {
            throw new RuntimeException("Property '" + key + "' not specified in the config.properties file.");
        }
    }
}
