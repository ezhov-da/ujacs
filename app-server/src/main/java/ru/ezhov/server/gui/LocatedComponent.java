package ru.ezhov.server.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;


/**
 * Класс, который распологает любой компонент в углу панели задач
 * <p>
 *
 * @author ezhov_da
 */
public class LocatedComponent {

//    public static void main(String[] args)
//    {
//        JFrame frame = new JFrame();
//        frame.setSize(400, 400);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        locatedInTheCorner(frame);
//        frame.setVisible(true);
//    }

    public static void locatedInTheCorner(Component component) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle rect = ge.getMaximumWindowBounds();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (rect.getHeight() < screenSize.height & rect.y != 0) {
            //System.out.println("UP");
            component.setLocation(screenSize.width - component.getWidth(), rect.y);
        } else if (rect.getHeight() < screenSize.height & rect.y == 0) {
            //System.out.println("DOWN");
            component.setLocation(screenSize.width - component.getWidth(), rect.height - component.getHeight());
        } else if (rect.getWidth() < screenSize.width & rect.x == 0) {
            //System.out.println("RIGHT");
            component.setLocation(rect.width - component.getWidth(), screenSize.height - component.getHeight());
        } else if (rect.getWidth() < screenSize.width & rect.x != 0) {
            //System.out.println("LEFT");
            component.setLocation(rect.x, screenSize.height - component.getHeight());
        }
    }
}
