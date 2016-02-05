package ru.ezhov.ujacsappupdater;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import ru.ezhov.common.objects.ujacs.Commands;
import ru.ezhov.common.objects.ujacs.InformationClass;
import ru.ezhov.common.objects.ujacs.client.ClientConfig;
import ru.ezhov.common.objects.ujacs.server.ApplicationConfig;
import ru.ezhov.common.objects.ujacs.server.CommonConfig;
import ru.ezhov.common.objects.ujacs.server.ServerConfig;

/**
 *
 * @author ezhov_da
 */
public class CommonObjectsUjacsJsonTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Gson gson = new Gson();
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setPort(44545);
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setExtFile("расширение файла");
        applicationConfig.setFileForRunApp("файл для запуска");
        applicationConfig.setLnkNameFromArchive("имя иконки в архиве");
        applicationConfig.setLnkNameOnDesktop("имя иконки\n"
                + "\n"
                + "\nна рабочем столе");
        applicationConfig.setName("имя приложения");
        applicationConfig.setNameArchive("имя архива");
        applicationConfig.setNameFolderApp("имя папки с приложением");
        applicationConfig.setShortCutCreate(""
                + "Dim oShell, oShortCut, sDeskTopPath\n"
                + "Set oShell = CreateObject(\"Wscript.Shell\")\n"
                + "sDeskTopPath = oShell.SpecialFolders(\"Desktop\") 'на рабочий стол\n"
                + "Set oShortCut = oShell.CreateShortcut(sDeskTopPath &amp; \"\\%s.lnk\")  'название ярлыка\n"
                + "oShortCut.IconLocation = \"%s\" 'путь к иконке\n"
                + "oShortCut.TargetPath = \"%s\" 'путь к запускаемому файлу\n"
                + "	oShortCut.WorkingDirectory = \"%s\" 'путь к рабочей папки\n"
                + "oShortCut.Save");
        applicationConfig.setVersion("версия");
        applicationConfig.setPathToArchiveOnServer(".............");
        CommonConfig commonConfig = new CommonConfig();
        commonConfig.setServerConfig(serverConfig);
        List<ApplicationConfig> applicationConfigs = new ArrayList<ApplicationConfig>();
        applicationConfigs.add(applicationConfig);
        commonConfig.setApplicationConfigs(applicationConfigs);
        System.out.println(gson.toJson(commonConfig));
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setPort(9894);
        clientConfig.setHost("123.23234.52236236");
        clientConfig.setNameApp("test");
        System.out.println(gson.toJson(clientConfig));
        InformationClass informationClass = new InformationClass("test", Commands.NEW_LOAD);

        System.out.println(gson.toJson(informationClass));
        informationClass = new InformationClass("test", Commands.CHECK_UPDATE);
        System.out.println(gson.toJson(informationClass));
    }

}
