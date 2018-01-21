package ru.ezhov.updater;

import java.io.File;
import java.util.logging.Logger;

import ru.ezhov.ujatools.AbsolutePath;
import ru.ezhov.ujatools.PropertiesConst;
import ru.ezhov.ujatools.PropertyHttpHolder;

/**
 * Проверяем наличие папки с приложением "Сервис приложений"
 * <p>
 * @author ezhov_da
 */
public class AppFolder
{
    private static final Logger LOG = Logger.getLogger(AppFolder.class.getName());

    public File constructPathToAppFolder() throws Exception
    {
        PropertyHttpHolder propertyHttpHolder = PropertyHttpHolder.getInstance();
        String folder = propertyHttpHolder.getProperty(PropertiesConst.NAME_FOLDER_APP_SERVICE_REAL);
        String pathToFolderApp = AbsolutePath.getAbsolutePath(AppFolder.class) + File.separator + folder;
        return new File(pathToFolderApp);
    }
}
