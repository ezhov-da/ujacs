package ru.ezhov.updater;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.ezhov.ujatools.AbsolutePath;
import ru.ezhov.ujatools.PropertiesConst;
import ru.ezhov.ujatools.PropertiesHolder;
import ru.ezhov.ujatools.PropertyHttpHolder;

/**
 *
 * @author ezhov_da
 */
public class CheckVersion
{
    private static final Logger LOG = Logger.getLogger(CheckVersion.class.getName());

    public boolean versionEquals() throws Exception
    {
        PropertyHttpHolder propertyHttpHolder = PropertyHttpHolder.getInstance();
        String version = propertyHttpHolder.getProperty(PropertiesConst.APP_SERVICE_VERSION);
        LOG.log(Level.INFO, "Версия сервиса приложений с сервера: {0}", version);
        String versionFromFile;
        try
        {
            String path =
                    AbsolutePath.getAbsolutePath(CheckVersion.class)
                    + File.separator
                    + propertyHttpHolder.getProperty(PropertiesConst.FILE_APP_VERSION);
            PropertiesHolder propertiesHolder = new PropertiesHolder(path);
            propertiesHolder.loadProperty();
            versionFromFile = propertiesHolder.getProperty(PropertiesConst.APP_VERSION);
        } catch (NullPointerException ex)
        {//если файл с версией не нашелся
            LOG.log(Level.INFO, "Файл с версией не найден");
            return false;
        }
        LOG.log(Level.INFO, "Версия сервиса приложений из файла: {0}", versionFromFile);
        return versionFromFile.equals(version);
    }
}
