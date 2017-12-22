package ru.ezhov.ujatools.xmlobjects;

import java.util.logging.Logger;

/**
 *
 * @author ezhov_da
 */
public class RunnerApp
{
    private static final Logger LOG = Logger.getLogger(RunnerApp.class.getName());
    private String nameAppSystem;

    public RunnerApp()
    {
    }


    public RunnerApp(String nameAppSystem)
    {
        this.nameAppSystem = nameAppSystem;
    }


    public static Logger getLOG()
    {
        return LOG;
    }

    public String getNameAppSystem()
    {
        return nameAppSystem;
    }
}
