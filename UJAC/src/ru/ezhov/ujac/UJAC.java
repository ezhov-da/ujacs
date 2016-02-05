package ru.ezhov.ujac;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author RRNDeonisiusEZH
 */
public class UJAC {

    private static final Logger LOG = Logger.getLogger(UJAC.class.getName());

    static {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream(new File("logger.properties")));
        } catch (Exception ex) {
            Logger.getLogger(UJAC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        new UJAC().startUjac();
    }

    private void startUjac() {
        try {
            //для ожидания
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(UJAC.class.getName()).log(Level.SEVERE, null, ex);
        }
        LOG.info("запускаем загрузку приложения");
        SocketLoadApp socketLoadApp = new SocketLoadApp();
        socketLoadApp.start();
        LOG.info("загрузка приложения завершена");
    }
}
