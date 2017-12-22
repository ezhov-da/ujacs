package ru.ezhov.appserviceinstaller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import ru.ezhov.ujatools.DesktopPathHolder;
import ru.ezhov.ujatools.JOptionPaneError;
import ru.ezhov.ujatools.PropertyHttpHolder;

/**
 * Запускаем сразу приложение, если пользователь этого захотел
 * <p>
 * @author ezhov_da
 */
public class RunApp
{
    public static synchronized void run(String nameLnk)
    {
        try
        {
            if (Desktop.isDesktopSupported())
            {
                String pathToDesktop = DesktopPathHolder.getDesktopPath();
                if (pathToDesktop != null)
                {
                    Desktop.getDesktop().open(new File(pathToDesktop + File.separator + nameLnk + ".lnk"));
                } else
                {
                    JOptionPane.showMessageDialog(
                            null,
                            "Установка завершена, но не удалось получить путь к рабочему столу.\nПожалуйста, запустите приложение: \"" + nameLnk + "\" с рабочего стола."
                            ,"Внимание"
                            ,JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (IOException ex)
        {
            JOptionPaneError.showErrorMsg(PropertyHttpHolder.getInstance().getProperty("error.run.app.desktop.not.supported"), ex);
        }
    }
}
