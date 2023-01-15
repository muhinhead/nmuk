/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend;

//import static com.ap.AllPlasticWorks.getExchanger;
//import com.xlend.allplastic.orm.User;
import static com.xlend.TheLaserShop.getExchanger;
import com.xlend.dbutil.ExchangeFactory;
import com.xlend.guiutil.GeneralUtils;
import com.xlend.guiutil.MyJideTabbedPane;
import com.xlend.guiutil.RecordEditPanel;
import com.xlend.orm.Mats_usr;
//import com.xlend.orm.Maths_user;
//import static com.xlend.tt.TreatmentTrack.getExchanger;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.rmi.RemoteException;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author nick
 */
public class EditUserPanel extends RecordEditPanel {

    private JTextField positionField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JPasswordField pwdField1;
    private JPasswordField pwdField2;
    private JCheckBox isAdminCB;
    private JTextField loginField;
    private JTextField middleNameField;
    private MyJideTabbedPane downPanel;

    @Override
    protected void fillContent() {
        String[] titles = new String[]{
                "ID", //"Position:",
                "First Name:", //"Middle Name:", 
                "Last Name:",
                "Login:",
                "Password:",// "re-enter password:"
                ""
        };
        JComponent edits[] = new JComponent[]{
            getGridPanel(new JComponent[]{idField = new JTextField(),
                new JLabel("Position:", SwingConstants.RIGHT),
                positionField = new JTextField()
            }),
            getGridPanel(new JComponent[]{firstNameField = new JTextField(),
                new JLabel("MIssle Name:", SwingConstants.RIGHT),
                middleNameField = new JTextField()
            }),
            lastNameField = new JTextField(30),
            getGridPanel(new JComponent[]{loginField = new JTextField(20),new JPanel()}),
            getBorderPanel(new JComponent[]{
                pwdField1 = new JPasswordField(20),
                new JLabel("repeat the password:", SwingConstants.RIGHT),
                pwdField2 = new JPasswordField(20)
            }),
            getBorderPanel(new JComponent[]{isAdminCB = new JCheckBox("Administrator")})
        };
        idField.setEnabled(false);

        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        Mats_usr user = (Mats_usr) getDbObject();
        if (downPanel == null) {
            String hdr = "Additional Info";
            downPanel = new MyJideTabbedPane();
            downPanel.setPreferredSize(new Dimension(downPanel.getPreferredSize().width, 300));
            try {
                if (user == null) {
                    JLabel lbl = new JLabel("Before adding the file save this record", SwingConstants.CENTER);
                    lbl.setForeground(Color.blue);
                    lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 16));
                    downPanel.add(lbl, hdr);
                } else {
                    downPanel.add(new AdditionalFilesGrid(getExchanger(), "userID", user.getPK_ID()),"Files");
                }
            } catch (RemoteException ex) {
                ExchangeFactory.getPropLogEngine().log(ex);
                GeneralUtils.errMessageBox(GeneralUtils.ERROR, ex.getMessage());
            }
            add(downPanel, BorderLayout.CENTER);
        }
        if (user != null) {
            isViewOnly = user.getPK_ID() < 0;
            idField.setText("" + Math.abs(user.getPK_ID().intValue()));
            positionField.setText(user.getPosition());
            firstNameField.setText(user.getFirstName());
            middleNameField.setText(user.getSecondName());
            lastNameField.setText(user.getLastName());
            loginField.setText(user.getLogin());
            pwdField1.setText(user.getPassword());
            pwdField2.setText(user.getPassword());
            isAdminCB.setSelected(user.getIsAdmin() != null && user.getIsAdmin().intValue() == 1);

        }
        disable4view(new JComponent[]{
            positionField, firstNameField, lastNameField,
            pwdField1, pwdField2, isAdminCB, loginField, middleNameField
        });
    }

    @Override
    public boolean save() throws Exception {
        String pwd1, pwd2;
        boolean ok;
        pwd1 = new String(pwdField1.getPassword());
        pwd2 = new String(pwdField2.getPassword());
        if (!pwd1.equals(pwd2)) {
            GeneralUtils.errMessageBox("Attention!", "Passwords does not match!");
            pwdField1.requestFocus();
            return false;
        }
        boolean isNew = false;
        Mats_usr user = (Mats_usr) getDbObject();
        if (user == null) {
            user = new Mats_usr(null);
            user.setPK_ID(0);
            isNew = true;
        }
        user.setPosition(positionField.getText());
        user.setFirstName(firstNameField.getText());
        user.setSecondName(middleNameField.getText());
        user.setLastName(lastNameField.getText());
        user.setLogin(loginField.getText());
        user.setPassword(pwd2);
        user.setIsAdmin(isAdminCB.isSelected() ? 1 : 0);
        return saveDbRecord(user, isNew);
    }
    
    @Override
    public void freeResources() {
        //
    }

}
