package ru.ezhov.appservice;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.ezhov.appservice.managers.RunningApplicationManager;
import ru.ezhov.ujatools.xmlobjects.RunnerApp;

/**
 * Закрываем все запущенные процессы
 * <p>
 *
 * @author ezhov_da
 */
public class AppHook extends Thread {
    private static final Logger LOG = Logger.getLogger(AppHook.class.getName());

    public AppHook() {
    }

    @Override
    public void run() {
        LOG.info("Запускаем hook");
        RunningApplicationManager runningApplicationManager = RunningApplicationManager.getInstance();
        Collection<RunApp> runApps = runningApplicationManager.getValues();
        LOG.log(Level.INFO, "Кол-во приложений для проверки: {0}", runApps.size());
        StartApp startAppTest = null;
        try {
            startAppTest = new StartApp();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Не удалось создать хранителя запущенных приложений", ex);
        }
        for (RunApp runApp : runApps) {
            try {
                if (startAppTest != null) {
                    if (runApp.isRunning()) {
                        startAppTest.addRunnerApp(new RunnerApp(runApp.getApplicationInstance().getNameAppSystem()));
                    }
                }
                runApp.destroyProcess();
                LOG.log(Level.INFO, "Приложение закрыто: {0}", runApp.getApplicationInstance().getNameAppUser());
            } catch (InterruptedException ex) {
                Logger.getLogger(AppHook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (startAppTest != null) {
            startAppTest.save();
        }
    }
}
