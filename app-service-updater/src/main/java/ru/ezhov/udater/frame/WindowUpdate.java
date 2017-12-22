package ru.ezhov.udater.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import ru.ezhov.udater.ProcessRegister;
import ru.ezhov.ujatools.JOptionPaneError;
import ru.ezhov.ujatools.LocatedComponent;
import ru.ezhov.ujatools.PropertyHttpHolder;

/**
 * Окно для отображения сообщения об обновлении приложения
 * <p>
 * @author ezhov_da
 */
public class WindowUpdate extends JWindow
{
    private static final Logger LOG = Logger.getLogger(WindowUpdate.class.getName());
    private static WindowUpdate windowUpdate;
    private JButton buttonYes;
    private JButton buttonNo;
    private JLabel label;

    public WindowUpdate()
    {
        buttonYes = new JButton("Да");
        buttonNo = new JButton("Нет");
        label = new JLabel();
        add(new PanelBasic());
        setAlwaysOnTop(true);
        initListeners();
    }

    private void initListeners()
    {
        buttonYes.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                LOG.log(Level.INFO, "Пользователь нажал да");
                try
                {
                    LOG.log(Level.INFO, "Прячем форму");
                    setVisible(false);
                    LOG.log(Level.INFO, "Запускаем закрытие сокета");
                    updateApp();
                } catch (Exception ex)
                {
                    String error = "Ошибка обновления";
                    Logger.getLogger(WindowUpdate.class.getName()).log(Level.SEVERE, error, ex);
                    JOptionPaneError.showErrorMsg(error, ex);
                }
            }
        });
        buttonNo.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
            }
        });
    }

    public static synchronized WindowUpdate getInstance()
    {
        if (windowUpdate == null)
        {
            windowUpdate = new WindowUpdate();
        }
        return windowUpdate;
    }

    @Override
    public void setVisible(boolean b)
    {
        if (b)
        {
            init();
        }
        super.setVisible(b);
    }

    private void init()
    {
        LOG.info("Показываем окно уведомление об обновлении");
        PropertyHttpHolder propertyHttpHolder = PropertyHttpHolder.getInstance();
        label.setText(propertyHttpHolder.getProperty("message.update.service.app.body"));
        //propertyHttpHolder.getProperty("message.update.service.app.title");
        pack();
        LocatedComponent.locatedInTheCorner(this);
    }

    private void updateApp() throws Exception
    {
        LOG.log(Level.INFO, "Пользователь захотел обновить приложение");
        ProcessRegister.getInstance().destroyProcess();
    }

    private class PanelBasic extends JPanel
    {

        public PanelBasic()
        {
            super(new BorderLayout());
            add(label, BorderLayout.CENTER);
            JPanel panel = new JPanel();
            panel.add(buttonYes);
            panel.add(buttonNo);
            add(panel, BorderLayout.SOUTH);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }

    }

}
