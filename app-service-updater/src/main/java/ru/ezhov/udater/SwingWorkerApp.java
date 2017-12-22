package ru.ezhov.udater;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import ru.ezhov.udater.frame.WindowNotify;
import ru.ezhov.ujatools.JOptionPaneError;

/**
 *
 * @author ezhov_da
 */
public class SwingWorkerApp extends SwingWorker<Object, Object>
{
    private static final Logger LOG = Logger.getLogger(SwingWorkerApp.class.getName());

    private WindowNotify windowNotify;

    public SwingWorkerApp()
    {
        windowNotify = WindowNotify.getInstance();
        windowNotify.setVisible(true);
    }

    @Override
    protected Object doInBackground() throws Exception
    {
        try
        {
            LoaderAndRunner loaderAndRunner = new LoaderAndRunner();
            loaderAndRunner.loadAndRun();
            LOG.log(Level.INFO, "Приложение запустилось");
        } catch (Exception ex)
        {
            JOptionPaneError.showErrorMsg("Ошибка запуска \"Сервиса приложений\".", ex);
        }
        return "";
    }

    @Override
    protected void done()
    {
        windowNotify.setVisible(false);
    }


}
