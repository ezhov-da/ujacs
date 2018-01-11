package ru.ezhov.updater.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import ru.ezhov.ujatools.*;

/**
 * Окно, которое показывается при запуске, обновлении и установке сервиса приложений
 * <p>
 *
 * @author ezhov_da
 */
public class WindowNotify extends JWindow {
    private static final Logger LOG = Logger.getLogger(WindowNotify.class.getName());
    private static WindowNotify windowNotify;
    private JLabel labelInfo;
    private JLabel labelLoad;
    private String textShow;
    private Icon iconLoader;
    private Image imageBackGroundPanel;
    private JPanel panel;

    public static WindowNotify getInstance() {
        if (windowNotify == null) {
            windowNotify = new WindowNotify();
        }
        return windowNotify;
    }

    public WindowNotify() {
        labelInfo = new JLabel();
        labelInfo.setHorizontalAlignment(SwingConstants.CENTER);
        labelLoad = new JLabel();
        labelLoad.setHorizontalAlignment(SwingConstants.CENTER);
        createPanel();
        add(panel, BorderLayout.CENTER);
        setAlwaysOnTop(true);
    }

    private void createPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imageBackGroundPanel != null) {
                    g.drawImage(imageBackGroundPanel, 0, 0, null);
                }
            }
        };
        panel.add(labelInfo);
        panel.add(labelLoad);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void loadInfo() {
        PropertyHttpHolder propertyHttpHolder = PropertyHttpHolder.getInstance();
        textShow = propertyHttpHolder.getProperty(PropertiesConst.TEXT_LOADER_APP_SERVICE);
        labelInfo.setText(textShow);
        try {
            //TODO: не найдены настройки, разобраться
            iconLoader = new ImageIcon(new URL(propertyHttpHolder.getProperty(PropertiesConst.HTTP_FILE_ICON_APP_SERVICE_LOADER)));
            labelLoad.setIcon(iconLoader);
        } catch (MalformedURLException ex) {
            LOG.log(Level.WARNING, "Не найдена иконка загрузки", ex);
        }
        try {
            imageBackGroundPanel = new ImageIcon(new URL(propertyHttpHolder.getProperty(PropertiesConst.HTTP_FILE_ICON_APP_SERVICE_BACKGROUND_LOADER))).getImage();
        } catch (MalformedURLException ex) {
            LOG.log(Level.SEVERE, "Не найден фон загрузчика", ex);
        }
        setSize(DimensionProperties.getDimension(propertyHttpHolder.getProperty(PropertiesConst.SIZE_LOADER_WINDOW)));
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            loadInfo();
            setLocationRelativeTo(null);
            panel.revalidate();
        }
        super.setVisible(b);
    }

    //Тестовый метод
    public static void main(String[] args) {
        try {
            new LoadHttpProperties().load();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    WindowNotify.getInstance().setVisible(true);
                }
            });
        } catch (IOException ex) {
            JOptionPaneError.showErrorMsg("Ошибка загрузки файла настроек", ex);
        }
    }
}
