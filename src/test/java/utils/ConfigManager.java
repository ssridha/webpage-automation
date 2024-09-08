package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

    private static Properties properties = new Properties();

    // Method to load property file based on the environment
    public static void loadProperties(String env) {
        String filePath = String.format("src/test/resources/config/%s.properties", env);

        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load config file: " + filePath);
        }
    }

    // Method to get property value
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public static String getDriverPath(String browser) {
    	ConfigManager.loadProperties("driver");
        String relativePath = properties.getProperty(browser);
        // Convert relative path to absolute path
        String browserpath= new File(ConfigManager.class.getClassLoader().getResource(relativePath).getFile()).getPath();
        System.out.println(browserpath);
        return browserpath;
    }
}
