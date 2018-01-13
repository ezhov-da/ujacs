package ru.ezhov.updater;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Это класс, который создает сервер и ждет подключения от сервиса приложений,
 * таим образом когда нужно обновить сервис приложений, выключаем сервис, сервис приложений читает это
 * и закрывает приложений
 * <p>
 *
 * @author ezhov_da
 */
public class ServerSocketUpdateApp {
    private static final Logger LOG = Logger.getLogger(ServerSocketUpdateApp.class.getName());
    private static ServerSocketUpdateApp serverSocketUpdateApp;
    private ServerSocket serverSocket;
    private Socket socket;

    public static ServerSocketUpdateApp getInstance() {
        if (serverSocketUpdateApp == null) {
            serverSocketUpdateApp = new ServerSocketUpdateApp();
        }
        return serverSocketUpdateApp;
    }

    private ServerSocketUpdateApp() {
        try {
            serverSocket = new ServerSocket(60000);
        } catch (Exception ex) {
            String s = "Не удалось запустить сокет";
            LOG.log(Level.SEVERE, s, ex);
            JOptionPane.showMessageDialog(null, s + "\n" + ex.getMessage());
            System.exit(-2000);
        }
    }

    public void startServer() throws IOException {
        LOG.log(Level.INFO, "Начинаем запуск сокета");
        while (true) {
            LOG.log(Level.INFO, "Ждем соединения");
            Socket s = serverSocket.accept();
            LOG.log(Level.INFO, "Соединение получено: {0}", s.getInetAddress().getHostAddress());
            socket = s;
        }
    }

    public void closeSocket() throws IOException {
        LOG.log(Level.INFO, "Закрываем сокеты");
        if (socket != null && !socket.isClosed()) {
            LOG.info("Сокет не null");
            socket.close();
        } else {
            LOG.info("Сокет null");
        }
    }
}
