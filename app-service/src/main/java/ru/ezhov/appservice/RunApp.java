package ru.ezhov.appservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.ezhov.ujatools.xmlobjects.ApplicationInstance;
import java.util.concurrent.TimeUnit;
import ru.ezhov.appservice.frame.WindowNotifyUpdate;

/**
 * Это класс обертка, с которым работает {@link RunningApplicationManager}
 * <p>
 * @author ezhov_da
 */
public class RunApp
{
    private static final Logger LOG = Logger.getLogger(RunApp.class.getName());

    private boolean mustBeUpdate;
    //приложение за которым мы следим
    private ApplicationInstance applicationInstance;
    //процесс, который создаем при запуске
    private Process process;

    private WindowNotifyUpdate windowNotifyUpdate;


    private List<ListenerDestroyApp> destroyApps;


    public RunApp(ApplicationInstance applicationInstance)
    {
        mustBeUpdate = isMustBeUpdateCheck(applicationInstance);
        this.windowNotifyUpdate = new WindowNotifyUpdate();
        this.applicationInstance = applicationInstance;
        this.destroyApps = new ArrayList<ListenerDestroyApp>();
    }

    private boolean isMustBeUpdateCheck(ApplicationInstance applicationInstance)
    {
        if (this.applicationInstance == null)
        {
            return false;
        }
        return !applicationInstance.getVersion().equals(this.applicationInstance.getVersion());
    }

    public void addListenerDestroyApp(ListenerDestroyApp listenerDestroyApp)
    {
        destroyApps.add(listenerDestroyApp);
    }

    public void removeListenerDestroyApp(ListenerDestroyApp listenerDestroyApp)
    {
        destroyApps.remove(listenerDestroyApp);
    }

    public void notifyListenerDestroyApp(RunApp runApp)
    {
        for (ListenerDestroyApp listenerDestroyApp : destroyApps)
        {
            listenerDestroyApp.destroyApp(runApp);
        }
    }

    public void setApplicationInstance(ApplicationInstance applicationInstance)
    {
        mustBeUpdate = isMustBeUpdateCheck(applicationInstance);
        this.applicationInstance = applicationInstance;
        showUpdateWindow();
    }

    private void showUpdateWindow()
    {
        if (isMustBeUpdate() && isRunning())
        {
            if (!windowNotifyUpdate.isShowing())
            {
                windowNotifyUpdate.setVisible(true, this);
            }
        } else
        {
            if (!windowNotifyUpdate.isShowing())
            {
                windowNotifyUpdate.setVisible(false);
            }
        }
    }

    public boolean isMustBeUpdate()
    {
        return mustBeUpdate;
    }

    public ApplicationInstance getApplicationInstance()
    {
        return applicationInstance;
    }

    public Process getProcess()
    {
        return process;
    }

    public synchronized void runProcess(final String commandRun)
            throws IOException
    {
        process = Runtime.getRuntime().exec(commandRun);
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    LOG.log(Level.INFO, "Запускаем приложение: {0}", commandRun);
                    process.waitFor();
                    notifyListenerDestroyApp(RunApp.this);
                } catch (InterruptedException ex)
                {
                    Logger.getLogger(RunApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        thread.start();
    }

    public synchronized boolean isRunning()
    {
        if (process == null)
        {
            return false;
        } else
        {
            try
            {
                process.exitValue();
                return false;
            } catch (Exception e)
            {
                return true;
            }
        }
    }

    public synchronized void destroyProcess() throws InterruptedException
    {
        if (process != null)
        {
            windowNotifyUpdate.setAlreadyShow(false);
            LOG.log(Level.INFO, "Убиваем процесс: {0}", applicationInstance.getNameAppUser());
            process.destroy();
            //ожидаем завершение приложения в течении 5сек
            long l = TimeUnit.SECONDS.convert(1L, TimeUnit.SECONDS);
            TimeUnit.SECONDS.sleep(l);
        }
    }
}
