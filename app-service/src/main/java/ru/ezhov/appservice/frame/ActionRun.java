package ru.ezhov.appservice.frame;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import ru.ezhov.appservice.IconLoader;
import ru.ezhov.appservice.managers.actionmanagers.AppRunManager;
import ru.ezhov.appservice.ListenerDestroyApp;
import ru.ezhov.appservice.RunApp;
import ru.ezhov.ujatools.PropertiesConst;
import ru.ezhov.ujatools.PropertyHttpHolder;

/**
 * Запускаем приложение
 * <p>
 * @author ezhov_da
 */
public class ActionRun extends ActionApp
{
    {
        putValue(NAME, "Запустить");
    }

    private static final Logger LOG = Logger.getLogger(ActionRun.class.getName());

    public ActionRun(RunApp runApp)
    {
        super(runApp);
        String prop = PropertyHttpHolder.getInstance().getProperty(PropertiesConst.ICON_RUN);
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
    public void actionPerformed(final ActionEvent e)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                AppRunManager appRunManager = new AppRunManager(runApp);
                runApp.addListenerDestroyApp(new ListenerDestroy());
                appRunManager.run();
                JButton jButton = (JButton) e.getSource();
                jButton.setAction(new ActionStop(runApp));
                BasicPanel.getInstance().load();
            }
        });
    }


    private class ListenerDestroy implements ListenerDestroyApp
    {

        @Override
        public void destroyApp(RunApp runApp)
        {
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    BasicPanel.getInstance().load();
                }
            });
        }

    }

}
