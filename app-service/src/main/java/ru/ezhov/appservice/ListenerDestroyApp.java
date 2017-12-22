package ru.ezhov.appservice;

/**
 * Слушатель, который говорит о том, что приложение закрылось
 * <p>
 * @author ezhov_da
 */
public interface ListenerDestroyApp
{
    void destroyApp(RunApp runApp);
}
