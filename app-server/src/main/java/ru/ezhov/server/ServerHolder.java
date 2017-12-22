package ru.ezhov.server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

/**
 * Сервер обновлений
 * <p>
 *
 * @author ezhov_da
 */
public class ServerHolder {
    private static final Logger LOG = Logger.getLogger(ServerHolder.class.getName());
    private static ServerHolder serverHolder;
    private Server server;
    private PropertiesHolder propertiesHolder;

    private ServerHolder() {
    }

    public static synchronized ServerHolder getInstance() {
        if (serverHolder == null) {
            serverHolder = new ServerHolder();
        }
        return serverHolder;
    }

    public void startServer() {
        try {
            loadProperties();
            int port = getPort();
            server = new Server(port);
            HandlerList handlerList = getHandlers();
            server.setHandler(handlerList);
            Thread thread = new Thread(new ThreadStartServer(port));
            thread.setDaemon(true);
            thread.start();
        } catch (IOException ex) {
            String text = "Не удалось прочитать файл настроек сервера.";
            LOG.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, text + "\n" + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            String text = "Не удалось пропарсить номер порта, проверьте корректность.";
            LOG.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, text + "\n" + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void stopServer() {
        if (isRunningServer()) {
            try {
                server.stop();
                LOG.log(Level.INFO, "Сервер остановлен");
            } catch (Exception ex) {
                String text = "Ошибка остановки сервера";
                LOG.log(Level.SEVERE, text, ex);
                JOptionPane.showMessageDialog(null, text + "\n" + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public boolean isRunningServer() {
        if (server == null) {
            return false;
        }
        return server.isRunning();
    }

    private void loadProperties() throws IOException {
        propertiesHolder = PropertiesHolder.getInstance();
    }

    private int getPort() throws NumberFormatException {
        return Integer.valueOf(propertiesHolder.getProperties(PropertiesConst.SERVER_PORT));
    }

    private HandlerList getHandlers() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(propertiesHolder.getProperties(PropertiesConst.FOLDER_RESOURCE));
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]
                {
                        resourceHandler
                });
        return handlers;
    }


    private class ThreadStartServer implements Runnable {
        private int port;

        public ThreadStartServer(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            try {
                server.start();
                LOG.log(Level.INFO, "Сервер запущен на порту: {0}", port);
                server.join();
            } catch (Exception e) {
                String text = "Не удалось запустить сервер.";
                LOG.log(Level.SEVERE, null, e);
                JOptionPane.showMessageDialog(null, text + "\n" + e.getMessage(), null, JOptionPane.ERROR_MESSAGE);
            }
        }

    }

}
