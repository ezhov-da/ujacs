package ru.ezhov.common.objects.ujacs.tools;

import java.io.*;
import java.util.Scanner;
import ru.ezhov.common.objects.ujacs.client.ClientConfig;
import ru.ezhov.common.objects.ujacs.server.CommonConfig;

/**
 * Загрузчик настроек
 *
 * @author ezhov_da
 */
public class LoadProperties {

    private static final String CHARSET = "UTF-8";
    private static final int COUNT_CHAR = 5000;
    private static final String NAME_FILE_UJAS = "config_ujas.json";
    private static final String NAME_FILE_UJAC = "config_ujac.json";

    private LoadProperties() {
    }

    //TODO: изменить метод на красивый
    public static synchronized CommonConfig getCommonConfig() throws FileNotFoundException {
        String text = getStringFromFile(NAME_FILE_UJAS);
        CommonConfig commonConfig
                = (CommonConfig) JsonConverter.getInstance().convertFromJsonClass(text, CommonConfig.class);
        return commonConfig;
    }

    public static synchronized ClientConfig getClientConfig() throws FileNotFoundException {
        String text = getStringFromFile(NAME_FILE_UJAC);
        ClientConfig clientConfig
                = (ClientConfig) JsonConverter.getInstance().convertFromJsonClass(text, ClientConfig.class);
        return clientConfig;
    }

    private static String getStringFromFile(String nameFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(nameFile), CHARSET);
        StringBuilder stringBuilder = new StringBuilder(COUNT_CHAR);
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine());
        }
        scanner.close();
        return stringBuilder.toString();
    }
}
