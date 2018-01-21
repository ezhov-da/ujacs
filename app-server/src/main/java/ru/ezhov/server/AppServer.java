package ru.ezhov.server;

import com.sun.jna.platform.win32.Kernel32;
import ru.ezhov.server.action.Server;
import ru.ezhov.server.action.ServerActionType;
import ru.ezhov.server.common.ServerHolder;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class AppServer {
    private static final Logger LOG = Logger.getLogger(AppServer.class.getName());

    public static void main(String[] args) {
        AppServer appServer = new AppServer();
        appServer.installLogManager();
        LOG.log(Level.INFO, "Application server started with PID: {0}", Kernel32.INSTANCE.GetCurrentProcessId());
        appServer.addHooks();
        appServer.checkAndRunCommand(args);
    }

    private void installLogManager() {
        try {
            LogManager.getLogManager().readConfiguration(AppServer.class.getResourceAsStream("/log.properties"));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    private void addHooks() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> ServerHolder.getInstance().stopServer()));
    }

    private void checkAndRunCommand(String[] args) {
        Server server = new Server();
        try {
            if (args.length == 0) {
                throw new IllegalArgumentException("Должна быть указана команда");
            } else {
                String arg = args[0].toUpperCase();
                ServerActionType serverActionType = ServerActionType.valueOf(arg);
                server.execute(serverActionType);
            }
        } catch (IllegalArgumentException e) {
            server.execute(ServerActionType.HELP);
        }
    }
}
