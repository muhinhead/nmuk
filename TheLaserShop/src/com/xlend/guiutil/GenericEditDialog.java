/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.guiutil;

//import com.ap.EditRecordDialog;
//import com.ap.RecordEditPanel;
//import com.xlend.dbutil.DbObject;
import com.xlend.dbutil.ExchangeFactory;
import com.xlend.orm.dbobject.DbObject;
    
/**
 *
 * @author nick
 */
/**
 * Generic version of the EditDialog class.
 *
 * @param <T> the type RecordEditPanel
 */
public class GenericEditDialog<T extends RecordEditPanel> extends EditRecordDialog {

    private boolean okPressed = false;
    private Class<T> panelType;
    private T panel;

    public GenericEditDialog(Class<T> type, String title, Object obj) {
        super();
        setTitle(title);
        setObject(obj);
        panelType = type;
        init();
    }

    public GenericEditDialog(Class<T> type, String title) {
        this(type, title, null);
    }
    
    @Override
    protected void fillContent() {
        try {
            panel = panelType.newInstance();
            panel.setDbObject((DbObject) getObject());
            panel.setOwnerDialog(this);
            super.fillContent(panel);
        } catch (InstantiationException | IllegalAccessException ex) {
            ExchangeFactory.getPropLogEngine().logAndShowMessage(ex);
        }
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }

    /**
     * @return the okPressed
     */
    public boolean isOkPressed() {
        return okPressed;
    }
}
