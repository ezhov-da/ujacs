/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ezhov.ujatools.xmlobjects;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.IOException;
import java.util.logging.Logger;

import ru.ezhov.ujatools.HttpTextLoader;

/**
 * Класс по загрузке списка приложений
 * <p>
 *
 * @author ezhov_da
 */
public class AppsLoader {
    private static final Logger LOG = Logger.getLogger(AppsHolder.class.getName());
    private String urlApps;
    private String aliasAppHolder;
    private String clazzAppHolder;
    private String aliasApp;
    private String clazzApp;


    public AppsLoader(String urlApps,
                      String aliasAppHolder,
                      String clazzAppHolder,
                      String aliasApp,
                      String clazzApp) {
        this.urlApps = urlApps;
        this.aliasAppHolder = aliasAppHolder;
        this.clazzAppHolder = clazzAppHolder;
        this.aliasApp = aliasApp;
        this.clazzApp = clazzApp;
    }

    public synchronized AppsHolder getAppsHolder() throws IOException, ClassNotFoundException {
        HttpTextLoader httpTextLoader = new HttpTextLoader(urlApps);
        String text = httpTextLoader.getText();
        XStream stream = new XStream(new DomDriver());
        stream.alias(aliasAppHolder, Class.forName(clazzAppHolder));
        stream.alias(aliasApp, Class.forName(clazzApp));
        AppsHolder appsHolder = (AppsHolder) stream.fromXML(text);
        return appsHolder;
    }
}
