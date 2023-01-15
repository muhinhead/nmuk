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
import com.xlend.orm.Mats_addfile;
//import com.xlend.orm.Addfile;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author nick
 */
public class AdditionalFilesGrid extends AbstractGridAdapter {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();
    private static final String sp1 = "from Mats.addfile where ";

    static {
        maxWidths.put(0, 50);
    }

    public AdditionalFilesGrid(IMessageSender exchanger, String fkFieldName, Integer parentID) throws RemoteException {
        super(exchanger, "select addfileID \"ID\",name \"File Name\", filetype \"File Type\" " + sp1
                + fkFieldName + "=" + parentID,
                maxWidths, false);
    }

    private String getFKfieldName() {
        String sql = getSelect();
        int p1 = sql.indexOf(sp1) + sp1.length();
        int p2 = sql.indexOf("=", p1);
        return sql.substring(p1, p2);
    }

    private Integer getFKfieldValue() {
        String sql = getSelect();
        String fld = getFKfieldName();
        return Integer.valueOf(sql.substring(sql.indexOf(fld) + fld.length() + 1));
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add File") {

            @Override
            public void actionPerformed(ActionEvent e) {
                Integer prevFkValue = EditFilePanel.fkFieldValue;
                String prevFkName = EditFilePanel.fkFieldName;
                EditFilePanel.fkFieldValue = getFKfieldValue();
                EditFilePanel.fkFieldName = getFKfieldName();
                try {
                    GenericEditDialog<EditFilePanel> dlg
                            = new GenericEditDialog<EditFilePanel>(EditFilePanel.class, "Add file", null);
                    if (dlg.isOkPressed()) {
                        GeneralFrame.updateGrid(exchanger, getTableView(),
                                getTableDoc(), getSelect(), null, getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    ExchangeFactory.getPropLogEngine().log(ex);
                    GeneralUtils.errMessageBox(GeneralUtils.ERROR, ex.getMessage());
                }
                EditFilePanel.fkFieldValue = prevFkValue;
                EditFilePanel.fkFieldName = prevFkName;
            }
        };
    }

    @Override
    protected AbstractAction editAction() {
        return new AbstractAction("Edit File") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    Integer prevFkValue = EditFilePanel.fkFieldValue;
                    String prevFkName = EditFilePanel.fkFieldName;
                    EditFilePanel.fkFieldValue = getFKfieldValue();
                    EditFilePanel.fkFieldName = getFKfieldName();
                    try {
                        Mats_addfile addfile = (Mats_addfile) exchanger.loadDbObjectOnID(Mats_addfile.class, id);
                        GenericEditDialog<EditFilePanel> dlg
                                = new GenericEditDialog<EditFilePanel>(EditFilePanel.class, "Edit File", addfile);
                        if (dlg.isOkPressed()) {
                            GeneralFrame.updateGrid(exchanger, getTableView(),
                                    getTableDoc(), getSelect(), id, getPageSelector().getSelectedIndex());
                        }
                    } catch (RemoteException ex) {
                        ExchangeFactory.getPropLogEngine().log(ex);
                        GeneralUtils.errMessageBox(GeneralUtils.ERROR, ex.getMessage());
                    }
                    EditFilePanel.fkFieldValue = prevFkValue;
                    EditFilePanel.fkFieldName = prevFkName;
                }
            }
        };
    }

    @Override
    protected AbstractAction delAction() {
        return new AbstractAction("Delete File") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {                    
                    try {
                        Mats_addfile addfile = (Mats_addfile) exchanger.loadDbObjectOnID(Mats_addfile.class, id);
                        if (addfile != null) {
                            if (GeneralUtils.yesNo("File deletion",
                                    "Are you sure?") == JOptionPane.YES_OPTION) {
                                exchanger.deleteObject(addfile);
                                GeneralFrame.updateGrid(exchanger, getTableView(),
                                        getTableDoc(), getSelect(), null, getPageSelector().getSelectedIndex());
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
}
