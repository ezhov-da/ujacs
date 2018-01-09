package ru.ezhov.ujatools;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс, который инициализирует {@link PropertyHttpHolder}
 * <p>
 * @author ezhov_da
 */
public class LoadHttpProperties
{
    private static final Logger LOG = Logger.getLogger(LoadHttpProperties.class.getName());

    public void load() throws IOException
    {
        PropertiesHolder propertiesHolder = new PropertiesHolder("/installer.properties");
        try
        {
            propertiesHolder.loadPropertyClass(LoadHttpProperties.class);
            String httpPtoperties = propertiesHolder.getProperty(PropertiesConst.INSTALLER_FILE_APP_PROPERTY_HTTP);
            PropertyHttpHolder propertyHttpHolder = PropertyHttpHolder.getInstance();
            try
            {
                propertyHttpHolder.load(httpPtoperties);
            } catch (IOException ex)
            {
                Logger.getLogger(LoadHttpProperties.class.getName()).log(Level.SEVERE, null, ex);
                throw new IOException("Не удалось загрузить файл настроек с сервера.", ex);
            }
        } catch (IOException ex)
        {
            Logger.getLogger(LoadHttpProperties.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException("Не удалось найти файл настроек для первоначального запуска.", ex);
        }
    }



}
