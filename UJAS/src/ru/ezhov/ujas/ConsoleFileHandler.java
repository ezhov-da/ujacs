package ru.ezhov.ujas;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * класс. который выводит лог и в консоль и в файл
 * <p>
 * @author ezhov_da
 */
public class ConsoleFileHandler extends ConsoleHandler {

    private static final Logger LOG = Logger.getLogger(ConsoleFileHandler.class.getName());
    private FileHandler fileHandler;

    public ConsoleFileHandler() {
        try {
            fileHandler = new FileHandler();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void close() {
        super.close();
        if (fileHandler != null) {
            fileHandler.close();
        }
    }

    @Override
    public void publish(LogRecord record) {
        super.publish(record);
        if (fileHandler != null) {
            fileHandler.publish(record);
        }
    }

}
