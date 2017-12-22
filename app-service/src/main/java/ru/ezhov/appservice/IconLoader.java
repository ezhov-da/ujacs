package ru.ezhov.appservice;

import java.awt.Image;
import java.io.IOException;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import ru.ezhov.ujatools.xmlobjects.HttpImgLoader;

/**
 *
 * @author ezhov_da
 */
public class IconLoader
{
    private static final Logger LOG = Logger.getLogger(IconLoader.class.getName());


    public static synchronized Icon getIcon(String url) throws IOException
    {
        return new ImageIcon(getImage(url));
    }

    public static synchronized Image getImage(String url) throws IOException
    {
        HttpImgLoader httpImgLoader = new HttpImgLoader();
        Image imager = httpImgLoader.loadImage(url);
        return imager;
    }

}
