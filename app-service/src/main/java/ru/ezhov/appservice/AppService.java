package ru.ezhov.appservice;

import com.sun.jna.platform.win32.Kernel32;
import ru.ezhov.ujatools.JOptionPaneError;
import ru.ezhov.ujatools.LoadHttpProperties;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

import ru.ezhov.appservice.frame.BasicFrame;
import ru.ezhov.appservice.tray.AppTray;
import ru.ezhov.ujatools.AppRussifier;

/**
 * ВАЖНО! Нужно понимать, что вся работа с внешними приложениями осуществляется
 * на каталог выше данного установленного приложения, то есть все папки создаем и ищем там.
 * <p>
 *
 * @author ezhov_da
 */
public class AppService {
    private static final Logger LOG = Logger.getLogger(AppService.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AppRussifier.runRussifier();
        registerLogManager();
        LOG.log(Level.INFO, "PID: {0}", Kernel32.INSTANCE.GetCurrentProcessId());
        addHook();
        try {
            loadHttpProperties();
        } catch (IOException ex) {
            String text = "Ошибка загрузки настроек http";
            LOG.log(Level.SEVERE, text, ex);
            JOptionPaneError.showErrorMsg(text, ex);
            return;
        }
        try {
            loadApplicationInstance();
            runAppSaveBefore();
        } catch (Exception ex) {
            String text = "Ошибка загрузки списка приложений";
            LOG.log(Level.SEVERE, text, ex);
            JOptionPaneError.showErrorMsg(text, ex);
        }
        showFrame();
        AppUpdater appUpdater = new AppUpdater();
        appUpdater.startTaskCheckUpdate();
        try {
            LOG.log(Level.INFO, "Запускаем сокет из main");
            startSocket();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Ошибка запуска сокета", ex);
        }
    }

    private static void registerLogManager() {
        try {
            LogManager
                    .getLogManager()
                    .readConfiguration(
                            AppService.class.getResourceAsStream("/log.properties")
                    );
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    private static void addHook() {
        LOG.info("Добавляем hook");
        Runtime.getRuntime().addShutdownHook(new AppHook());
    }

    private static void startSocket() throws IOException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                SocketConnector socketConnector = new SocketConnector();
                try {
                    socketConnector.connect();
                } catch (IOException ex) {
                    String text =
                            "Важно! \n" +
                                    "Приложение запущено вне режима обновления, " +
                                    "в данном режиме обновление приложения недоступно.\n\n" +
                                    "Ошибка подключения сокета";
                    JOptionPaneError.showErrorMsg(text, ex);
                }
            }
        };
        thread.start();
    }

    private static void loadHttpProperties() throws IOException {
        LoadHttpProperties loadHttpProperties = new LoadHttpProperties();
        loadHttpProperties.load();
    }

    private static void loadApplicationInstance() throws Exception {
        LoadApplicationInstance loadApplicationInstance = new LoadApplicationInstance();
        loadApplicationInstance.loadAndRegisterApps();
    }

    private static void runAppSaveBefore() {
        RunSaveApp runSaveApp = new RunSaveApp();
        runSaveApp.execute();
    }

    private static void showFrame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BasicFrame frame = BasicFrame.getInstance();
                frame.setVisible(true);
                AppTray.getInstance().installTray();
            }
        });
    }

}
