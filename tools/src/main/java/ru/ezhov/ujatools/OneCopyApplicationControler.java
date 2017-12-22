package ru.ezhov.ujatools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Контроль открытия одного экземпляра приложения
 * <p>
 *
 * @author ezhov_da
 */
public class OneCopyApplicationControler {
    private static final Logger LOG = Logger.getLogger(OneCopyApplicationControler.class.getName());

    public static void check(File file) throws FileNotFoundException {
        LOG.info("Проверка открытого экземпляра приложения ");
        if (!lock(file)) {
            LOG.log(Level.INFO, "Приложение уже открыто");
            JOptionPane.showMessageDialog(null, "Экземпляр приложения уже запущен.", "Внимание", JOptionPane.INFORMATION_MESSAGE);
            System.exit(1000);
        }
    }

    private static boolean lock(File lockFile) throws FileNotFoundException {
        if (lockFile == null) {
            throw new IllegalArgumentException("path don't be null.");
        }
        try {
            final FileLock lock = new FileOutputStream(lockFile).getChannel().tryLock();
            if (lock != null) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                if (lock.isValid()) {
                                    Thread.sleep(Long.MAX_VALUE);
                                }
                            } catch (InterruptedException ex) {
                            }
                        }
                    }
                });
                thread.setDaemon(true);
                thread.start();
            }
            LOG.log(Level.INFO, "Проверка открытого экземпляра приложения: {0}", (lock != null));
            return lock != null;
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Ошибка проверки наличия открытого экземпляра класса ", ex);
        }
        return true;
    }

}
