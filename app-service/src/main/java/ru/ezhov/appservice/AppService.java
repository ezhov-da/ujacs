package ru.ezhov.appservice;

import com.sun.jna.platform.win32.Kernel32;
import ru.ezhov.ujatools.LoadHttpProperties;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import ru.ezhov.appservice.frame.BasicFrame;
import ru.ezhov.appservice.managers.RunningApplicationManager;
import ru.ezhov.appservice.managers.actionmanagers.AppRunManager;
import ru.ezhov.appservice.tray.AppTray;
import ru.ezhov.ujatools.AppRussifier;
import ru.ezhov.ujatools.xmlobjects.RunnerApp;

/**
 * ВАЖНО! Нужно понимать, что вся работа с внешними приложениями осуществляется
 * на каталог выше данного установленного приложения, то есть все папки создаем и ищем там.
 * <p>
 * @author ezhov_da
 */
public class AppService
{
    private static final Logger LOG = Logger.getLogger(AppService.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        AppRussifier.runRussifier();
        registerLogManager();
        LOG.log(Level.INFO, "PID: {0}", Kernel32.INSTANCE.GetCurrentProcessId());
        addHook();
        try
        {
            loadHttpProperties();
        } catch (IOException ex)
        {
            String text = "Ошибка загрузки настроек http";
            LOG.log(Level.SEVERE, text, ex);
            JOptionPane.showMessageDialog(null, text + "\n" + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try
        {
            loadApplicationInstance();
            runAppSaveBefore();
        } catch (Exception ex)
        {
            String text = "Ошибка загрузки списка приложений";
            LOG.log(Level.SEVERE, text, ex);
            JOptionPane.showMessageDialog(null, text + "\n" + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        showFrame();
        AppUpdater appUpdater = new AppUpdater();
        appUpdater.startTaskCheckUpdate();
        try
        {
            LOG.log(Level.INFO, "Запускаем сокет из main");
            startSocket();
        } catch (IOException ex)
        {
            LOG.log(Level.OFF, "Ошибка запуска сокета", ex);
        }
    }

    private static void registerLogManager()
    {
        try
        {
            LogManager.getLogManager().readConfiguration(AppService.class.getResourceAsStream("log.properties"));
        } catch (IOException ex)
        {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex)
        {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void addHook()
    {
        LOG.info("Добавляем hook");
        Runtime.getRuntime().addShutdownHook(new AppHook());
    }

    private static void startSocket() throws IOException
    {
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                SocketConnector socketConnector = new SocketConnector();
                try
                {
                    socketConnector.connect();
                } catch (IOException ex)
                {
                    String text = "Ошибка подключения сокета";
                    LOG.log(Level.SEVERE, text, ex);
                    JOptionPane.showMessageDialog(null, text + "\n" + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                    System.exit(-1000);
                }
            }
        };
        thread.start();
    }

    private static void loadHttpProperties() throws IOException
    {
        LoadHttpProperties loadHttpProperties = new LoadHttpProperties();
        loadHttpProperties.load();
    }

    private static void loadApplicationInstance() throws Exception
    {
        LoadApplicationInstance loadApplicationInstance = new LoadApplicationInstance();
        loadApplicationInstance.loadAndRegisterApps();
    }


    private static void runAppSaveBefore()
    {
        RunSaveApp runSaveApp = new RunSaveApp();
        runSaveApp.execute();
    }

    private static void showFrame()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                BasicFrame frame = BasicFrame.getInstance();
                frame.setVisible(true);
                AppTray.getInstance().installTray();
            }
        });
    }

}
