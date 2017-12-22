package ru.ezhov.ujatools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Держатель настроек
 * <p>
 * @author ezhov_da
 */
public class PropertiesHolder
{
    private static final Logger LOG = Logger.getLogger(PropertiesHolder.class.getName());

    private String pathToFile;
    private Properties properties;

    public PropertiesHolder(String pathToFile)
    {
        this.pathToFile = pathToFile;
    }

    public void loadProperty() throws IOException
    {
        properties = new Properties();
        properties.load(new FileInputStream(new File(pathToFile)));
    }

    public void loadPropertyClass(Class clazz) throws IOException
    {
        properties = new Properties();
        properties.load(clazz.getResourceAsStream(pathToFile));
    }

    public String getProperty(String key)
    {
        return properties.getProperty(key);
    }
}
