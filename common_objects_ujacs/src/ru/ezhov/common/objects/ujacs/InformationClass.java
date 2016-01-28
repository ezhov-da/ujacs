package ru.ezhov.common.objects.ujacs;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Данный класс предназначен для отправки информации о том, какой инструмент
 * требует обновления
 * <p>
 * @author ezhov_da
 */
@XStreamAlias("infoClass")
public class InformationClass {

    private final String nameApplication;
    private String date;
    private String userName;
    private final Commands commands;
    private String version;

    public InformationClass(String nameApplication, Commands commands) {
        this.nameApplication = nameApplication;
        this.commands = commands;
    }

    public String getNameApplication() {
        return nameApplication;
    }

    public String getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Commands getCommands() {
        return commands;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return nameApplication;
    }

}
