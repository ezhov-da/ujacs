package ru.ezhov.ujac;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.*;
import ru.ezhov.common.objects.ujacs.client.ClientConfig;

/**
 *
 * @author ezhov_da
 */
public class LoadProperties
{
    private static final String FILE_CONFIG = "config_ujac.xml";
    public static final LoadProperties INSTANCE = new LoadProperties();

    private LoadProperties()
    {
    }

    public ClientConfig getProperties() throws FileNotFoundException, IOException
    {
        XStream stream = new XStream(new DomDriver());
        Annotations.configureAliases(stream, ClientConfig.class);
        ClientConfig clientConfig = (ClientConfig) stream.fromXML(new FileReader(new File(FILE_CONFIG)));
        return clientConfig;
    }
}
