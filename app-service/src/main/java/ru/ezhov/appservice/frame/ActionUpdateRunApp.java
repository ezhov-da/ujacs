package ru.ezhov.appservice.frame;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.ezhov.appservice.RunApp;
import ru.ezhov.appservice.managers.actionmanagers.AppInstallManager;
import ru.ezhov.appservice.managers.actionmanagers.AppRemoveManager;
import ru.ezhov.appservice.managers.actionmanagers.AppRunManager;
import ru.ezhov.ujatools.exception.DeleteFileException;

/**
 * Действие, которое обновляет запущенное приложение, то есть:
 * 1. Закрывает его
 * 2. Устанавливлает его
 * 3. Снова открывает
 * <p>
 * @author ezhov_da
 */
public class ActionUpdateRunApp extends ActionApp
{
    {
        putValue(NAME, "Обновить");
    }
    private static final Logger LOG = Logger.getLogger(ActionUpdateRunApp.class.getName());

    private WindowNotifyUpdate windowNotifyUpdateTest;

    public ActionUpdateRunApp(RunApp runApp)
    {
        super(runApp);
    }

    public ActionUpdateRunApp(RunApp runApp, WindowNotifyUpdate windowNotifyUpdateTest)
    {
        this(runApp);
        this.windowNotifyUpdateTest = windowNotifyUpdateTest;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            LOG.log(Level.INFO, "Пользователь захотел обновиться");
            AppRemoveManager appRemoveManager = new AppRemoveManager(runApp);
            appRemoveManager.removeApp();
            AppInstallManager appInstall = new AppInstallManager(runApp);
            appInstall.install();
            AppRunManager appRunManager = new AppRunManager(runApp);
            appRunManager.run();
            windowNotifyUpdateTest.setVisible(false);
        } catch (IOException ex)
        {
            Logger.getLogger(ActionUpdateRunApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DeleteFileException ex)
        {
            Logger.getLogger(ActionUpdateRunApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setRunApp(RunApp runApp)
    {
        this.runApp = runApp;
    }


}
