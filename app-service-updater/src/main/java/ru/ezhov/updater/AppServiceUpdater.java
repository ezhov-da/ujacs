package ru.ezhov.udater;

import com.sun.jna.platform.win32.Kernel32;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ru.ezhov.ujatools.AppRussifier;
import ru.ezhov.ujatools.OneCopyApplicationControler;
import ru.ezhov.ujatools.JOptionPaneError;
import ru.ezhov.ujatools.LoadHttpProperties;

/**
 * Это приложение используется как запускатор для сервиса приложений и именно он отвечает за
 * его обновление
 * <p>
 * @author ezhov_da
 */
public class UpdaterAppService
{
    private static final Logger LOG = Logger.getLogger(UpdaterAppService.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        loadLogManager();
        try
        {
            checkRunnApp();
            savePID();
            AppRussifier.runRussifier();
            addHook();
            start();
        } catch (FileNotFoundException ex)
        {
            String s = "Не удалось проверить уже запущенный экземпляр приложения";
            Logger.getLogger(UpdaterAppService.class.getName()).log(Level.SEVERE, s, ex);
            JOptionPaneError.showErrorMsg(s, ex);
        } catch (IOException ex)
        {
            String s = "Не удалось создать socket";
            Logger.getLogger(UpdaterAppService.class.getName()).log(Level.SEVERE, s, ex);
            JOptionPaneError.showErrorMsg(s, ex);
        }
    }

    private static void loadLogManager()
    {
        try
        {
            LogManager.getLogManager().readConfiguration(UpdaterAppService.class.getResourceAsStream("log.properties"));
        } catch (Exception ex)
        {
            Logger.getLogger(UpdaterAppService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void checkRunnApp() throws FileNotFoundException
    {
        OneCopyApplicationControler.check(new File(System.getProperty("user.dir") + File.separator + "UPDATER_LOCK.lock"));
    }

    private static void savePID()
    {
        Kernel32 kernel32 = Kernel32.INSTANCE;
        int pid = kernel32.GetCurrentProcessId();
        LOG.log(Level.INFO, "PID процесса: {0}", pid);
    }

    private static void start()
    {
        try
        {
            UpdaterAppService updaterAppService = new UpdaterAppService();
            updaterAppService.loadHttpSettings();
            updaterAppService.loadAndRunApp();
        } catch (Exception ex)
        {
            String s = "Не удалось запустить \"Сервис приложений.\"";
            LOG.log(Level.SEVERE, s, ex);
            JOptionPane.showMessageDialog(null, s + "\n" + ex.getMessage());
        }
    }

    private void loadAndRunApp() throws IOException, Exception
    {
        Thread thread = new Thread(new SwingWorkerApp());
        thread.setDaemon(true);
        thread.start();
    }


    private static void addHook()
    {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                LOG.info("Вызываем hook");
                ProcessRegister.getInstance().destroyProcess();
            }
        });
    }

    private void loadHttpSettings() throws IOException
    {
        LoadHttpProperties loadHttpProperties = new LoadHttpProperties();
        loadHttpProperties.load();
    }
}
