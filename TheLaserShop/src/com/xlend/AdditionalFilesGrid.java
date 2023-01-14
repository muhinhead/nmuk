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
    private static final String sp1 = "from addfile where ";

    static {
        maxWidths.put(0, 50);
    }

    public AdditionalFilesGrid(IMessageSender exchanger, String fkFieldName, Integer parentID) throws RemoteException {
        super(exchanger, "select addfile_id \"ID\",name \"Имя файла\", filetype \"Тип файла\" " + sp1
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
        return Integer.parseInt(sql.substring(sql.indexOf(fld) + fld.length() + 1));
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Добавить файл") {

            @Override
            public void actionPerformed(ActionEvent e) {
//                Integer prevFkValue = EditFilePanel.fkFieldValue;
//                String prevFkName = EditFilePanel.fkFieldName;
//                EditFilePanel.fkFieldValue = getFKfieldValue();
//                EditFilePanel.fkFieldName = getFKfieldName();
//                try {
//                    GenericEditDialog<EditFilePanel> dlg
//                            = new GenericEditDialog<EditFilePanel>(EditFilePanel.class, "Добавить файл", null);
//                    if (dlg.isOkPressed()) {
//                        GeneralFrame.updateGrid(exchanger, getTableView(),
//                                getTableDoc(), getSelect(), null, getPageSelector().getSelectedIndex());
//                    }
//                } catch (RemoteException ex) {
//                    ExchangeFactory.getPropLogEngine().log(ex);
//                    GeneralUtils.errMessageBox(GeneralUtils.ERROR, ex.getMessage());
//                }
//                EditFilePanel.fkFieldValue = prevFkValue;
//                EditFilePanel.fkFieldName = prevFkName;
            }

        };
    }

    @Override
    protected AbstractAction editAction() {
        return new AbstractAction("Редактировать файл") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
//                    Integer prevFkValue = EditFilePanel.fkFieldValue;
//                    String prevFkName = EditFilePanel.fkFieldName;
//                    EditFilePanel.fkFieldValue = getFKfieldValue();
//                    EditFilePanel.fkFieldName = getFKfieldName();
//                    try {
//                        Addfile addfile = (Addfile) exchanger.loadDbObjectOnID(Addfile.class, id);
//                        GenericEditDialog<EditFilePanel> dlg
//                                = new GenericEditDialog<EditFilePanel>(EditFilePanel.class, "Редактировать файл", addfile);
//                        if (dlg.isOkPressed()) {
//                            GeneralFrame.updateGrid(exchanger, getTableView(),
//                                    getTableDoc(), getSelect(), id, getPageSelector().getSelectedIndex());
//                        }
//                    } catch (RemoteException ex) {
//                        ExchangeFactory.getPropLogEngine().log(ex);
//                        GeneralUtils.errMessageBox(GeneralUtils.ERROR, ex.getMessage());
//                    }
//                    EditFilePanel.fkFieldValue = prevFkValue;
//                    EditFilePanel.fkFieldName = prevFkName;
                }
            }

        };
    }

    @Override
    protected AbstractAction delAction() {
        return new AbstractAction("Удалить файл") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {                    
//                    try {
//                        Addfile addfile = (Addfile) exchanger.loadDbObjectOnID(Addfile.class, id);
//                        if (addfile != null) {
//                            if (GeneralUtils.yesNo("Внимание!",
//                                    "Вы действительно хотите удалить этот файл?") == JOptionPane.YES_OPTION) {
//                                exchanger.deleteObject(addfile);
//                                GeneralFrame.updateGrid(exchanger, getTableView(),
//                                        getTableDoc(), getSelect(), null, getPageSelector().getSelectedIndex());
//                            }
//                        }
//                    } catch (RemoteException ex) {
//                        ExchangeFactory.getPropLogEngine().log(ex);
//                        GeneralUtils.errMessageBox(GeneralUtils.ERROR, ex.getMessage());
//                    }
                }
            }
        };
    }
}
