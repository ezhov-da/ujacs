package ru.ezhov.appservice;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import ru.ezhov.appservice.managers.RunningApplicationManager;
import ru.ezhov.ujatools.PropertiesConst;
import ru.ezhov.ujatools.PropertyHttpHolder;
import ru.ezhov.ujatools.xmlobjects.ApplicationInstance;
import ru.ezhov.ujatools.xmlobjects.AppsHolder;
import ru.ezhov.ujatools.xmlobjects.AppsLoader;

/**
 * Первоначальная загрузка и регистрация приложений
 * @author ezhov_da
 */
public class LoadApplicationInstance
{
    private static final Logger LOG = Logger.getLogger(LoadApplicationInstance.class.getName());

    public List<ApplicationInstance> load() throws IOException, ClassNotFoundException
    {
        PropertyHttpHolder propertyHttpHolder = PropertyHttpHolder.getInstance();
        String httpApps = propertyHttpHolder.getProperty(PropertiesConst.HTTP_APPS);
        String aliasHolder = propertyHttpHolder.getProperty(PropertiesConst.XSTREAM_ALIAS_APP_HOLDER);
        String classHolder = propertyHttpHolder.getProperty(PropertiesConst.XSTREAM_ALIAS_APP_HOLDER_CLASS);
        String aliasApp = propertyHttpHolder.getProperty(PropertiesConst.XSTREAM_ALIAS_APP);
        String classApp = propertyHttpHolder.getProperty(PropertiesConst.XSTREAM_ALIAS_APP_CLASS);
        AppsLoader appsLoader = new AppsLoader(
                httpApps,
                aliasHolder,
                classHolder,
                aliasApp,
                classApp);
        AppsHolder appsHolder = appsLoader.getAppsHolder();
        List<ApplicationInstance> applicationInstances = appsHolder.getApplicationInstances();
        return applicationInstances;
    }

    public List<ApplicationInstance> loadAndRegisterApps() throws IOException, ClassNotFoundException
    {
        final List<ApplicationInstance> applicationInstances = load();
        final RunningApplicationManager runningApplicationManager = RunningApplicationManager.getInstance();
        for (ApplicationInstance applicationInstance : applicationInstances)
        {
            runningApplicationManager.registerApp(applicationInstance, PropertyHttpHolder.getInstance());
        }
        return applicationInstances;
    }
}
