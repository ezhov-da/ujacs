package ru.ezhov.appservice;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import ru.ezhov.appservice.managers.RunningApplicationManager;
import ru.ezhov.appservice.managers.actionmanagers.AppRunManager;
import ru.ezhov.ujatools.xmlobjects.RunnerApp;

/**
 * Запускаем ранее запущенные приложения
 * <p>
 *
 * @author ezhov_da
 */
public class RunSaveApp {
    private static final Logger LOG = Logger.getLogger(RunSaveApp.class.getName());

    private Set<RunApp> set;

    public void execute() {
        try {
            StartApp startApp = new StartApp();
            startApp.load();
            List<RunnerApp> list = startApp.getListRunnerApp();
            set = new HashSet<RunApp>();
            for (RunnerApp runnerApp : list) {
                RunApp runApp = RunningApplicationManager.getInstance().getRunApp(runnerApp.getNameAppSystem());
                if (runApp != null) {
                    set.add(runApp);
                }
            }
            if (set.size() > 0) {
                int q = JOptionPane.showConfirmDialog(
                        null,
                        getTextQuestion(set),
                        "Запуск приложений",
                        JOptionPane.YES_NO_OPTION
                );
                if (q == JOptionPane.YES_OPTION) {
                    runApp();
                }
            }
        } catch (IOException ex) {
            LOG.log(Level.WARNING, "Не удалось запустить ранее запущенные приложения", ex);
        }
    }

    public String getTextQuestion(Set<RunApp> set) {
        String nameApps = "";
        for (RunApp runApp : set) {
            nameApps =
                    nameApps +
                            "- " +
                            Jsoup.parse(
                                    runApp.getApplicationInstance().getNameUserFull()
                            ).text() +
                            "\n";
        }
        return "Ранее запущенные приложения: \n" + nameApps + "Запустить?";
    }

    private void runApp() {
        for (RunApp runApp : set) {
            AppRunManager appRunManager = new AppRunManager(runApp);
            appRunManager.run();
        }
    }
}
