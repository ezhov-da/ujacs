package ru.ezhov.udater;

import java.io.File;
import java.util.logging.Logger;
import ru.ezhov.pathtofile.AbsolutePath;
import ru.ezhov.ujatools.PropertiesConst;
import ru.ezhov.ujatools.PropertyHttpHolder;

/**
 * Проверяем наличие папки с приложением "Сервис приложений"
 * <p>
 * @author ezhov_da
 */
public class CheckFolder
{
    private static final Logger LOG = Logger.getLogger(CheckFolder.class.getName());

    public boolean isFolderAppCreate() throws Exception
    {
        PropertyHttpHolder propertyHttpHolder = PropertyHttpHolder.getInstance();
        String folder = propertyHttpHolder.getProperty(PropertiesConst.NAME_FOLDER_APP_SERVICE_REAL);
        String pasthToFolderApp = AbsolutePath.getAbsolutePath(CheckFolder.class) + File.separator + folder;
        return new File(pasthToFolderApp).exists();
    }
}
