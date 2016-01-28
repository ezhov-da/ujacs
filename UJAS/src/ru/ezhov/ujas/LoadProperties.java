package ru.ezhov.ujas;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.*;
import java.util.logging.Logger;
import ru.ezhov.common.objects.ujacs.server.ApplicationConfig;
import ru.ezhov.common.objects.ujacs.server.CommonConfig;
import ru.ezhov.common.objects.ujacs.server.ServerConfig;

/**
 * Загрузчик настроек
 *
 * @author ezhov_da
 */
public class LoadProperties {

    private static final Logger LOG = Logger.getLogger(LoadProperties.class.getName());
    private static final String NAME_FILE_CONFIG = "config_ujas.xml";

    private LoadProperties() {
    }

    public static synchronized CommonConfig getCommonConfig() throws FileNotFoundException {
        XStream xStream = new XStream(new DomDriver());
        Annotations.configureAliases(xStream, CommonConfig.class);
        Annotations.configureAliases(xStream, ServerConfig.class);
        Annotations.configureAliases(xStream, ApplicationConfig.class);
        CommonConfig commonConfig = (CommonConfig) xStream.fromXML(new FileReader(new File(NAME_FILE_CONFIG)));
        return commonConfig;
    }
}
