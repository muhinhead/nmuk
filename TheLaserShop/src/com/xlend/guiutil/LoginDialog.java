/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.guiutil;

import com.jtattoo.plaf.aero.AeroLookAndFeel;
import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import com.jtattoo.plaf.noire.NoireLookAndFeel;
import com.xlend.AboutDialog;
import com.xlend.TheLaserShop;
import com.xlend.dbutil.DbAssistant;
import com.xlend.dbutil.ExchangeFactory;
import com.xlend.guiutil.GeneralUtils;
import com.xlend.guiutil.ImagePanel;
import com.xlend.guiutil.Java2sAutoComboBox;
import com.xlend.guiutil.PopupDialog;
import com.xlend.guiutil.TexturedPanel;
import com.xlend.guiutil.ToolBarButton;
import com.xlend.orm.Mats_usr;
//import com.xlend.orm.User;
//import com.xlend.tt.AboutDialog;
//import com.xlend.tt.TreatmentTrack;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author nick
 */
public class LoginDialog extends PopupDialog {

    private static final String BACKGROUNDIMAGE = "Login.png";
    public static final String LASTLOGIN = "LastLogin";

    public static boolean isOkPressed() {
        return okPressed;
    }
//    private final String NMSOFTWARE = "Nick Mukhin (c)2013";
    private JPanel controlsPanel;
    private Java2sAutoComboBox loginField;
    private JPasswordField pwdField;
    private static boolean okPressed = false;

    private class LayerPanel extends JLayeredPane {

        private LayerPanel() {
            super();
        }

        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            controlsPanel.setBounds(getBounds());
        }
    }
    
    public LoginDialog(Object obj) {
        super(null, "Логин", obj);
    }

    @Override
    protected void fillContent() {
        //XlendWorks.setWindowIcon(this, "Xcost.png");
        okPressed = false;
        buildMenu();
        try {
            String theme = ExchangeFactory.getPropLogEngine().getProps().getProperty("LookAndFeel", "com.nilo.plaf.nimrod.NimRODLookAndFeel");
            if (theme.indexOf("HiFi") > 0) {
                HiFiLookAndFeel.setTheme("Default", "", GeneralUtils.NMSOFTWARE);
            } else if (theme.indexOf("Noire") > 0) {
                NoireLookAndFeel.setTheme("Default", "", GeneralUtils.NMSOFTWARE);
            } else if (theme.indexOf("Bernstein") > 0) {
                BernsteinLookAndFeel.setTheme("Default", "", GeneralUtils.NMSOFTWARE);
            } else if (theme.indexOf("Aero") > 0) {
                AeroLookAndFeel.setTheme("Green", "", GeneralUtils.NMSOFTWARE);
            }
            UIManager.setLookAndFeel(theme);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception ie) {
                //XlendWorks.log(ie);
            }
        }
        loginField = new Java2sAutoComboBox(DbAssistant.loadAllLogins());
        loginField.setEditable(true);
        loginField.setStrict(false);
        pwdField = new JPasswordField(20);
        controlsPanel = new JPanel(new BorderLayout());
        JPanel main = new TexturedPanel(BACKGROUNDIMAGE);
        controlsPanel.add(main, BorderLayout.CENTER);
        ImagePanel img = new ImagePanel(GeneralUtils.loadImage(BACKGROUNDIMAGE, getClass()));
        addNotify();
        Insets insets = getInsets();
        int dashWidth = img.getWidth();
        int dashHeight = img.getHeight();
        int yShift = 45;
        int xShift = 30;

        this.setMinimumSize(new Dimension(dashWidth + insets.left + insets.right,
                dashHeight + insets.top + insets.bottom + 20));
        LayerPanel layers = new LayerPanel();
        layers.add(controlsPanel, JLayeredPane.DEFAULT_LAYER);
        getContentPane().add(layers, BorderLayout.CENTER);

        loginField.setBounds(129, 46, 225, 26);
        loginField.setBorder(null);
        main.add(loginField);
        pwdField.setBounds(129, 91, 225, 26);
        pwdField.setBorder(null);
        main.add(pwdField);

        String lockPng = "Lock.png";
        JButton okButton = new ToolBarButton(lockPng, true);
        img = new ImagePanel(GeneralUtils.loadImage(lockPng, getClass()));

        okButton.setBounds(dashWidth - img.getWidth() - xShift, dashHeight - img.getHeight() - yShift,
                img.getWidth(), img.getHeight());

        main.add(okButton);
        okButton.addActionListener(okButtonListener());

        Preferences userPref = Preferences.userRoot();
        String pwdmd5 = userPref.get("DEVPWD", "");
        pwdField.setText(pwdmd5);
        if (pwdmd5.trim().length() > 0) {
            loginField.setSelectedItem(ExchangeFactory.getPropLogEngine().readProperty(LASTLOGIN, ""));
        }
        getRootPane().setDefaultButton(okButton);
        setResizable(false);
    }

    private void buildMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu m = new JMenu("Настройки");
        m.add(new JMenuItem(new AbstractAction("Соединение с БД") {
            @Override
            public void actionPerformed(ActionEvent e) {
                TheLaserShop.configureConnection();
            }
        }));
        m.add(GeneralUtils.appearanceMenu("Тема оформления", this));
        bar.add(m);
//        m.add(new JMenuItem(new AbstractAction("Export data") {
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                String dumpFile = XlendWorks.makeBackup();
//                if (dumpFile != null) {
//                    JOptionPane.showMessageDialog(null, "Database dump exported to "
//                            + dumpFile,
//                            "Backup succeed", JOptionPane.INFORMATION_MESSAGE);
//                }
//            }
//        }));
//        bar.add(m);
        bar.add(new JMenuItem(new AbstractAction("О программе") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AboutDialog();
            }
        }));

        setJMenuBar(bar);
    }

    private ActionListener okButtonListener() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okPressed = false;
                try {
                    Mats_usr u = DbAssistant.findUser((String) loginField.getSelectedItem(), new String(pwdField.getPassword()));
                    if (u != null) {
                        TheLaserShop.setCurrentUser(u);
                        okPressed = true;
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Доступ закрыт", "Увы!", JOptionPane.ERROR_MESSAGE);
                        loginField.requestFocus();
                    }
                } catch (RemoteException ex) {
                    ExchangeFactory.getPropLogEngine().logAndShowMessage(ex);
                }
            }
        };
    }
}
