package ru.ezhov.ujatools;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class JOptionPaneError {
    private static final Logger LOG = Logger.getLogger(JOptionPaneError.class.getName());


    public static synchronized void showErrorMsg(String text, Throwable ex) {
        LOG.log(Level.SEVERE, text, ex);
        JOptionPane.showMessageDialog(
                null,
                text + "\n" + ex.getMessage(),
                "Ошибка",
                JOptionPane.ERROR_MESSAGE);
    }
}
