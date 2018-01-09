package ru.ezhov.ujatools;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Получаем текст с определенного адреса
 * <p>
 * @author ezhov_da
 */
public class HttpTextLoader
{
    private static final Logger LOG = Logger.getLogger(HttpFileLoader.class.getName());
    private String http;

    public HttpTextLoader(String http)
    {
        this.http = http;
    }

    public String getText() throws MalformedURLException, IOException
    {
        URL url = new URL(http);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        Scanner scanner = new Scanner(httpURLConnection.getInputStream(), "UTF-8");
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNextLine())
        {
            String s = scanner.nextLine();
            stringBuilder.append(s);
            stringBuilder.append("\n");
        }
        httpURLConnection.disconnect();
        scanner.close();
        return stringBuilder.toString();
    }

}
