/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend;

import static com.xlend.TheLaserShop.getExchanger;
import com.xlend.guiutil.GeneralUtils;
import com.xlend.guiutil.LookupDialog;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.JComboBox;

/**
 *
 * @author nick
 */
class UserLookupAction extends GeneralUtils.LookupAbstractAction {
    private final JComboBox cBox;

    public UserLookupAction(JComboBox cb) {
        super(UserLookupAction.class);
        this.cBox = cb;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            LookupDialog ld = new LookupDialog("Пользователи",cBox,new UserGrid(getExchanger()),
                new String[]{"firstName","secondName","lastName","login"});
        } catch (RemoteException ex) {
            GeneralUtils.errMessageBox(GeneralUtils.ERROR, ex.getMessage());
        }
    }
}
