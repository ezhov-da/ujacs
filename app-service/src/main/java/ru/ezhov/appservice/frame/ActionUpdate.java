package ru.ezhov.appservice.frame;

import java.awt.event.ActionEvent;
import java.util.logging.Logger;
import ru.ezhov.appservice.managers.actionmanagers.AppUpdateManager;
import ru.ezhov.appservice.RunApp;

/**
 *
 * @author ezhov_da
 */
public class ActionUpdate extends ActionApp
{
    {
        putValue(NAME, "Обновить");
    }
    private static final Logger LOG = Logger.getLogger(ActionUpdate.class.getName());

    public ActionUpdate(RunApp runApp)
    {
        super(runApp);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        AppUpdateManager appUpdateManager = new AppUpdateManager(runApp);
        appUpdateManager.update();
    }
}
