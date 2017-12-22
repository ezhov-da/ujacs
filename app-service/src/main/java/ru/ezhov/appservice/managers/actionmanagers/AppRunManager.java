package ru.ezhov.appservice.managers.actionmanagers;

import java.io.IOException;
import java.util.logging.Logger;
import ru.ezhov.ujatools.PathToApps;
import ru.ezhov.appservice.RunApp;
import ru.ezhov.ujatools.JOptionPaneError;
import ru.ezhov.ujatools.PropertiesConst;
import ru.ezhov.ujatools.PropertyHttpHolder;

/**
 *
 * @author ezhov_da
 */
public class AppRunManager
{
    private static final Logger LOG = Logger.getLogger(AppInstallManager.class.getName());
    private RunApp runApp;

    public AppRunManager(RunApp runApp)
    {
        this.runApp = runApp;
    }

    public void run()
    {
        try
        {
            String pathToFolderJava = PathToApps.getFolderJava();
            String pathToApp = PathToApps.createFolderAndReturnPath(runApp.getApplicationInstance().getNameAppSystem());
            String command = runApp.getApplicationInstance().getCommandRunApp();
            String constReplaceJava = PropertyHttpHolder.getInstance().getProperty(PropertiesConst.CONST_PATH_TO_JAVA);
            String constReplacePathApp = PropertyHttpHolder.getInstance().getProperty(PropertiesConst.CONST_PATH_TO_APP);
            String commandRun = command.replace(constReplaceJava, pathToFolderJava).replace(constReplacePathApp, pathToApp);
            runApp.runProcess(commandRun);
        } catch (IOException ex)
        {
            JOptionPaneError.showErrorMsg("Ошибка запуска приложения", ex);
        }
    }
}
