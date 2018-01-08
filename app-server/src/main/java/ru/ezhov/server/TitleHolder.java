package ru.ezhov.server;

import com.sun.jna.platform.win32.Kernel32;

import java.util.logging.Logger;

public class TitleHolder {
    private static final Logger LOG = Logger.getLogger(TitleHolder.class.getName());

    public static String title() {
        return "Сервер обновления java приложений. PID[" + Kernel32.INSTANCE.GetCurrentProcessId() + "]";
    }
}
