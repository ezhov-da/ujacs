package ru.ezhov.server;

import com.sun.jna.platform.win32.Kernel32;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ru.ezhov.server.frame.BasicFrame;

public class ServerUpdate {
    private static final Logger LOG = Logger.getLogger(ServerUpdate.class.getName());

    public static void main(String[] args) {
        installLogManeger();
        LOG.log(Level.INFO, "PID: {0}", Kernel32.INSTANCE.GetCurrentProcessId());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> ServerHolder.getInstance().stopServer()));
        showFrame();
    }

    private static void installLogManeger() {
        try {
            LogManager.getLogManager().readConfiguration(ServerUpdate.class.getResourceAsStream("log.properties"));
        } catch (IOException ex) {
            Logger.getLogger(ServerUpdate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ServerUpdate.class.getName()).log(Level.SEVERE, null, ex);
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
