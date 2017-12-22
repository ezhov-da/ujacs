package ru.ezhov.appservice.frame;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import ru.ezhov.appservice.IconLoader;
import ru.ezhov.ujatools.PropertiesConst;
import ru.ezhov.ujatools.PropertyHttpHolder;
import ru.ezhov.ujatools.DimensionProperties;
import ru.ezhov.ujatools.LocatedComponent;
import ru.ezhov.ujatools.WindowService;

/**
 * Это базовое окно для сервиса приложений
 * <p>
 * @author ezhov_da
 */

public class BasicFrame extends JFrame
{
    private static final Logger LOG = Logger.getLogger(BasicFrame.class.getName());
    private static BasicFrame basicFrame;

    private BasicFrame()
    {
        BasicPanel basicPanel = BasicPanel.getInstance();
        basicPanel.load();
        add(new JScrollPane(basicPanel));
        setSizeFromProperty();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new ListenerCloseFrame());
        setIcon();
        setTitle();
        setResizable(false);
        LocatedComponent.locatedInTheCorner(this);
        WindowService.createEscActionOnWindows(getRootPane(), this);
        WindowService.createListenerForRepaint(this);
    }

    public static BasicFrame getInstance()
    {
        if (basicFrame == null)
        {
            basicFrame = new BasicFrame();
        }
        return basicFrame;
    }

    private void setIcon()
    {
        String prop = PropertyHttpHolder.getInstance().getProperty(PropertiesConst.ICON_BASIC);
        try
        {
            Image image = IconLoader.getImage(prop);
            setIconImage(image);
        } catch (IOException ex)
        {
            Logger.getLogger(ActionRun.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setTitle()
    {
        String title = PropertyHttpHolder.getInstance().getProperty(PropertiesConst.BASIC_TITLE_NAME);
        setTitle(title);
    }

    private void setSizeFromProperty()
    {
        String prop = PropertyHttpHolder.getInstance().getProperty(PropertiesConst.SIZE_SERVICE_APP_WINDOW);
        Dimension dim;
        try
        {
            dim = DimensionProperties.getDimension(prop);
        } catch (Exception ex)
        {
            dim = new Dimension(400, 300);
        }
        setSize(dim);
    }


}
