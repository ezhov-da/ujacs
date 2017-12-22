package ru.ezhov.appservice;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import ru.ezhov.appservice.frame.BasicPanel;
import ru.ezhov.ujatools.PropertiesConst;
import ru.ezhov.ujatools.PropertyHttpHolder;

/**
 * Класс, который запускает задачу на обновление
 * <p>
 * @author ezhov_da
 */
public class AppUpdater
{
    private static final Logger LOG = Logger.getLogger(AppUpdater.class.getName());

    public void startTaskCheckUpdate()
    {
        TimerTask timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        BasicPanel.getInstance().load();
                        LOG.log(Level.INFO, "Проверка обновления");
                    }
                });
            }
        };
        Timer task = new Timer();
        long l = Long.valueOf(PropertyHttpHolder.getInstance().getProperty(PropertiesConst.HOW_OFTEN_CHECK_UPDATE));
        LOG.log(Level.INFO, "Период обновления: {0}", String.valueOf(l));
        task.schedule(
                timerTask,
                new Date(),
                l);
    }

}
