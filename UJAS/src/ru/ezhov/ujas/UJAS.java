package ru.ezhov.ujas;

import com.sun.jna.platform.win32.Kernel32;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.*;
import javax.swing.ImageIcon;

public class UJAS {

    private static final Logger LOG = Logger.getLogger(UJAS.class.getName());
    private static final Image IMAGE = new ImageIcon(UJAS.class.getResource("/ru/ezhov/ujas/res/server_network.png")).getImage();
    private static final String TITLE = "сервер обновления java приложений: PID [%s]";
    private static TrayIcon trayIcon;

    static {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("logger.properties"));
        } catch (Exception ex) {
            Logger.getLogger(UJAS.class.getName()).log(Level.WARNING, "не удалось загрузить логгер", ex);
            System.out.println("exception in main load logger");
            System.exit(5);
        }
    }

    public static void main(String[] args) {
        createTray();
        Server server = new Server();
        server.statrServer();
    }

    private static void createTray() {
        //Запускаем сервер в трей
        if (!SystemTray.isSupported()) {
            return;
        }
        SystemTray systemTray = SystemTray.getSystemTray();
        createTrayIcon();
        try {
            systemTray.add(trayIcon);
        } catch (AWTException ex) {
            LOG.log(Level.INFO, "ошибка добавления трэя");
        }
    }

    private static void createTrayIcon() {
        trayIcon = new TrayIcon(IMAGE, createTitle());
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });
    }

    private static String createTitle() {
        return String.format(TITLE, Kernel32.INSTANCE.GetCurrentProcessId());
    }

}
