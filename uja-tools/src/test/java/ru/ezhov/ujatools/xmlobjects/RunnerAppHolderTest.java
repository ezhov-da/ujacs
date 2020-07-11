package ru.ezhov.ujatools.xmlobjects;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.junit.Test;

public class RunnerAppHolderTest {

    public RunnerAppHolderTest() {
    }

    @Test
    public void testSomeMethod() {
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("runnerAppHolder", RunnerAppHolder.class);
        xStream.alias("runnerApp", RunnerApp.class);
        RunnerAppHolder runnerAppHolder = new RunnerAppHolder();
        RunnerApp runnerApp;
        runnerApp = new RunnerApp("1");
        runnerAppHolder.addRunnerApp(runnerApp);
        runnerApp = new RunnerApp("2");
        runnerAppHolder.addRunnerApp(runnerApp);
        runnerApp = new RunnerApp("3");
        runnerAppHolder.addRunnerApp(runnerApp);
        runnerApp = new RunnerApp("4");
        runnerAppHolder.addRunnerApp(runnerApp);
        String xml = xStream.toXML(runnerAppHolder);
        System.out.println(xml);
    }

}
