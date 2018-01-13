package ru.ezhov.updater;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import ru.ezhov.ujatools.JOptionPaneError;

/**
 * Класс, который запускает приложение
 * <p>
 *
 * @author ezhov_da
 */
public class Runner {
    private static final Logger LOG = Logger.getLogger(Runner.class.getName());
    private String pathForRunApp;

    public Runner(String pathForRunApp) {
        this.pathForRunApp = pathForRunApp;
    }

    //1. проверяем есть ли папка приложения
    //2. сходятся ли версии
    //3. удаляем файл конфигурации и папку с приложением
    //4. скачиваем снова
    //5. распаковываем
    //6. запускаем
    public void run() {
        try {
            LOG.log(Level.INFO, "Проверяем наличие папки");
            File appFolder = new AppFolder().constructPathToAppFolder();
            if (!appFolder.exists()) {
                firstInstall(appFolder);
            } else {
                LOG.log(Level.INFO, "Папка есть, проверяем версию");
                CheckVersion checkVersion = new CheckVersion();
                if (checkVersion.versionEquals()) {
                    LOG.log(Level.INFO, "Версия сошлась, запускаем");
                    startApp();
                } else {
                    versionNotEquals();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPaneError.showErrorMsg("Не удалось запустить приложение.", ex);
            System.exit(-2000);
        }
    }

    private void firstInstall(File appFolder) throws Exception {
        appFolder.mkdirs();
        LOG.log(Level.INFO, "Папки нет, загружаем архив");
        LoadZipFromServer loadZipFromServer = new LoadZipFromServer();
        String path = loadZipFromServer.load();
        LOG.log(Level.INFO, "Распаковываем");
        Unzip unzip = new Unzip();
        unzip.unzipArchive(path);
        LOG.log(Level.INFO, "Запускаем");
        startApp();
    }

    private void versionNotEquals() throws Exception {
        LOG.log(Level.INFO, "Версия не сошлась");
        LOG.log(Level.INFO, "Чистим папку");
        try {
            ClearFileAppService clearFileAppService = new ClearFileAppService();
            clearFileAppService.deleteFilesAppService();
        } catch (Exception ex) {
            LOG.log(Level.WARNING, "Ошибка удаления файлов", ex);
        }
        LOG.log(Level.INFO, "Загружаем архив");
        LoadZipFromServer loadZipFromServer = new LoadZipFromServer();
        String path = loadZipFromServer.load();
        LOG.log(Level.INFO, "Распаковываем");
        Unzip unzip = new Unzip();
        unzip.unzipArchive(path);
        LOG.log(Level.INFO, "Запускаем");
        startApp();
    }

    private void startApp() throws IOException {
        startSocket();
        LOG.log(Level.INFO, "Пробуем запустить приложение: {0}", pathForRunApp);
        final Process process = Runtime.getRuntime().exec(pathForRunApp);
        ProcessRegister.getInstance().registerProcess(process);
        TreadProcessHolder treadProcessHolder = new TreadProcessHolder(process);
        treadProcessHolder.setDaemon(true);
        treadProcessHolder.start();
        startDaemonUpdater();
    }

    public static void startSocket() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                ServerSocketUpdateApp serverSocketUpdateApp = ServerSocketUpdateApp.getInstance();
                try {
                    LOG.log(Level.INFO, "Запускаем сокет");
                    serverSocketUpdateApp.startServer();
                } catch (Exception ex) {
                    String s = "Не удалось запустить сокет";
                    LOG.log(Level.SEVERE, s, ex);
                    JOptionPane.showMessageDialog(null, s + "\n" + ex.getMessage());
                    System.exit(-2000);
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    private void startDaemonUpdater() {
        Thread thread = new TaskCheckUpdate();
        thread.setDaemon(true);
        thread.start();
    }

}
