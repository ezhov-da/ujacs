package ru.ezhov.appservice.managers.actionmanagers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.ezhov.ujatools.exception.DeleteFileException;
import ru.ezhov.appservice.RunApp;

/**
 *
 * @author ezhov_da
 */
public class AppUpdateManager
{
    private static final Logger LOG = Logger.getLogger(AppUpdateManager.class.getName());
    private RunApp runApp;

    public AppUpdateManager(RunApp runApp)
    {
        this.runApp = runApp;
    }

    public void update()
    {
        AppInstallManager appInstallManager = new AppInstallManager(runApp);
        try
        {
            appInstallManager.install();
        } catch (IOException ex)
        {
            Logger.getLogger(AppUpdateManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DeleteFileException ex)
        {
            Logger.getLogger(AppUpdateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
