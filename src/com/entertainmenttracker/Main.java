package com.entertainmenttracker;

import com.entertainmenttracker.ui.MainFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
                System.out.println("Entertainment Balance Tracker started!");
            }
        });
    }
}
