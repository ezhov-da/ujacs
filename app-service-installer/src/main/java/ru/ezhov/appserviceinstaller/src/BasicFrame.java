package ru.ezhov.appserviceinstaller.src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import ru.ezhov.appserviceinstaller.FolderChooserSave;
import ru.ezhov.appserviceinstaller.Installer;
import ru.ezhov.ujatools.PropertyHttpHolder;
import ru.ezhov.ujatools.DimensionProperties;

/**
 * Окно установки
 * <p>
 * @author ezhov_da
 */
public class BasicFrame extends JFrame
{
    private static final Logger LOG = Logger.getLogger(BasicFrame.class.getName());
    private PropertyHttpHolder propertyHttpHolder = PropertyHttpHolder.getInstance();
    private JLabel labelTitle;
    private JLabel labelBody;
    private FilePanel filePanel;
    private JButton buttonInstall;

    public BasicFrame()
    {
        add(new BasicPanel(), BorderLayout.CENTER);
        setSize(DimensionProperties.getDimension(propertyHttpHolder.getProperty("install.size.frame")));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle(propertyHttpHolder.getProperty("title.install"));
    }

    private class BasicPanel extends JPanel
    {
        private TitlePanel titlePanel;

        public BasicPanel()
        {
            super(new BorderLayout());
            titlePanel = new TitlePanel();
            filePanel = new FilePanel();
            buttonInstall = new JButton(propertyHttpHolder.getProperty("button.install"));
            add(titlePanel, BorderLayout.NORTH);
            add(filePanel, BorderLayout.CENTER);
            JPanel panel = new JPanel();
            panel.add(buttonInstall);
            add(panel, BorderLayout.SOUTH);
            buttonInstall.setEnabled(false);
            buttonInstall.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        Installer installer = new Installer(filePanel.getDirectoryForInstall());
                        installer.install();
                    } catch (Exception ex)
                    {
                        String s = "Не удалось произвести установку";
                        LOG.log(Level.SEVERE, s, ex);
                        JOptionPane.showMessageDialog(
                                null,
                                s + ": " + ex.getMessage(),
                                propertyHttpHolder.getProperty("error.app.already.install.title"),
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });
        }
    }

    private class TitlePanel extends JPanel
    {
        private Image image;

        public TitlePanel()
        {
            super(new FlowLayout(FlowLayout.LEFT));
            loadImage();
            labelTitle = new JLabel(propertyHttpHolder.getProperty("title.install.html"));
            add(labelTitle);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }

        @Override
        public void paintComponent(Graphics g)
        {
            g.drawImage(image, 0, 0, null);
        }


        private void loadImage()
        {
            try
            {
                image = ImageIO.read(new URL(propertyHttpHolder.getProperty("url.background.title.install.service")));
            } catch (IOException ex)
            {
                Logger.getLogger(BasicFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private class FilePanel extends JPanel
    {
        private File directoryForInstall;
        private JTextField textField;
        private JButton buttonSelectFile;

        public FilePanel()
        {
            super(new GridBagLayout());
            labelBody = new JLabel(propertyHttpHolder.getProperty("body.text.install"));
            labelBody.setHorizontalAlignment(SwingConstants.CENTER);
            textField = new JTextField();
            textField.setEditable(false);
            buttonSelectFile = new JButton("...");
            buttonSelectFile.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    directoryForInstall = new FolderChooserSave().getSelectedDirectory();
                    if (directoryForInstall == null || !directoryForInstall.exists())
                    {
                        JOptionPane.showMessageDialog(
                                null,
                                propertyHttpHolder.getProperty("error.install.cancel.body"),
                                propertyHttpHolder.getProperty("error.install.cancel.title"),
                                JOptionPane.INFORMATION_MESSAGE);
                        buttonInstall.setEnabled(false);
                    } else
                    {
                        buttonInstall.setEnabled(true);
                        textField.setText(directoryForInstall.getAbsolutePath());
                    }
                }
            });
            Insets insets = new Insets(5, 5, 5, 5);
            add(labelBody, new GridBagConstraints(0, 0, 1, 1, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets, 0, 0));
            add(textField, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));
            add(buttonSelectFile, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.NONE, insets, 0, 0));
        }

        public File getDirectoryForInstall()
        {
            return directoryForInstall;
        }



    }

}
