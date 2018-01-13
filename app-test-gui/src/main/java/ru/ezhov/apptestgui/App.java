package ru.ezhov.apptestgui;

import javax.swing.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Тестовое приложение");
                JLabel label = new JLabel("Привет, я тестовое приложение ;)");
                label.setHorizontalAlignment(SwingConstants.CENTER);
                frame.add(label);

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 400);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
