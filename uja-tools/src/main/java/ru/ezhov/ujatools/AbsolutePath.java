package ru.ezhov.ujatools;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Данный класс позволяет получить абсолютный путь к папке, где лежит исполняемый файл
 * <p>
 *
 * @author ezhov_da
 */
public class AbsolutePath {
    /**
     * Получаем абсолютный путь к папке, в которой лежит jar файл передаваемого класса
     * <p>
     *
     * @param clazzFromLoadJar - класс для получения пути к jar файлу
     *                         <p>
     * @return абсолютный путь
     * <p>
     * @throws Exception
     */
    public static synchronized String getAbsolutePath(Class clazzFromLoadJar)
            throws Exception {
        URL url = clazzFromLoadJar.getProtectionDomain().getCodeSource().getLocation();
        File f = new File(url.toURI());
        String decoder = URLDecoder.decode(f.getParentFile().getAbsolutePath(), "UTF-8");
        return decoder;
    }
}
