package ru.ezhov.common.objects.ujacs.client;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.logging.Logger;

/**
 *
 * @author ezhov_da
 */
@XStreamAlias("config")
public class ClientConfig {

    private static final Logger LOG = Logger.getLogger(ClientConfig.class.getName());
    private String host;
    private int port;
    private String nameApp;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getNameApp() {
        return nameApp;
    }

    public void setNameApp(String nameApp) {
        this.nameApp = nameApp;
    }

    @Override
    public String toString() {
        return String.format("host: %s\nport: %s\nnameApp: %s", host, port, nameApp);
    }

}
