/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.ezhov.appservice.frame;

import java.awt.event.ActionEvent;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import ru.ezhov.appservice.RunApp;

/**
 *
 * @author ezhov_da
 */
public class ActionApp extends AbstractAction
{
    private static final Logger LOG = Logger.getLogger(ActionInstall.class.getName());
    protected RunApp runApp;

    public ActionApp(RunApp runApp)
    {
        this.runApp = runApp;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}