package ru.ezhov.ujatools.xmlobjects;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.junit.Test;

/**
 *
 * @author ezhov_da
 */
public class ApplicationInstanceTest
{

    public ApplicationInstanceTest()
    {
    }

    @Test
    public void testSomeMethod()
    {
        XStream xStream = new XStream(new DomDriver());
        ApplicationInstance applicationInstance = new ApplicationInstance();
        xStream.alias("applicationInstance", ApplicationInstance.class);
        applicationInstance.setNameAppUser("Планировщик/постановщик задач (базовая версия)");
        applicationInstance.setNameAppSystem("spt");
        applicationInstance.setVersion("0.55");
        applicationInstance.setFileZip("zip");
        applicationInstance.setHttpLoad("office6887:9090/apps/spt");
        applicationInstance.setListOfChanges("09.08.2016:Реализован модуль переноса задач");
        applicationInstance.setDescription("Данное приложение предназначено для планирования рабочего дня сотрудника, \n"
                + "			а так же контроля и учета задач сотрудника руководителем.");
        applicationInstance.setNews("Что то новое");
        //
        applicationInstance.setCommandRunApp("\"C:\\jre1.7.0_75_x32\\bin\\javaw\" -Xmx768m -jar \"E:\\renamer\\renamer.jar\"");
        String xml = xStream.toXML(applicationInstance);
        System.out.println(xml);
    }

}
