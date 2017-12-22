package ru.ezhov.appservice.frame;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.Icon;
import javax.swing.SwingUtilities;
import ru.ezhov.appservice.IconLoader;
import ru.ezhov.appservice.managers.actionmanagers.AppInstallManager;
import ru.ezhov.ujatools.exception.DeleteFileException;
import ru.ezhov.appservice.RunApp;
import ru.ezhov.ujatools.PropertiesConst;
import ru.ezhov.ujatools.PropertyHttpHolder;

/**
 *
 * @author ezhov_da
 */
public class ActionInstall extends ActionApp
{
    {
        putValue(NAME, "Установить");
    }
    private static final Logger LOG = Logger.getLogger(ActionInstall.class.getName());

    public ActionInstall(RunApp runApp)
    {
        super(runApp);
        String prop = PropertyHttpHolder.getInstance().getProperty(PropertiesConst.ICON_INSTALL);
        try
        {
            Icon icon = IconLoader.getIcon(prop);
            putValue(SMALL_ICON, icon);
        } catch (IOException ex)
        {
            Logger.getLogger(ActionRun.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                AppInstallManager appInstallManager = new AppInstallManager(runApp);
                try
                {
                    appInstallManager.install();
                    BasicPanel.getInstance().load();
                } catch (IOException ex)
                {
                    Logger.getLogger(ActionInstall.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DeleteFileException ex)
                {
                    Logger.getLogger(ActionInstall.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
