package model.config.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class MyConfig {

    protected String propertyFile;
    protected Properties properties;

    public void saveProperties() throws IOException {
        //properties.store(new FileOutputStream(propertyFile), null);
    }

    public void loadProperties() throws IOException {
        /*ResourceBundle resourceBundle = ResourceBundle.getBundle(propertyFile);
        properties = new Properties();

        Enumeration<String> keys = resourceBundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            properties.put(key, resourceBundle.getString(key));
        }*/

        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(propertyFile);
        properties = new Properties();
        properties.load(inputStream);
    }
}
