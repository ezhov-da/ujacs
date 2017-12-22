package ru.ezhov.appservice;

import ru.ezhov.ujatools.xmlobjects.ApplicationInstance;

/**
 *
 * @author ezhov_da
 */
public class TitleCreator
{
    public static synchronized String getTitleApp(ApplicationInstance applicationInstance)
    {
        return String.format(applicationInstance.getNameAppUser(), applicationInstance.getVersion());
    }
}
