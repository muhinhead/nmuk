/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.guiutil;

//import com.ap.RecordEditPanel;
import javax.swing.AbstractAction;

/**
 *
 * @author nick
 */
public class GenericViewDialog<T extends RecordEditPanel> extends GenericEditDialog {

    public GenericViewDialog(Class<T> type, String title, Object obj) {
        super(type, title, obj);        
    }
    
    @Override
    protected AbstractAction getSaveAction() {
//        AbstractAction action = super.getSaveAction();
//        action.setEnabled(false);
//        return action;
        return null;
    }
    
    @Override
    protected String getCancelBtnLabel() {
        return "Close";
    }
    
    @Override
    protected String getCancelBtnImage() {
        return "ok.png";
    }
}
