package ru.ezhov.server;

import com.sun.jna.platform.win32.Kernel32;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.*;

import ru.ezhov.server.gui.BasicFrame;

public class App {
    private static final Logger LOG = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        App app = new App();
        try {
            app.installLogManager();
            LOG.log(Level.INFO, "Application server started with PID: {0}", Kernel32.INSTANCE.GetCurrentProcessId());
            app.unpackResources();
            app.addHooks();
            app.showFrame();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Ошибка распаковки ресурсов", e);
            JOptionPane.showMessageDialog(
                    null,
                    "Ошибка распаковки ресурсов",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void installLogManager() {
        try {
            LogManager.getLogManager().readConfiguration(App.class.getResourceAsStream("/log.properties"));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    private void addHooks() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> ServerHolder.getInstance().stopServer()));
    }

    private void unpackResources() throws Exception {
        UnpackResources unpackResources = new UnpackResources();
        unpackResources.unpack();
        LOG.log(Level.INFO, "Ресурсы распакованы");
    }

    private void showFrame() {
        SwingUtilities.invokeLater(() ->
        {
            {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Throwable ex) {
                    LOG.log(Level.WARNING, "Ошибка установлки L&F по умолчанию", ex);
                }
                BasicFrame basicFrame = new BasicFrame();
                basicFrame.setVisible(true);
            }
        });
    }

}
