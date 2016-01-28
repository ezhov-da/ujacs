package ru.ezhov.common.objects.ujacs.server;

import java.util.logging.Logger;

/**
 *
 * @author ezhov_da
 */
public class ServerConfig
{
    private static final Logger LOG = Logger.getLogger(ServerConfig.class.getName());

    private int port;

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

}
