package ru.ezhov.appservice;

import java.util.logging.Logger;

/**
 *
 * @author ezhov_da
 */
public class UserNameHolder
{
    private static final Logger LOG = Logger.getLogger(UserNameHolder.class.getName());

    public static synchronized boolean equalsUserName(String username)
    {
        String un = System.getProperty("user.name");
        return un.toLowerCase().equals(username.toLowerCase());
    }
}
