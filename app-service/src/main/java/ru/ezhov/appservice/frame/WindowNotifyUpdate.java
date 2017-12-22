package ru.ezhov.appservice.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import ru.ezhov.appservice.RunApp;
import ru.ezhov.appservice.TitleCreator;
import ru.ezhov.ujatools.DimensionProperties;
import ru.ezhov.ujatools.LocatedComponent;
import ru.ezhov.ujatools.PropertiesConst;
import ru.ezhov.ujatools.PropertyHttpHolder;

/**
 * Окно уведомление об обновлении приложения
 * <p>
 * @author ezhov_da
 */
public class WindowNotifyUpdate extends JWindow
{
    private static final Logger LOG = Logger.getLogger(WindowNotifyUpdate.class.getName());
    private String template;
    private JLabel labelText;
    private JButton update;
    private JButton later;
    private ActionUpdateRunApp actionUpdateRunApp;
    private boolean alreadyShow;

    public WindowNotifyUpdate()
    {
        template = PropertyHttpHolder.getInstance().getProperty(PropertiesConst.TEXT_NOTIFY_UPDATE_APP);
        labelText = new JLabel();
        labelText.setHorizontalAlignment(SwingConstants.CENTER);
        actionUpdateRunApp = new ActionUpdateRunApp(null, this);
        update = new JButton(actionUpdateRunApp);
        later = new JButton("Позже");
        later.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                alreadyShow = true;
                LOG.info("Пользователь нажал позже");
                setVisible(false);
            }
        });
        add(getPanelLabel(), BorderLayout.CENTER);
        add(getPanelButton(), BorderLayout.SOUTH);
        setSizeFromProperty();
        JPanel panel = (JPanel) getContentPane();
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setAlwaysOnTop(true);
    }

    @Override
    public void setVisible(boolean b)
    {
        LocatedComponent.locatedInTheCorner(this);
        super.setVisible(b);
    }

    private JPanel getPanelLabel()
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(labelText, BorderLayout.CENTER);
        return panel;
    }

    private JPanel getPanelButton()
    {
        JPanel panel = new JPanel();
        panel.add(update);
        panel.add(later);
        return panel;
    }

    public void setVisible(boolean b, RunApp runApp)
    {
        if (!alreadyShow)
        {
            actionUpdateRunApp.setRunApp(runApp);
            labelText.setText(String.format(template, TitleCreator.getTitleApp(runApp.getApplicationInstance())));
            setVisible(b);
        }
    }

    private void setSizeFromProperty()
    {
        String prop = PropertyHttpHolder.getInstance().getProperty(PropertiesConst.SIZE_WINDOW_UPDATE);
        Dimension dim;
        try
        {
            dim = DimensionProperties.getDimension(prop);
        } catch (Exception ex)
        {
            dim = new Dimension(400, 200);
        }
        setSize(dim);
    }

    public void setAlreadyShow(boolean alreadyShow)
    {
        this.alreadyShow = alreadyShow;
    }




}
