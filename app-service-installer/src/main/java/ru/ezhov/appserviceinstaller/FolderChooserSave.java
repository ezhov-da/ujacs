package ru.ezhov.appserviceinstaller;

import java.io.File;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import ru.ezhov.ujatools.PropertyHttpHolder;

/**
 * Выбираем папку для установки сервиса приложений
 * <p>
 * @author ezhov_da
 */
public class FolderChooserSave
{
    private static final Logger LOG = Logger.getLogger(FolderChooserSave.class.getName());

    public File getSelectedDirectory()
    {
        JFileChooser fileChooser = new JFileChooser(new File("E:\\"));
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooser.setDialogTitle(
                PropertyHttpHolder.getInstance().getProperty("choose.folder.for.install.service.app")
        );
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        int i = fileChooser.showSaveDialog(null);
        return i == JFileChooser.APPROVE_OPTION ? fileChooser.getSelectedFile() : null;
    }
}
