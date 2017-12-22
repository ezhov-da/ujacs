package ru.ezhov.appservice;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import ru.ezhov.ujatools.xmlobjects.RunnerApp;

/**
 *
 * @author ezhov_da
 */
public class StartAppTest
{

    public StartAppTest()
    {
    }

    @Test
    public void testSomeMethod()
    {
        StartApp startApp;
        try
        {
            startApp = new StartApp();
            startApp.addRunnerApp(new RunnerApp("1"));
            startApp.addRunnerApp(new RunnerApp("2"));
            startApp.addRunnerApp(new RunnerApp("3"));
            startApp.addRunnerApp(new RunnerApp("4"));
            startApp.save();
            startApp.load();
            List<RunnerApp> list = startApp.getListRunnerApp();
            System.out.println(Arrays.toString(list.toArray()));
        } catch (IOException ex)
        {
            Logger.getLogger(StartAppTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
