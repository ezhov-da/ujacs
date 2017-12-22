package ru.ezhov.ujatools.xmlobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author ezhov_da
 */
public class RunnerAppHolder
{
    private static final Logger LOG = Logger.getLogger(RunnerAppHolder.class.getName());
    private List<RunnerApp> runnerApps;

    public RunnerAppHolder()
    {
        runnerApps = new ArrayList<RunnerApp>();
    }

    public List<RunnerApp> getRunnerApps()
    {
        return runnerApps;
    }

    public void addRunnerApp(RunnerApp runnerApp)
    {
        runnerApps.add(runnerApp);
    }

}
