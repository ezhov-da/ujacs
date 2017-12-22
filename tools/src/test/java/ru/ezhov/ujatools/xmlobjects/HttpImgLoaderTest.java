package ru.ezhov.ujatools.xmlobjects;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 *
 * @author ezhov_da
 */
public class HttpImgLoaderTest
{

    public HttpImgLoaderTest()
    {
    }

    @Test
    public void testSomeMethod()
    {
        HttpImgLoader httpImgLoader = new HttpImgLoader();
        try
        {
            httpImgLoader.loadImage("http://office6887:9090/icon/service_16x16.png");
        } catch (IOException ex)
        {
            Logger.getLogger(HttpImgLoaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
