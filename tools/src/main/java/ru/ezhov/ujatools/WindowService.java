package ru.ezhov.ujatools;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;

import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;

import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 * Данный класс предназначен для добавления к оконным компонентам
 * метода обновления и валидации при отображении, а так же действия на Esc
 * <p>
 *
 * @author ezhov_da
 */
public class WindowService {
    private static final Logger LOG = Logger.getLogger(WindowService.class.getName());
    public static Set<Window> setWindowInApp = new HashSet<Window>();

    public synchronized static final void createListenerForRepaint(final Window window) {
        setWindowInApp.add(window);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                window.validate();
                window.repaint();
                LOG.log(Level.INFO, "Сработало обновление окна для\n: {0}", window);
            }
        });
    }

    public synchronized static final void createEscActionOnWindows(JRootPane rootPane, final Window window) {
        InputMap inputMap = rootPane.getInputMap(WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "closeWindow");
        ActionMap actionMap = rootPane.getActionMap();
        actionMap.put("closeWindow", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
            }
        });
    }

    public static void updateUITree() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for (Window window : setWindowInApp) {
                    SwingUtilities.updateComponentTreeUI(window);
                }
            }
        });
    }
}
