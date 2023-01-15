/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend;

import com.xlend.dbutil.ExchangeFactory;
import com.xlend.dbutil.IMessageSender;
import com.xlend.guiutil.AbstractGridAdapter;
import com.xlend.guiutil.GeneralFrame;
import com.xlend.guiutil.GeneralUtils;
import com.xlend.guiutil.GenericEditDialog;
import com.xlend.guiutil.GenericViewDialog;
import com.xlend.orm.Mats_usr;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author nick
 */
public class UserGrid extends AbstractGridAdapter {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 50);
    }

    public UserGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, 
//                "select * from mats.usr",
                "select userID \"ID\", firstName \"First Name\", "
                + "lastName \"Last Name\", login \"Login\" , case when isAdmin=1 then 'Yes' else 'No' end \"Superuser\" "
                + "from mats.usr", 
                maxWidths, false);
    }
    
//    @Override
//    protected JPanel getRightPanel(JPanel btnPanel) {
//        btnPanel.setLayout(new GridLayout(4, 1, 5, 5));
//        JButton viewBtn;
//        btnPanel.add(viewBtn = new JButton(getViewAction()));
//        viewBtn.setIcon(new ImageIcon(GeneralUtils.loadImage("lookup.png", getClass())));
//        return super.getRightPanel(btnPanel);
//    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    GenericEditDialog<EditUserPanel> dlg
                            = new GenericEditDialog<EditUserPanel>(EditUserPanel.class, "Add User", null);
                    if (dlg.isOkPressed()) {
                        GeneralFrame.updateGrid(exchanger, getTableView(),
                                getTableDoc(), getSelect(), null, getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    ExchangeFactory.getPropLogEngine().log(ex);
                    GeneralUtils.errMessageBox(GeneralUtils.ERROR, ex.getMessage());
                }
            }
        };
    }

    @Override
    protected AbstractAction editAction() {
        return new AbstractAction("Edit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Mats_usr user = (Mats_usr) exchanger.loadDbObjectOnID(Mats_usr.class, id);
                        GenericEditDialog<EditUserPanel> dlg
                                = new GenericEditDialog<EditUserPanel>(EditUserPanel.class, "Edit User", user);
                        if (dlg.isOkPressed()) {
                            GeneralFrame.updateGrid(exchanger, getTableView(),
                                    getTableDoc(), getSelect(), id, getPageSelector().getSelectedIndex());
                        }
                    } catch (RemoteException ex) {
                        ExchangeFactory.getPropLogEngine().log(ex);
                        GeneralUtils.errMessageBox(GeneralUtils.ERROR, ex.getMessage());
                    }
                }
            }
        };
    }

    @Override
    protected AbstractAction delAction() {
        return new AbstractAction("Удалить") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Mats_usr user = (Mats_usr) exchanger.loadDbObjectOnID(Mats_usr.class, id);
                        if (user != null) {
                            if (user.getPK_ID().intValue() == TheLaserShop.getCurrentUser().getPK_ID().intValue()) {
                                GeneralUtils.errMessageBox("No!", "You cannot delete yourself!");
                            } else if (GeneralUtils.yesNo("Attention!",
                                    "Are you sure?") == JOptionPane.YES_OPTION) {
//                                if (DbAssistant.findProjectByUserID(user.getPK_ID()) != null) {
//                                    GeneralUtils.errMessageBox("Sorry!", "Projects found created or modified by this user");
//                                } else {
                                    exchanger.deleteObject(user);
                                    GeneralFrame.updateGrid(exchanger, getTableView(),
                                            getTableDoc(), getSelect(), null, getPageSelector().getSelectedIndex());
//                                }
                            }
                        }
                    } catch (RemoteException ex) {
                        ExchangeFactory.getPropLogEngine().log(ex);
                        GeneralUtils.errMessageBox(GeneralUtils.ERROR, ex.getMessage());
                    }
                }
            }
        };
    }

    private AbstractAction getViewAction() {
        return new AbstractAction("Просмотр") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Mats_usr user = (Mats_usr) exchanger.loadDbObjectOnID(Mats_usr.class, id);
                        user.setPK_ID(-id); //to mark as view-only
                        GenericViewDialog<EditUserPanel> dlg
                                = new GenericViewDialog<EditUserPanel>(EditUserPanel.class, "Просмотр пользователя", user);
                    } catch (Exception ex) {
                        ExchangeFactory.getPropLogEngine().log(ex);
                        GeneralUtils.errMessageBox(GeneralUtils.ERROR, ex.getMessage());
                    }
                }
            }

        };
    }
}
