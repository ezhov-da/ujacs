/*==============================================================================
 * Данное приложение отвечает за первоначальную установку "Сервиса приложения"
 * Что необходимо знать:
 * 1. Все константы которые могут поменяться вынесены в файл с настройками на сервер
 *  http://office6887:9090/configs/config.properties
 * 2. Данное приложение автоматически упаковывается в один jar файл, даже если
 *  существуют подключенные библиотеки
 * 3. Результирующий jar упаковывается в exe
 * 4. Итоговый exe лежит в папке проекта
 =============================================================================*/
package ru.ezhov.appserviceinstaller;

import java.io.IOException;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

import ru.ezhov.appserviceinstaller.src.BasicFrame;
import ru.ezhov.ujatools.AppRussifier;
import ru.ezhov.ujatools.JOptionPaneError;
import ru.ezhov.ujatools.LoadHttpProperties;

/**
 * Приложение, которое устанавливает сервис приложений
 * <p>
 *
 * @author ezhov_da
 */
public class AppServiceInstaller {
    private static final Logger LOG = Logger.getLogger(AppServiceInstaller.class.getName());

    public static void main(String[] args) {
        try {
            AppRussifier.runRussifier();
            LoadHttpProperties loadHttpProperties = new LoadHttpProperties();
            loadHttpProperties.load();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    BasicFrame basicFrame = new BasicFrame();
                    basicFrame.setVisible(true);
                }
            });
        } catch (IOException ex) {
            String s = "Не удалось запустить установщик";
            JOptionPaneError.showErrorMsg(s, ex);
        }
    }
}
