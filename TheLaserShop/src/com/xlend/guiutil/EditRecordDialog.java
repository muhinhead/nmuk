/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.guiutil;

import com.xlend.dbutil.ExchangeFactory;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author nick
 */
public abstract class EditRecordDialog extends PopupDialog {

//    protected JButton applyButton;
//    private AbstractAction applyAction;
    protected JButton saveButton;
    private AbstractAction saveAction;
    protected JButton cancelButton;
    private AbstractAction cancelAction;
    private RecordEditPanel editPanel;

    public EditRecordDialog() {
        super();
        setResizable(true);
        setGlobalFont(this.getContentPane(), 18);

    }

    public EditRecordDialog(String title, Object obj) {
        super(null, title, obj);
        setResizable(true);
//        setUndecorated(false);
    }

    protected void fillContent(RecordEditPanel editPanel) {
        super.fillContent();
//        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setOkPressed(false);
        //XlendWorks.setWindowIcon(this, "Xcost.png");
        this.editPanel = editPanel;
        this.editPanel.setOwnerDialog(this);
        JPanel btnPanel = new JPanel();
//        btnPanel.add(applyButton = new JButton(applyAction = getApplyAction()));
        saveAction = getSaveAction();
        if (saveAction != null) {
            btnPanel.add(saveButton = new JButton(saveAction = getSaveAction()));
            saveButton.setToolTipText("Сохранить изменения и закрыть диалог");
        }
        btnPanel.add(cancelButton = new JButton(cancelAction = getCancelAction()));

//        applyButton.setToolTipText("Apply changes to database");
        cancelButton.setToolTipText("Отменить изменения и закрыть диалог");

        JPanel innerPanel = new JPanel(new BorderLayout());

        innerPanel.add(new JPanel(), BorderLayout.WEST);
        innerPanel.add(new JPanel(), BorderLayout.EAST);
        innerPanel.add(editPanel, BorderLayout.CENTER);

        JPanel aroundButton = new JPanel(new BorderLayout());
        aroundButton.add(btnPanel, BorderLayout.EAST);
        innerPanel.add(aroundButton, BorderLayout.SOUTH);
        getContentPane().add(new JScrollPane(innerPanel), BorderLayout.CENTER);
        if (saveButton != null) {
            getRootPane().setDefaultButton(saveButton);
        } else {
            getRootPane().setDefaultButton(cancelButton);
        }
    }

    @Override
    public void freeResources() {
//        applyButton.removeActionListener(applyAction);
        if (saveAction != null) {
            saveButton.removeActionListener(saveAction);
        }
        cancelButton.removeActionListener(cancelAction);
        saveAction = null;
        cancelAction = null;
//        applyAction = null;
        super.freeResources();
    }

    protected AbstractAction getSaveAction() {
        return new AbstractAction("Сохранить", new ImageIcon(GeneralUtils.loadImage("ok.png", EditRecordDialog.class))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (getEditPanel().save()) {
                        setOkPressed(true);
                        dispose();
                    }
                } catch (Exception ex) {
                    ExchangeFactory.getPropLogEngine().log(ex);
                    GeneralUtils.errMessageBox(GeneralUtils.ERROR, ex.getMessage());
                }
            }
        };
    }

    protected void setOkPressed(boolean b) {
    }

    protected String getCancelBtnLabel() {
        return "Отмена";
    }

    protected String getCancelBtnImage() {
        return "cancel.png";
    }

    protected AbstractAction getCancelAction() {
        return new AbstractAction(getCancelBtnLabel(), new ImageIcon(GeneralUtils.loadImage(getCancelBtnImage(), EditRecordDialog.class))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        };
    }

    /**
     * @return the editPanel
     */
    public RecordEditPanel getEditPanel() {
        return editPanel;
    }

    protected void setGlobalFont(Container cont, int fsize) {
        Component[] components = cont.getComponents();
        for (int i = components.length - 1; i >= 0; i--) {
            Component comp = components[i];
            if (comp != null) {
                if (comp instanceof Container) {
                    setGlobalFont((Container) comp, fsize);
                }
                Font f = comp.getFont();
                comp.setFont(new Font(f.getName(), f.getStyle(), fsize));
            }
        }
    }
    
}
