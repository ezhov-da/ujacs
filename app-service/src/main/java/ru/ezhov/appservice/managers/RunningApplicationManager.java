package ru.ezhov.appservice.managers;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.ezhov.ujatools.PropertyHttpHolder;
import ru.ezhov.ujatools.xmlobjects.ApplicationInstance;
import ru.ezhov.appservice.RunApp;
import ru.ezhov.appservice.UserNameHolder;



/**
 * Это менеджер, который отвечает за запуск и остановку приложений,
 * то есть, если приложение запускается, его необходимо регистрировать
 * <p>
 * @author ezhov_da
 */
public class RunningApplicationManager
{
    private static final Logger LOG = Logger.getLogger(RunningApplicationManager.class.getName());
    private static RunningApplicationManager runningApplicationManager;
    private Map<String, RunApp> mapApp;

    private RunningApplicationManager()
    {
        mapApp = new ConcurrentHashMap();
    }

    public static RunningApplicationManager getInstance()
    {
        if (runningApplicationManager == null)
        {
            runningApplicationManager = new RunningApplicationManager();
        }
        return runningApplicationManager;
    }

    //регистрируем приложения, которые доступны
    public synchronized void registerApp(ApplicationInstance applicationInstance, PropertyHttpHolder propertyHttpHolder)
    {
        CheckGrantUser checkGrantUser = new CheckGrantUser(applicationInstance);
        //если есть в разрешенном, и нет в запрещенном
        if (checkGrantUser.containsGoodList() && !checkGrantUser.containsBadList())
        {
            if (!containsApp(applicationInstance))
            {
                //если такого приложения не было, тогда добавляем новое
                RunApp runApp = new RunApp(applicationInstance);
                mapApp.put(applicationInstance.getNameAppSystem(), runApp);
            } else
            {
                //если было, тогда просто заменяем информацию о нем
                RunApp runApp = mapApp.get(applicationInstance.getNameAppSystem());
                runApp.setApplicationInstance(applicationInstance);
            }
        } else
        {
            mapApp.remove(applicationInstance.getNameAppSystem());
        }
    }

    //есть ли приложение
    private boolean containsApp(ApplicationInstance applicationInstance)
    {
        return mapApp.containsKey(applicationInstance.getNameAppSystem());
    }

    //получаем приложение для дальнейшей работы
    public RunApp getRunApp(String nameAppSystem)
    {
        return mapApp.get(nameAppSystem);
    }

    public Set<Entry<String, RunApp>> getEntrySet()
    {
        return mapApp.entrySet();
    }

    public Collection<RunApp> getValues()
    {
        LOG.log(Level.INFO, "Получаем список зарегистрированных компонентов");
        return mapApp.values();
    }

    public Set<String> keySet()
    {
        return mapApp.keySet();
    }

    //Класс, который отвечает за права пользователя
    private class CheckGrantUser
    {
        private ApplicationInstance applicationInstance;

        public CheckGrantUser(ApplicationInstance applicationInstance)
        {
            this.applicationInstance = applicationInstance;
        }

        public boolean containsGoodList()
        {
            List<String> list = applicationInstance.getListUsersGood();
            return checkList(list);
        }

        public boolean containsBadList()
        {
            List<String> list = applicationInstance.getListUsersBad();
            return checkList(list);
        }

        private boolean checkList(List<String> list)
        {
            for (String s : list)
            {
                if (isStar(s) || UserNameHolder.equalsUserName(s))
                {
                    return true;
                }
            }
            return false;
        }

        private boolean isStar(String username)
        {
            return "*".equals(username);
        }

    }

}
