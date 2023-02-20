package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Config {

    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
    private static final Properties prop = new Properties();

    static {
        InputStream is = Config.class.getClassLoader().getResourceAsStream("config.properties");
        if (is != null) {
            try {
                prop.load(is);
                is.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            throw new AssertionError("Couldn't get environment properties from file \"config.properties\"");
        }
    }

    /**
     * This method gets value of "url" from resources and returns it as a string
     *
     * @return value of url
     */
    public static String getBaseUrl() {
        return prop.getProperty("url");
    }

    /**
     * This method gets value of "private_token" from resources and returns it as a string
     *
     * @return value of private_token
     */
    public static String getPrivateToken() {
        return prop.getProperty("private_token");
    }

    /**
     * This method gets value of "private_token_value" from resources and returns it as a string
     *
     * @return value of private_token_value
     */
    public static String getPrivateTokenValue() {
        return prop.getProperty("private_token_value");
    }

    /**
     * This method gets value of "content_type" from resources and returns it as a string
     *
     * @return value of content_type
     */
    public static String getContentType() {
        return prop.getProperty("content_type");
    }

    /**
     * This method gets value of "content_type_value" from resources and returns it as a string
     *
     * @return value of content_type_value
     */
    public static String getContentTypeValue() {
        return prop.getProperty("content_type_value");
    }
}
