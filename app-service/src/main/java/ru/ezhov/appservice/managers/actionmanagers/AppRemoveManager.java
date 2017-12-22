package ru.ezhov.appservice.managers.actionmanagers;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.ezhov.ujatools.FilesUtil;
import ru.ezhov.ujatools.exception.DeleteFileException;
import ru.ezhov.ujatools.PathToApps;
import ru.ezhov.appservice.RunApp;

/**
 * Класс отвечающий за удаление приложения
 * <p>
 * @author ezhov_da
 */
public class AppRemoveManager
{
    private static final Logger LOG = Logger.getLogger(AppInstallManager.class.getName());
    private RunApp runApp;

    public AppRemoveManager(RunApp runApp)
    {
        this.runApp = runApp;
    }

    //удялем приложение
    public void removeApp() throws IOException, DeleteFileException
    {
        if (runApp.isRunning())
        {
            try
            {
                runApp.destroyProcess();
            } catch (InterruptedException ex)
            {
                Logger.getLogger(AppRemoveManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String pathToAppRemove =
                PathToApps.pathToApps(runApp.getApplicationInstance().getNameAppSystem());
        File file = new File(pathToAppRemove);
        FilesUtil filesUtil = new FilesUtil();
        filesUtil.delete(file);
    }

}
