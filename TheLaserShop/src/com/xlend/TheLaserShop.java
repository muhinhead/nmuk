/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.xlend;

import com.jidesoft.plaf.LookAndFeelFactory;
import com.xlend.dbutil.ExchangeFactory;
import com.xlend.dbutil.IMessageSender;
import com.xlend.guiutil.ConfigEditor;
import com.xlend.guiutil.GeneralUtils;
import com.xlend.guiutil.LoginDialog;
import com.xlend.guiutil.LookupDialog;
import com.xlend.guiutil.PropLogEngine;
import com.xlend.orm.Mats_usr;
import java.io.File;
import java.util.logging.Level;
import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;

/**
 *
 * @author Nick Mukhin
 */
public class TheLaserShop {

    public static final String defaultServerIP = "127.0.0.1"; //"18.208.137.87";
    private static IMessageSender exchanger;
    private static String homeDir;
    static String version = "0.1";
    private static MainFrame mainFrame;
    private static Mats_usr currentUser;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (!initPropAndLog()) {
            quit(1);
        }
        try {
            String current = new java.io.File(".").getCanonicalPath();
            System.out.println("Current dir:" + current);
            homeDir = System.getProperty("user.home") + File.separator;
            System.out.println("Current dir using System:" + homeDir);
        } catch (Exception ex) {
            ExchangeFactory.getPropLogEngine().logAndShowMessage(ex);
        }
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        String serverIP = ExchangeFactory.getPropLogEngine().readProperty("ServerAddress", defaultServerIP);
        try {
            IMessageSender exc = ExchangeFactory.getExchanger(
                    ExchangeFactory.getRMIkey(serverIP, PropLogEngine.getOwner().getName()),
                    ExchangeFactory.getPropLogEngine().getProps());
            if (exc == null) {
                configureConnection();
            } else {
                setExchanger(exc);
            }
        } catch (Exception ex) {
            ExchangeFactory.getPropLogEngine().logAndShowMessage(ex);
            if ((serverIP = serverSetup("Check server settings")) == null) {
                quit(1);
            } else {
                ExchangeFactory.getPropLogEngine().saveProps();
            }
        }
        if (getExchanger() != null) {
            ExchangeFactory.getPropLogEngine().saveProps();
            try {
                if (login()) {
                    showFleet();
                } else {
                    ExchangeFactory.getPropLogEngine().saveProps();
                    GeneralUtils.quit(1);
                }
            } catch (Exception ex) {
                ExchangeFactory.getPropLogEngine().logAndShowMessage(ex);
            }
        }
    }
    
    public static void showFleet() {
        if (mainFrame == null) {
            mainFrame = new MainFrame(exchanger);
        } else {
            try {
                mainFrame.setLookAndFeel(ExchangeFactory.getPropLogEngine().readProperty("LookAndFeel",
                        UIManager.getSystemLookAndFeelClassName()));
            } catch (Exception ex) {
            }
            mainFrame.setVisible(true);
        }
    }

    private static boolean initPropAndLog() {
        PropLogEngine.setOwner(TheLaserShop.class);
        try {
            ExchangeFactory.setPropLogEngine(PropLogEngine.getInstance(Level.ALL));
        } catch (PropLogEngine.NoOwnerException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean login() {
        try {
            new LoginDialog(getExchanger());
            return LoginDialog.isOkPressed();
        } catch (Throwable ee) {
            //ee.printStackTrace();
            GeneralUtils.errMessageBox(GeneralUtils.ERROR, "Сбой сервера\nПроверьте логи");
            ExchangeFactory.getPropLogEngine().log(ee);
        }
        return false;
    }

    public static String serverSetup(String title) {
        String cnctStr = null;
        String address = ExchangeFactory.getPropLogEngine().readProperty("ServerAddress", defaultServerIP);
        String[] vals = address.split(":");
        JTextField imageDirField = new JTextField(ExchangeFactory.getPropLogEngine().getProps().getProperty("imagedir"));
        JTextField addressField = new JTextField(16);
        addressField.setText(vals[0]);
        JSpinner portSpinner = new JSpinner(new SpinnerNumberModel(
                vals.length > 1 ? new Integer(vals[1]) : 1099, 0, 65536, 1));
        JTextField dbConnectionField = new JTextField(ExchangeFactory.getPropLogEngine().getProps()
//                .getProperty("JDBCconnection", "jdbc:derby://"
                .getProperty("JDBCconnection", "jdbc:mysql://"
                        + defaultServerIP
                        + "/materials"));
        JTextField dbDriverField = new JTextField(ExchangeFactory.getPropLogEngine().getProps()
//                .getProperty("dbDriverName", "org.apache.derby.jdbc.ClientDriver"));
                .getProperty("dbDriverName", "com.mysql.cj.jdbc.Driver"));
        JTextField dbUserField = new JTextField(ExchangeFactory.getPropLogEngine().getProps()
                .getProperty("dbUser", "nick"));
        JPasswordField dbPasswordField = new JPasswordField();

        JComponent[] edits = new JComponent[]{
            imageDirField, addressField, portSpinner,
            dbConnectionField, dbDriverField, dbUserField, dbPasswordField
        };
        new ConfigEditor(title, edits);
        if (ExchangeFactory.getProtocol().equals("rmi")) {
            if (addressField.getText().trim().length() > 0) {
                cnctStr = addressField.getText() + ":" + portSpinner.getValue();
                ExchangeFactory.getPropLogEngine().getProps().setProperty("ServerAddress", cnctStr);
                ExchangeFactory.getPropLogEngine().getProps().setProperty("imagedir", imageDirField.getText());
            }
        } else if (ExchangeFactory.getProtocol().equals("jdbc")) {
            if (dbConnectionField.getText().trim().length() > 0) {
                cnctStr = dbDriverField.getText() + ";"
                        + dbConnectionField.getText() + ";"
                        + dbUserField.getText() + ";"
                        + new String(dbPasswordField.getPassword());
                //imagedir
                ExchangeFactory.getPropLogEngine().getProps().setProperty("imagedir", imageDirField.getText());
                ExchangeFactory.getPropLogEngine().getProps().setProperty("JDBCconnection", dbConnectionField.getText());
                ExchangeFactory.getPropLogEngine().getProps().setProperty("dbDriverName", dbDriverField.getText());
                ExchangeFactory.getPropLogEngine().getProps().setProperty("dbUser", dbUserField.getText());
                ExchangeFactory.getPropLogEngine().getProps().setProperty("dbPassword", new String(dbPasswordField.getPassword()));
            }
        }
        return cnctStr;
    }

    /**
     * @return the exchanger
     */
    public static IMessageSender getExchanger() {
        return exchanger;
    }

    /**
     * @param aExchanger the exchanger to setА
     */
    public static void setExchanger(IMessageSender aExchanger) {
        LookupDialog.setExchanger(aExchanger);
        exchanger = aExchanger;
    }

    public static void configureConnection() {
        String cnctStr = serverSetup("DB connection");
        if (cnctStr != null) {
            try {
                if (ExchangeFactory.getProtocol().equals("rmi")) {
                    ExchangeFactory.getPropLogEngine().getProps().setProperty("ServerAddress", cnctStr);
                    setExchanger(ExchangeFactory.createRMIexchanger(cnctStr));
                } else {
                    String[] dbParams = cnctStr.split(";");
                    setExchanger(ExchangeFactory.createJDBCexchanger(dbParams));
                }
                ExchangeFactory.getPropLogEngine().saveProps();
            } catch (Exception ex) {
                ExchangeFactory.getPropLogEngine().logAndShowMessage(ex);
                quit(1);
            }
        }
    }

    public static void quit(int code) {
//        if (checkChatTimer != null) {
//            checkChatTimer.stop();
//        }
//        if (iconAnimationTimer != null) {
//            iconAnimationTimer.stop();
//        }
//        if (getExchanger() != null && currentUser != null) {
//            try {
//                currentUser.setLoggedin(null);
//                getExchanger().saveDbObject(currentUser);
//            } catch (Exception ex) {
//                log(ex);
//            }
//        }
        ExchangeFactory.getPropLogEngine().saveProps();
        System.exit(code);
    }

    /**
     * @return the currentUser
     */
    public static Mats_usr getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Mats_usr u) {
        currentUser = u;
    }
}
