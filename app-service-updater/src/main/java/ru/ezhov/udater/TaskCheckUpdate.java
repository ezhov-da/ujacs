package ru.ezhov.udater;

import ru.ezhov.udater.frame.WindowUpdate;
import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import ru.ezhov.ujatools.LoadHttpProperties;
import ru.ezhov.ujatools.PropertiesConst;
import ru.ezhov.ujatools.PropertyHttpHolder;

/**
 * Проверка версий
 * <p>
 * @author ezhov_da
 */
public class TaskCheckUpdate extends Thread
{
    private static final Logger LOG = Logger.getLogger(TaskCheckUpdate.class.getName());
    private boolean iSayAboutChangeVersion;
    private PropertyHttpHolder propertyHttpHolder;

    @Override
    public void run()
    {
        while (true)
        {
            CheckVersion checkVersion = new CheckVersion();
            try
            {
                loadSettings();
                propertyHttpHolder = PropertyHttpHolder.getInstance();
                if (!checkVersion.versionEquals() && !iSayAboutChangeVersion)
                {
                    LOG.log(Level.INFO, "Версия не сошлась");
                    showMessage();
                    LOG.log(Level.INFO, "Выходим из обновления");
                    return;
                }
            } catch (Exception ex)
            {
                LOG.log(Level.INFO, "Ошибка проверки версий", ex);
            }
            try
            {
                long time = Long.valueOf(propertyHttpHolder.getProperty(PropertiesConst.TIME_CHECK_UPDATE_APP));
                LOG.log(Level.INFO, "Период проверки обновлений: {0}", time);
                Thread.sleep(time);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(TaskCheckUpdate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }



    private void loadSettings() throws IOException
    {
        LOG.log(Level.INFO, "Проверка версии");
        LoadHttpProperties loadHttpProperties = new LoadHttpProperties();
        loadHttpProperties.load();
    }

    private void showMessage()
    {
        LOG.log(Level.INFO, "Показываем окно");
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                WindowUpdate.getInstance().setVisible(true);
            }
        });
    }
}
