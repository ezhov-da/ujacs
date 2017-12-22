package ru.ezhov.udater;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.ezhov.pathtofile.AbsolutePath;
import ru.ezhov.ujatools.FilesUtil;
import ru.ezhov.ujatools.PropertiesConst;
import ru.ezhov.ujatools.PropertyHttpHolder;

/**
 *
 * @author ezhov_da
 */
public class ClearFileAppService
{
    private static final Logger LOG = Logger.getLogger(ClearFileAppService.class.getName());

    public void deleteFilesAppService() throws Exception
    {
        PropertyHttpHolder propertyHttpHolder = PropertyHttpHolder.getInstance();
        String pathToFolder = AbsolutePath.getAbsolutePath(ClearFileAppService.class);
        LOG.log(Level.INFO, "Путь к папке приложения (общей): {0}", pathToFolder);
        String pathToFolderApp = pathToFolder + File.separator + propertyHttpHolder.getProperty(PropertiesConst.NAME_FOLDER_APP_SERVICE_REAL);
        LOG.log(Level.INFO, "Путь к папке приложения: {0}", pathToFolderApp);
        String pathToFile = pathToFolder + File.separator + propertyHttpHolder.getProperty(PropertiesConst.FILE_APP_VERSION);
        LOG.log(Level.INFO, "Путь к файлу настроек: {0}", pathToFile);
        FilesUtil filesUtil = new FilesUtil();
        LOG.log(Level.INFO, "Начинаем удаление");
        filesUtil.delete(new File(pathToFolderApp));
        LOG.log(Level.INFO, "Удаляем папку с приложением");
        filesUtil.delete(new File(pathToFile));
        LOG.log(Level.INFO, "Удаляем конфигурационный файл");
    }
}
