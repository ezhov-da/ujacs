package ru.ezhov.ujacsappupdater;

import java.io.*;
import java.util.logging.*;

/**
 *
 * @author ezhov_da
 */
public class ContollerVersion
{
    private static final Logger LOG = Logger.getLogger(ContollerVersion.class.getName());
    private final String nameApp;
    private final String version;
    private final String host;
    private final int port;

    public ContollerVersion(String nameApp, String version, String host, int port)
    {
        this.nameApp = nameApp;
        this.version = version;
        this.host = host;
        this.port = port;
    }

    public void startCheckerAndUpdate() throws IOException
    {
        LOG.log(Level.INFO, "запущено обновление приложения");
        UpdateRunner mySocket = new UpdateRunner(nameApp, version, host, port);
        mySocket.start();
    }
}
