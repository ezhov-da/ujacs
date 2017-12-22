/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ezhov.appservice.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.JOptionPane;
import org.jsoup.Jsoup;
import ru.ezhov.appservice.RunApp;
import ru.ezhov.appservice.TitleCreator;
import ru.ezhov.appservice.managers.RunningApplicationManager;

/**
 * Класс, который проверяет перед закрытием приложения запущенные процессы
 * <p>
 * @author ezhov_da
 */
public class CloseAppActionListener implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String listRunApp = "";
        RunningApplicationManager runningApplicationManager = RunningApplicationManager.getInstance();
        Collection<RunApp> runApps = runningApplicationManager.getValues();
        for (RunApp runApp : runApps)
        {
            if (runApp.isRunning())
            {
                listRunApp = listRunApp + "\n- " + Jsoup.parse(TitleCreator.getTitleApp(runApp.getApplicationInstance())).text();
            }
        }
        if (!"".equals(listRunApp))
        {
            showQuestion(listRunApp);
        } else
        {
            System.exit(-1);
        }
    }

    private String getText(String listRunApp)
    {
        return "В данный момент запущены следующие приложения:\n" + listRunApp + "\nвсе они будут закрыты. Продолжить? ";
    }

    private void showQuestion(String listRunApp)
    {
        int q = JOptionPane.showConfirmDialog(
                null,
                getText(listRunApp), "Закрытие приложения", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (q == JOptionPane.YES_OPTION)
        {
            System.exit(-1);
        }
    }
}
