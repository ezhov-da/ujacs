package ru.ezhov.appservice.managers.actionmanagers;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import ru.ezhov.ujatools.HttpFileLoader;
import ru.ezhov.ujatools.UnzipFile;
import ru.ezhov.ujatools.exception.DeleteFileException;
import ru.ezhov.ujatools.xmlobjects.ApplicationInstance;
import ru.ezhov.ujatools.PathToApps;
import ru.ezhov.appservice.RunApp;

/**
 * Класс, который отвечает за установку приложения
 * <p>
 * @author ezhov_da
 */
public class AppInstallManager
{
    private static final Logger LOG = Logger.getLogger(AppInstallManager.class.getName());
    private RunApp runApp;

    public AppInstallManager(RunApp runApp)
    {
        this.runApp = runApp;
    }

    public void install() throws IOException, DeleteFileException
    {
        if (!runApp.isRunning())
        {
            ApplicationInstance applicationInstance = runApp.getApplicationInstance();
            clearFolder();
            //получаем путь к папке приложения
            String path = getPathToFolderApp(applicationInstance);
            //создаем папку
            File file = createFolder(path);
            //скачиваем архив
            String fileArchive = loadArchive(applicationInstance, path);
            //распаковываем
            unzip(fileArchive, path);
        }
    }

    private void clearFolder() throws IOException, DeleteFileException
    {
        //чистим папку приложения
        AppRemoveManager appRemoveManager = new AppRemoveManager(runApp);
        appRemoveManager.removeApp();
    }

    private String getPathToFolderApp(ApplicationInstance applicationInstance)
            throws IOException
    {
        String path =
                PathToApps.createFolderAndReturnPath(applicationInstance.getNameAppSystem());
        return path;
    }

    private File createFolder(String path)
    {
        File file = new File(path);
        file.mkdirs();
        return file;
    }

    private String loadArchive(ApplicationInstance applicationInstance, String path)
            throws IOException
    {
        String url = applicationInstance.getHttpLoad() + applicationInstance.getFileZip();
        String fileArchive = path + File.separator + applicationInstance.getFileZip();
        HttpFileLoader httpFileLoader = new HttpFileLoader(url, fileArchive);
        httpFileLoader.load();
        return fileArchive;
    }

    private void unzip(String fileArchive, String path) throws IOException
    {
        UnzipFile unzipFile = new UnzipFile();
        File zip = new File(fileArchive);
        unzipFile.unzip(new File(path), zip);
        zip.delete();
    }
}
