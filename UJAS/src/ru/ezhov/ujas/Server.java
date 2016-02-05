package ru.ezhov.ujas;

import ru.ezhov.common.objects.ujacs.tools.LoadProperties;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ru.ezhov.common.objects.ujacs.server.CommonConfig;

/**
 *
 * @author ezhov_da
 */
public class Server {

    private static final Logger LOG = Logger.getLogger(Server.class.getName());
    private static int port;
    private ServerSocket s;

    public void statrServer() {
        LOG.log(Level.INFO, "сервер запускается");
        try {
            initPort();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "ошибка загрузки кофигурационного файла", ex);
            return;
        }
        LOG.log(Level.INFO, "порт сервера: {0}", port);
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
            String myLANIP = addr.getHostAddress();
            LOG.log(Level.INFO, "ip сервера: {0}", myLANIP);
        } catch (UnknownHostException ex) {
            LOG.log(Level.SEVERE, "Ошибка получения ip", ex);
        }
        try {
            startServerSocket();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "сервер упал", ex);
            JOptionPane.showMessageDialog(null, "Ошибка запуска сервера, подробности в логе.");
            System.exit(5);
        }
    }

    private void initPort() throws Exception {
        CommonConfig commonConfig = LoadProperties.getCommonConfig();
        port = commonConfig.getServerConfig().getPort();
    }

    private void startServerSocket() throws IOException {
        s = new ServerSocket(port);
        LOG.log(Level.INFO, "сервер запущен");
        while (true) {
            Socket socket = s.accept();
            LOG.log(Level.INFO, "запустили новое соединение");
            startNewConnect(socket);
        }
    }

    private void startNewConnect(Socket socket) {
        try {
            TreatmentSocket treatmentSocket = new TreatmentSocket(socket);
            treatmentSocket.start();
        } catch (IOException e) {
            LOG.log(Level.WARNING, "ошибка при создании нового подключения", e);
            try {
                socket.close();
            } catch (IOException ex) {
                //нам не нужно знать, что сокет закрылся с ошибкой
            }
        }
    }

}
