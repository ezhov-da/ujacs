package ru.ezhov.common.objects.ujacs.server;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author ezhov_da
 */
@XStreamAlias("config")
public class CommonConfig {

    private static final Logger LOG = Logger.getLogger(CommonConfig.class.getName());
    @XStreamAlias("serverConfig")
    private ServerConfig serverConfig;
    @XStreamAlias("apps")
    private List<ApplicationConfig> apps;

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public List<ApplicationConfig> getApplicationConfigs() {
        return apps;
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public void setApplicationConfigs(List<ApplicationConfig> apps) {
        this.apps = apps;
    }
}
