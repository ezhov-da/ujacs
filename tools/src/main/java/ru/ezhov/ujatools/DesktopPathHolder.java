package ru.ezhov.ujatools;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Получаем путь к рабочему столу
 */
public class DesktopPathHolder {
    private static final Logger LOG = Logger.getLogger(DesktopPathHolder.class.getName());

    public static synchronized String getDesktopPath() {
        String deString = null;
        File desktop = new File(System.getProperty("user.home"), "Desktop");
        if (desktop.exists()) {
            deString = desktop.getAbsolutePath();
        } else {
            desktop = new File(System.getProperty("user.home"), "Рабочий стол"); //для XP проверка
            if (desktop.exists()) {
                deString = desktop.getAbsolutePath();
            }
        }
        LOG.log(Level.INFO, "Путь к файлу запуска:{0}", deString);
        return deString;
    }

    public static void main(String[] args) {
        System.out.println(DesktopPathHolder.getDesktopPath());
    }
}
