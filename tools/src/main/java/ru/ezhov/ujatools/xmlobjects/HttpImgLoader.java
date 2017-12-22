package ru.ezhov.ujatools.xmlobjects;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author ezhov_da
 */
public class HttpImgLoader
{
    private static final Logger LOG = Logger.getLogger(HttpImgLoader.class.getName());

    public Image loadImage(String urlImg) throws IOException
    {
        URL url = new URL(urlImg);
        Image image = ImageIO.read(url);
        return image;
    }
}
