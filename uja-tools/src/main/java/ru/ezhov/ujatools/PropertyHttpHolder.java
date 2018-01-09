package ru.ezhov.ujatools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Класс, который получает настройки с web сервера
 * <p>
 * @author ezhov_da
 */
public class PropertyHttpHolder
{
    private static final Logger LOG = Logger.getLogger(PropertyHttpHolder.class.getName());
    private static PropertyHttpHolder propertyHttpHolder;
    private Properties properties;

    private PropertyHttpHolder()
    {
        properties = new Properties();
    }

    public static synchronized PropertyHttpHolder getInstance()
    {
        if (propertyHttpHolder == null)
        {
            propertyHttpHolder = new PropertyHttpHolder();
        }
        return propertyHttpHolder;
    }

    public synchronized void load(String http) throws IOException
    {
        URL url = new URL(http);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        properties = new Properties();
        properties.load(new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8")));
        httpURLConnection.disconnect();
    }

    public synchronized String getProperty(String key)
    {
        return properties.getProperty(key);
    }
}
