package ru.ezhov.appservice.managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import ru.ezhov.appservice.enumapp.AppButtonAction;
import ru.ezhov.ujatools.PropertiesConst;
import ru.ezhov.ujatools.PropertyHttpHolder;
import ru.ezhov.ujatools.xmlobjects.ApplicationInstance;
import ru.ezhov.ujatools.PathToApps;

/**
 * Класс, который реализует логику для отображения кнопок для каждого приложения
 * <p>
 * @author ezhov_da
 */
public class ActionButtonManager
{
    private ApplicationInstance applicationInstance;
    private PropertyHttpHolder propertyHttpHolder;

    public ActionButtonManager(
            ApplicationInstance applicationInstance)
    {
        this.applicationInstance = applicationInstance;
        this.propertyHttpHolder = PropertyHttpHolder.getInstance();
    }

    public AppButtonAction checkApp() throws IOException
    {
        //1 проверяем наличие папки приложения и файла с версией если одного из этого нет, показываем установку
        if (!checkFolderApp() || !checkFileAppVersion())
        {
            return AppButtonAction.INSTALL;
        }
        //2 проверем версии и либо запускаем, либо обновляем
        if (equalsVersionApp())
        {
            return AppButtonAction.RUN;
        } else
        {
            return AppButtonAction.UPDATE;
        }
    }

    //проверяем наличие папки приложения
    private boolean checkFolderApp() throws IOException
    {
        String patentPathWithEnd = PathToApps.createFolderAndReturnPath(applicationInstance.getNameAppSystem());
        File file = new File(patentPathWithEnd);
        return file.exists();
    }

    //проверяем наличие файла с версией
    private boolean checkFileAppVersion() throws IOException
    {
        return fileAppVersion().exists();
    }

    //получаем файл с версией
    private File fileAppVersion() throws IOException
    {
        String patentPathWithEnd = PathToApps.createFolderAndReturnPath(applicationInstance.getNameAppSystem());
        return new File(patentPathWithEnd + File.separator + propertyHttpHolder.getProperty(PropertiesConst.FILE_APP_VERSION));
    }

    //проверяем версию приложениня
    private boolean equalsVersionApp() throws FileNotFoundException, IOException
    {
        File file = fileAppVersion();
        FileReader fileReader = new FileReader(file);
        Properties properties = new Properties();
        try
        {
            properties.load(fileReader);
        } finally
        {
            fileReader.close();
        }
        String appVersion = properties.getProperty(propertyHttpHolder.getProperty(PropertiesConst.APP_VERSION));
        return appVersion.equals(applicationInstance.getVersion());
    }

}
