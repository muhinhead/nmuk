/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.dbutil;

import static com.xlend.guiutil.AbstractGridAdapter.DD_MM_YYYY_HH_MI_SS;
import com.xlend.guiutil.LookupDialog;
import static com.xlend.guiutil.LookupDialog.getExchanger;
import com.xlend.orm.Mats_usr;
import com.xlend.orm.dbobject.DbObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author nick
 */
public class DbAssistant {

    public static List loadAllLogins() {
        try {
            DbObject[] users = (DbObject[]) LookupDialog.getExchanger().getDbObjects(Mats_usr.class, null, "login");
            ArrayList logins = new ArrayList();
            logins.add("");
            for (DbObject o : users) {
                Mats_usr up = (Mats_usr)o;
                logins.add(up.getLogin());
            }
            return logins;
        } catch (RemoteException ex) {
            ExchangeFactory.getPropLogEngine().log(ex);
        }
        return null;
    }

    public static Mats_usr findUser(String login, String password) throws RemoteException {
        DbObject[] users = (DbObject[]) getExchanger().getDbObjects(Mats_usr.class,
                "login='" + login + "' and password='" + password + "'", null);
        return (Mats_usr) (users.length > 0 ? users[0] : null);
    }
    
    private static ComboItem[] loadOnSelect(String select) {
        try {
            Vector[] tab = getExchanger().getTableBody(select);
            Vector rows = tab[1];
            ComboItem[] ans = new ComboItem[rows.size()];
            for (int i = 0; i < rows.size(); i++) {
                Vector line = (Vector) rows.get(i);
                int id = Integer.parseInt(line.get(0).toString());
                String tmvnr = line.get(1).toString();
                ans[i] = new ComboItem(id, tmvnr);
            }
            return ans;
        } catch (RemoteException ex) {
            ExchangeFactory.getPropLogEngine().log(ex);
        }
        return new ComboItem[]{new ComboItem(0, "")};
    }

    private static List loadListFromComboItems(String select) {
        ComboItem[] cits = loadOnSelect(select);
        ArrayList names = new ArrayList();
        names.add("");
        for (ComboItem ci : cits) {
            names.add(ci.getValue());
        }
        return names;
    }

    public static ComboItem[] loadUsers() {
        return loadOnSelect("select userID,concat(login,' (',firstName,' ',lastName.')') from mats_usr");
    }
    
//    public static ComboItem[] loadPatients() {
//        return loadOnSelect("select patient_id,concat(last_name,' ',substr(first_name,1,1),'.',substr(second_name,1,1),'.') from patient");
//    }
//
//    public static ComboItem[] loadPathologies() {
//        return loadOnSelect("select pathology_id, pathology from pathology");
//    }
//    
//    public static ComboItem[] loadTreatments() {
//        return loadOnSelect(
//                "select treatment_id,concat(last_name,' (',DATE_FORMAT(date_in,'" + DD_MM_YYYY_HH_MI_SS 
//                        + "'),'...',ifnull(DATE_FORMAT(date_out,'" + DD_MM_YYYY_HH_MI_SS + "'),''),')') "
//                        + "from treatment,patient where treatment.patient_id=patient.patient_id");
//    }
//    
//    public static ComboItem[] loadDiagnoses() {
//        return loadOnSelect("select diagnose_id,concat((select concat(last_name,' ',substr(first_name,1,1),'.',substr(second_name,1,1),'.')"
//                + " from patient where patient_id=diagnose.patient_id),'(',diagnose,')') ptnt from diagnose");
//    }
//
//    public static List loadAllWhereFroms() {
//        return loadListFromComboItems("select distinct 0,where_from from treatment order by where_from");
//    }
//
//    public static List loadAllDirections() {
//        return loadListFromComboItems("select distinct 0,directed from treatment order by where_from");
//    }
}
