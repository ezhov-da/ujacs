package ru.ezhov.ujatools.xmlobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Хранитель приложений
 * <p>
 *
 * @author ezhov_da
 */
public class AppsHolder {
    private static final Logger LOG = Logger.getLogger(AppsHolder.class.getName());

    private List<ApplicationInstance> applicationInstances;

    public AppsHolder() {
        applicationInstances = new ArrayList<ApplicationInstance>();
    }

    public List<ApplicationInstance> getApplicationInstances() {
        return applicationInstances;
    }

    public void addApplicationInstance(ApplicationInstance applicationInstance) {
        applicationInstances.add(applicationInstance);
    }
}
