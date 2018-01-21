package ru.ezhov.appservice.frame;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.JPanel;

import ru.ezhov.appservice.managers.RunningApplicationManager;
import ru.ezhov.appservice.RunApp;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdesktop.swingx.JXTaskPaneContainer;
import ru.ezhov.appservice.LoadApplicationInstance;

/**
 * Основная панель, на которой распологаются приложения для работы
 * <p>
 *
 * @author ezhov_da
 */
public class BasicPanel extends JXTaskPaneContainer {
    private static final Logger LOG = Logger.getLogger(BasicPanel.class.getName());

    public static BasicPanel basicPanel;
    private Map<String, AppPanel> mapPanels;

    public static final BasicPanel getInstance() {
        if (basicPanel == null) {
            basicPanel = new BasicPanel();
        }
        return basicPanel;
    }

    private BasicPanel() {
        mapPanels = new ConcurrentHashMap();
        setBackground(new JPanel().getBackground());
    }

    public synchronized void load() {
        ReloadPanel reloadPanel = new ReloadPanel();
        reloadPanel.load();
    }

    /**
     * Класс отвечает за загрузку/перезагрузку списка приложений на панели
     */
    private class ReloadPanel {
        public synchronized void load() {
            try {
                loadApplicationInstance();
                loadNewApp();
                removeOldApp();
                BasicPanel.this.revalidate();
            } catch (Exception ex) {
                String s = "Ошибка обновления списка приложений";
                LOG.log(Level.INFO, s, ex);
            }
        }

        private void loadApplicationInstance() throws IOException, ClassNotFoundException {
            LoadApplicationInstance loadApplicationInstance = new LoadApplicationInstance();
            loadApplicationInstance.loadAndRegisterApps();
        }

        /**
         * загружаем новые приложения в панель
         * <p>
         * return список приложений. которые были добавлены в панель
         */
        private void loadNewApp() {
            RunningApplicationManager runningApplicationManager = RunningApplicationManager.getInstance();
            Set<Entry<String, RunApp>> setEntry = runningApplicationManager.getEntrySet();
            Iterator<Entry<String, RunApp>> iterator = setEntry.iterator();
            while (iterator.hasNext()) {
                Entry<String, RunApp> entry = iterator.next();
                String key = entry.getKey();
                RunApp runApp = entry.getValue();
                if (mapPanels.containsKey(key)) {
                    AppPanel appPanel = mapPanels.get(key);
                    appPanel.reloadAppPanel(runApp);
                } else {
                    AppPanel appPanel = new AppPanel(runApp);
                    mapPanels.put(key, appPanel);
                    add(appPanel);
                }
            }
        }

        //Удаляем старые, не запущенные приложения, которые убраны у пользователя
        private void removeOldApp() {
            RunningApplicationManager runningApplicationManager = RunningApplicationManager.getInstance();
            Set<String> setKeyUserApp = runningApplicationManager.keySet();
            Set<String> setKeyAddApp = mapPanels.keySet();
            Iterator<String> it = setKeyAddApp.iterator();
            while (it.hasNext()) {
                String nameAppAdd = it.next();
                if (!setKeyUserApp.contains(nameAppAdd)) {
                    remove(mapPanels.get(nameAppAdd));
                    mapPanels.remove(nameAppAdd);
                }
            }
        }
    }
}
