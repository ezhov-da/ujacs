package ru.ezhov.server;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Получение настроек сервера
 * <p>
 *
 * @author ezhov_da
 */
public class PropertiesHolder {
    private static final Logger LOG = Logger.getLogger(PropertiesHolder.class.getName());
    private static PropertiesHolder propertiesHolder;
    private Properties properties;


    private PropertiesHolder() throws IOException {
        properties = new Properties();
        properties.load(new FileReader("./server/config.properties"));
    }

    public static PropertiesHolder getInstance() throws IOException {
        if (propertiesHolder == null) {
            propertiesHolder = new PropertiesHolder();
        }
        return propertiesHolder;
    }

    public String getProperties(String key) {
        return properties.getProperty(key);
    }
}
