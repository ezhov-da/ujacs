package ru.ezhov.server;

import com.sun.jna.platform.win32.Kernel32;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ru.ezhov.server.gui.BasicFrame;

public class App {
    private static final Logger LOG = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        installLogManager();
        LOG.log(Level.INFO, "Application server started with PID: {0}", Kernel32.INSTANCE.GetCurrentProcessId());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> ServerHolder.getInstance().stopServer()));
        showFrame();
    }

    private static void installLogManager() {
        try {
            LogManager.getLogManager().readConfiguration(App.class.getResourceAsStream("/log.properties"));
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void showFrame() {
        SwingUtilities.invokeLater(() ->
        {
            {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Throwable ex) {
                    //
                }
                BasicFrame basicFrame = new BasicFrame();
                basicFrame.setVisible(true);
            }
        });
    }

}
