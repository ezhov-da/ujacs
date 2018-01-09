package ru.ezhov.ujatools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class HttpFileLoader {
    private static final Logger LOG = Logger.getLogger(HttpFileLoader.class.getName());
    private String urlText;
    private String fileToSave;

    public HttpFileLoader(String urlText, String fileToSave) {
        this.urlText = urlText;
        this.fileToSave = fileToSave;
    }

    public void load() throws MalformedURLException, IOException {
        URL url = new URL(urlText);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(fileToSave)));
        BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
        try {
            byte[] byteIn = new byte[65536];
            int b;
            while ((b = bufferedInputStream.read(byteIn)) != -1) {
                bufferedOutputStream.write(byteIn, 0, b);
            }
            bufferedOutputStream.flush();
        } finally {
            bufferedOutputStream.close();
            bufferedInputStream.close();
        }
        httpURLConnection.disconnect();
    }

}
