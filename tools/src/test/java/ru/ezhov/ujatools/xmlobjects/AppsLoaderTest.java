package ru.ezhov.ujatools.xmlobjects;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ezhov_da
 */
public class AppsLoaderTest
{

    public AppsLoaderTest()
    {
    }

    @Test
    public void testSomeMethod()
    {
        AppsLoader appsLoader = new AppsLoader("http://office6887:9090/apps.xml", "apps", "ru.ezhov.ujatools.xmlobjects.AppsHolder", "applicationInstance", "ru.ezhov.ujatools.xmlobjects.ApplicationInstance");
        try
        {
            AppsHolder appsHolder = appsLoader.getAppsHolder();
            appsHolder.getApplicationInstances();
        } catch (IOException ex)
        {
            Logger.getLogger(AppsLoaderTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(AppsLoaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
