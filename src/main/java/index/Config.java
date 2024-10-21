package index;

import java.io.IOException;
import java.util.Properties;

public final class Config {
    private final Properties props = new Properties();

    {
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