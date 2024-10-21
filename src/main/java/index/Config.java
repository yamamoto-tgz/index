package index;

import java.io.IOException;
import java.util.Properties;

public final class Config {
    private static final Properties props = new Properties();

    static {
        try {
            var classLoader = Server.class.getClassLoader();
            var inputStream = classLoader.getResourceAsStream("index.properties");
            props.load(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return props.getProperty(key);
    }
}