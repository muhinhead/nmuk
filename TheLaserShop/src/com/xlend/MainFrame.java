/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend;

import com.xlend.dbutil.ExchangeFactory;
import com.xlend.dbutil.IMessageSender;
import com.xlend.guiutil.GeneralFrame;
import com.xlend.guiutil.GeneralGridPanel;
import com.xlend.guiutil.GeneralUtils;
import com.xlend.guiutil.MyJideTabbedPane;
import java.rmi.RemoteException;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author nick
 */
public class MainFrame extends GeneralFrame {

    public static MainFrame instance;
    private static String[] sheetList = new String[]{
        "Users"
//        ,"Лечение"
//        ,"Диагнозы"
//        ,"Патологии"
//        ,"Пользователи"
    };
//    private GeneralGridPanel patientsPanel;
//    private GeneralGridPanel treatmentsPanel;
//    private GeneralGridPanel diagnosePanel;
//    private GeneralGridPanel patologyPanel;
    private GeneralGridPanel usersPanel;

    public MainFrame(IMessageSender exch) {
        super("TreatmentTrack Works Prototype v." + TheLaserShop.version, exch);
        instance = this;
    }

    @Override
    protected String[] getSheetList() {
        return sheetList;
    }

//    public void updateDependentGrids(String[] panels) throws RemoteException {
//        GeneralGridPanel grid;
//        for (String p : panels) {
//            grid = null;
//            if (p.equals(sheetList[1])) {
//                grid = (GeneralGridPanel) getClientsPanel();
//            } else if (p.equals(sheetList[2])) {
//                grid = (GeneralGridPanel) getCompaniesPanel();
//            }
//            if (grid != null) {
//                updateGrid(getExchanger(), grid.getTableView(), grid.getTableDoc(), grid.getSelect(), null,
//                        grid.getPageSelector().getSelectedIndex());
//
//            }
//        }
//    }
    @Override
    protected JTabbedPane getMainPanel() {
        int n = 0;
        MyJideTabbedPane fleetTab = new MyJideTabbedPane();
//        fleetTab.addTab(getPatientsPanel(), sheetList[n++]);
//        fleetTab.addTab(getTreatmentsPanel(), sheetList[n++]);
//        fleetTab.addTab(getDiagnosePanel(), sheetList[n++]);
//        fleetTab.addTab(getPatologyPanel(), sheetList[n++]);
        if (TheLaserShop.getCurrentUser().getIsAdmin() == 1) {
            fleetTab.addTab(getUsersPanel(), sheetList[n++]);
        }
        return fleetTab;
    }

    private JPanel getUsersPanel() {
        if (usersPanel == null) {
            try {
                registerGrid(usersPanel = new UserGrid(getExchanger()));
            } catch (RemoteException ex) {
                ExchangeFactory.getPropLogEngine().log(ex);
                GeneralUtils.errMessageBox(GeneralUtils.ERROR, ex.getMessage());
            }
        }
        return usersPanel;
    }

//    private JPanel getPatientsPanel() {
//        if (patientsPanel == null) {
//            try {
//                registerGrid(patientsPanel = new PatientsGrid(getExchanger()));
//            } catch (RemoteException ex) {
//                ExchangeFactory.getPropLogEngine().log(ex);
//                GeneralUtils.errMessageBox(GeneralUtils.ERROR, ex.getMessage());
//            }
//        }
//        return patientsPanel;
//    }
//
//    private JPanel getTreatmentsPanel() {
//        if (treatmentsPanel == null) {
//            try {
//                registerGrid(treatmentsPanel = new TreatmentsGrid(getExchanger(), null));
//            } catch (RemoteException ex) {
//                ExchangeFactory.getPropLogEngine().log(ex);
//                GeneralUtils.errMessageBox(GeneralUtils.ERROR, ex.getMessage());
//            }
//        }
//        return treatmentsPanel;
////        return new JPanel();
//    }
//
//    private JPanel getDiagnosePanel() {
//        if (diagnosePanel == null) {
//            try {
//                registerGrid(diagnosePanel = new DiagnosesGrid(getExchanger(), null));
//            } catch (RemoteException ex) {
//                ExchangeFactory.getPropLogEngine().log(ex);
//                GeneralUtils.errMessageBox(GeneralUtils.ERROR, ex.getMessage());
//            }
//        }
//        return diagnosePanel;
////        return new JPanel();
//    }
//
//    private JPanel getPatologyPanel() {
//        if (patologyPanel == null) {
//            try {
//                registerGrid(patologyPanel = new PathologyGrid(getExchanger()));
//            } catch (RemoteException ex) {
//                ExchangeFactory.getPropLogEngine().log(ex);
//                GeneralUtils.errMessageBox(GeneralUtils.ERROR, ex.getMessage());
//            }
//        }
//        return patologyPanel;
////        return new JPanel();
//    }
    
}
