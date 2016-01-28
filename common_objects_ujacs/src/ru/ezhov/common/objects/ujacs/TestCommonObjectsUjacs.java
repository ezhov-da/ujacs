package ru.ezhov.common.objects.ujacs;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;
import java.util.ArrayList;
import java.util.List;
import ru.ezhov.common.objects.ujacs.client.ClientConfig;
import ru.ezhov.common.objects.ujacs.server.ApplicationConfig;
import ru.ezhov.common.objects.ujacs.server.CommonConfig;
import ru.ezhov.common.objects.ujacs.server.ServerConfig;

/**
 *
 * @author ezhov_da
 */
public class TestCommonObjectsUjacs {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        XStream xStream = new XStream();
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setPort(44545);
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setExtFile("расширение файла");
        applicationConfig.setFileForRunApp("файл для запуска");
        applicationConfig.setLnkNameFromArchive("имя иконки в архиве");
        applicationConfig.setLnkNameOnDesktop("имя иконки на рабочем столе");
        applicationConfig.setName("имя приложения");
        applicationConfig.setNameArchive("имя архива");
        applicationConfig.setNameFolderApp("имя папки с приложением");
        applicationConfig.setShortCutCreate("текст для создания vbs");
        applicationConfig.setVersion("версия");
        CommonConfig commonConfig = new CommonConfig();
        commonConfig.setServerConfig(serverConfig);
        List<ApplicationConfig> applicationConfigs = new ArrayList<ApplicationConfig>();
        applicationConfigs.add(applicationConfig);
        commonConfig.setApplicationConfigs(applicationConfigs);
        Annotations.configureAliases(xStream, CommonConfig.class);
        Annotations.configureAliases(xStream, ServerConfig.class);
        Annotations.configureAliases(xStream, ApplicationConfig.class);
        Annotations.configureAliases(xStream, ClientConfig.class);
        System.out.println(xStream.toXML(commonConfig));
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setPort(9894);
        clientConfig.setHost("123.23234.52236236");
        System.out.println(xStream.toXML(clientConfig));
        InformationClass informationClass = new InformationClass("test", Commands.NEW_LOAD);
        Annotations.configureAliases(xStream, InformationClass.class);
        System.out.println(xStream.toXML(informationClass));
        informationClass = new InformationClass("test", Commands.CHECK_UPDATE);
        System.out.println(xStream.toXML(informationClass));
    }

}
