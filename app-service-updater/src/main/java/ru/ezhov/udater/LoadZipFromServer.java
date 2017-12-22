package ru.ezhov.udater;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.ezhov.pathtofile.AbsolutePath;
import ru.ezhov.ujatools.HttpFileLoader;
import ru.ezhov.ujatools.PropertiesConst;
import ru.ezhov.ujatools.PropertyHttpHolder;

/**
 *
 * @author ezhov_da
 */
public class LoadZipFromServer
{
    private static final Logger LOG = Logger.getLogger(LoadZipFromServer.class.getName());

    public String load() throws Exception
    {
        PropertyHttpHolder propertyHttpHolder = PropertyHttpHolder.getInstance();
        String pathToSave = AbsolutePath.getAbsolutePath(LoadZipFromServer.class) + File.separator + propertyHttpHolder.getProperty(PropertiesConst.NAME_OUTPUT_ARCHIVE_APP_SERVICE_INSTALL);
        LOG.log(Level.INFO, "Файл для сохранения архива: {0}", pathToSave);
        String versionAppService = propertyHttpHolder.getProperty(PropertiesConst.APP_SERVICE_VERSION);
        String fullURLToArchive = propertyHttpHolder.getProperty(PropertiesConst.HTTP_INSTALL_APP_SERVICE_LOADER_REAL) + "_" + versionAppService + ".zip";
        HttpFileLoader httpFileLoader =
                new HttpFileLoader(fullURLToArchive, pathToSave);
        httpFileLoader.load();
        LOG.log(Level.INFO, "Архив загружен");
        return pathToSave;
    }
}
