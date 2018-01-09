package ru.ezhov.appservice.frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import ru.ezhov.appservice.IconLoader;
import ru.ezhov.appservice.TitleCreator;
import ru.ezhov.appservice.enumapp.AppButtonAction;
import ru.ezhov.appservice.managers.ActionButtonManager;
import ru.ezhov.ujatools.xmlobjects.ApplicationInstance;
import ru.ezhov.appservice.RunApp;

/**
 * Панель, которая представляет из себя приложение, то есть вся работа с приложением осуществляется здесь
 * <p>
 *
 * @author ezhov_da
 */
public class AppPanel extends JXTaskPane {
    private static final Logger LOG = Logger.getLogger(AppPanel.class.getName());
    private RunApp runApp;
    private ApplicationInstance applicationInstance;
    private JButton buttonRun;
    private JButton buttonInstall;
    private JButton buttonUpdate;
    private JButton buttonRemove;
    private JEditorPane textPaneDescription;
    private JEditorPane textPaneNews;
    private JPanel panelButton;

    public AppPanel(RunApp runApp) {
        setCollapsed(true);
        this.runApp = runApp;
        this.applicationInstance = runApp.getApplicationInstance();
        buttonRun = new JButton();
        buttonInstall = new JButton();
        buttonUpdate = new JButton();
        buttonRemove = new JButton();
        textPaneDescription = new JTextPane();
        textPaneDescription.setEditable(false);
        textPaneNews = new JTextPane();
        textPaneNews.setEditable(false);
        panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        init();
        registerApp(runApp);
    }

    public void reloadAppPanel(RunApp runApp) {
        registerApp(runApp);
    }

    private void registerApp(RunApp runApp) {
        this.runApp = runApp;
        this.applicationInstance = runApp.getApplicationInstance();
        setTitle(TitleCreator.getTitleApp(applicationInstance) + (runApp.isMustBeUpdate() ? " [Обновление...]" : ""));
        setIconFromUrl();
        setDescriptionAndNews();
        if (runApp.isRunning()) {
            buttonRun.setAction(new ActionStop(runApp));
        } else {
            buttonRun.setAction(new ActionRun(runApp));
        }
        buttonInstall.setAction(new ActionInstall(runApp));
        buttonUpdate.setAction(new ActionUpdate(runApp));
        buttonRemove.setAction(new ActionRemove(runApp));
        remove(buttonRun);
        remove(buttonInstall);
        remove(buttonUpdate);
        remove(buttonRemove);
        try {
            fillPanelButton();
        } catch (IOException ex) {
            Logger.getLogger(AppPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        revalidate();
    }

    private void setIconFromUrl() {
        String urlIcon = runApp.getApplicationInstance().getHttpIconImage();
        try {
            Icon icon = IconLoader.getIcon(urlIcon);
            setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(AppPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setDescriptionAndNews() {
        String description = applicationInstance.getDescription();
        String news = applicationInstance.getListOfChanges();
        setTextPane(textPaneDescription, description, isHttp(description));
        setTextPane(textPaneNews, news, isHttp(news));
    }

    private void init() {
        setLayout(new GridBagLayout());
        Insets insets = new Insets(5, 5, 5, 5);
        add(createTabbed(), new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets, 0, 0));
        add(panelButton, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets, 0, 0));
    }

    private boolean isHttp(String text) {
        return text.trim().startsWith("http://");
    }


    private boolean isHtml(String text) {
        return text.trim().startsWith("<html>");
    }


    private void setTextPane(JEditorPane editorPane, String text, boolean setPage) {
        if (setPage) {
            try {
                editorPane.setContentType("text/html");
                editorPane.setPage(text);
            } catch (IOException ex) {
                Logger.getLogger(AppPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (isHtml(text)) {
                editorPane.setContentType("text/html");
            } else {
                editorPane.setContentType("text/plain");
            }
            editorPane.setText(text);
        }
    }

    private JXTaskPaneContainer createTabbed() {
        JXTaskPaneContainer taskPaneContainer = new JXTaskPaneContainer();
        taskPaneContainer.setBackground(new JPanel().getBackground());
        taskPaneContainer.add(createPanelDescription());
        taskPaneContainer.add(createPanelNews());
        return taskPaneContainer;
    }

    private JXTaskPane createPanelDescription() {
        JXTaskPane panel = new JXTaskPane();
        panel.setTitle("Описание");
        panel.setCollapsed(true);
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(textPaneDescription));
        return panel;
    }

    private JXTaskPane createPanelNews() {
        JXTaskPane panel = new JXTaskPane();
        panel.setTitle("Список изменений");
        panel.setCollapsed(true);
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(textPaneNews));
        return panel;
    }

    private void fillPanelButton() throws IOException {
        panelButton.removeAll();
        ActionButtonManager actionButtonManager = new ActionButtonManager(applicationInstance);
        AppButtonAction appButtonAction = actionButtonManager.checkApp();
        switch (appButtonAction) {
            case INSTALL:
                panelButton.add(buttonInstall);
                break;
            case UPDATE:
                //panelButton.add(buttonUpdate);
                //panelButton.add(buttonRemove); //пока убираем эту кнопку, так как она не нуджна
                if (!runApp.isRunning()) {
                    new ActionUpdate(runApp).actionPerformed(null);
                } else {
                    panelButton.add(buttonRun);
                }
                break;
            case RUN:
                panelButton.add(buttonRun);
                //panelButton.add(buttonRemove); //пока убираем эту кнопку, так как она не нуджна
                break;
            default:
                buttonRun.addActionListener(new ActionStop(runApp));
                break;
        }
        panelButton.revalidate();
    }

}
