package ru.ezhov.ujatools.xmlobjects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ezhov_da
 */
public class ApplicationInstance
{
    private String nameAppUser;
    private String nameAppSystem;
    private String version;
    private String fileZip;
    private String httpLoad;
    private String listOfChanges;
    private List<String> listUsersGood;
    private List<String> listUsersBad;
    private String description;
    private String news;
    private String commandRunApp;
    private String httpIconImage;

    public ApplicationInstance()
    {
        listUsersGood = new ArrayList<String>();
        listUsersBad = new ArrayList<String>();
    }

    public String getNameAppUser()
    {
        return nameAppUser;
    }

    public String getNameAppSystem()
    {
        return nameAppSystem;
    }

    public String getVersion()
    {
        return version;
    }

    public String getFileZip()
    {
        return fileZip + "_" + version + ".zip";
    }

    public String getHttpLoad()
    {
        return httpLoad;
    }

    public String getListOfChanges()
    {
        return listOfChanges;
    }

    public List<String> getListUsersGood()
    {
        return listUsersGood;
    }

    public List<String> getListUsersBad()
    {
        return listUsersBad;
    }

    public String getDescription()
    {
        return description;
    }

    public String getNews()
    {
        return news;
    }

    public String getCommandRunApp()
    {
        return commandRunApp;
    }

    public String getHttpIconImage()
    {
        return httpIconImage;
    }

    public void setNameAppUser(String nameAppUser)
    {
        this.nameAppUser = nameAppUser;
    }

    public void setNameAppSystem(String nameAppSystem)
    {
        this.nameAppSystem = nameAppSystem;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public void setFileZip(String fileZip)
    {
        this.fileZip = fileZip;
    }

    public void setHttpLoad(String httpLoad)
    {
        this.httpLoad = httpLoad;
    }

    public void setListOfChanges(String listOfChanges)
    {
        this.listOfChanges = listOfChanges;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setNews(String news)
    {
        this.news = news;
    }

    public void setCommandRunApp(String commandRunApp)
    {
        this.commandRunApp = commandRunApp;
    }

    public void setHttpIconImage(String httpIconImage)
    {
        this.httpIconImage = httpIconImage;
    }

    public String getNameUserFull()
    {
        return String.format(nameAppUser, version);
    }
}
