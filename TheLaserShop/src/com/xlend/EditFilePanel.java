/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend;

import com.xlend.dbutil.DbAssistant;
import com.xlend.dbutil.ExchangeFactory;
import com.xlend.guiutil.GeneralUtils;
import com.xlend.guiutil.RecordEditPanel;
import com.xlend.guiutil.ToolBarButton;
import com.xlend.orm.Mats_addfile;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author nick
 */
public class EditFilePanel extends RecordEditPanel {

    static Integer fkFieldValue;
    static String fkFieldName;
    private JTextField fileTypeField;
    private JTextField fileNameField;
    private JComboBox parentCB;
    private GeneralUtils.LookupAbstractAction lookupAct;
    private JTextField descriptionField;
    private ToolBarButton addBtn;
    private ToolBarButton clearBtn;
    private ToolBarButton showBtn;
    private byte[] docBody;
    private static String docExtension = null;
    private static HashMap<String, String> hm = new HashMap<String, String>();

    static {
        hm.put("userID", "User:");
//        hm.put("patient_id", "Пациент:");
//        hm.put("diagnose_id", "Диагноз:");
//        hm.put("treatment_id", "Лечение:");
//        hm.put("pathology_id", "Патология:");
    }

    @Override
    protected void fillContent() {
        String[] titles;
        titles = new String[]{
            "ID",// "Type:",
            "File name:",
            hm.get(fkFieldName),
            "Description:",
            ""
        };

        JComponent edits[] = new JComponent[]{
            getBorderPanel(new JComponent[]{idField = new JTextField(5), new JLabel("File type:"), fileTypeField = new JTextField(15)}),
            fileNameField = new JTextField(),
            getParentControl(),
            descriptionField = new JTextField(60),
            getBorderPanel(new JComponent[]{
                new JPanel(),
                getGridPanel(new JComponent[]{
                    new JPanel(),
                    addBtn = new ToolBarButton("plus.png", "Add/Replace file"),
                    clearBtn = new ToolBarButton("minus.png", "Clear file"),
                    showBtn = new ToolBarButton("download.png", "Show file"),
                    new JPanel()
                }),
                new JPanel()
            })
        };
        addBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser(ExchangeFactory.getPropLogEngine().readProperty("imagedir", "./"));
                chooser.setDialogTitle("Import file");
                chooser.setApproveButtonText("Import");
                int retVal = chooser.showOpenDialog(null);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File f = chooser.getSelectedFile();
                    docBody = GeneralUtils.readFile(f.getAbsolutePath());
                    fileTypeField.setText(docExtension = GeneralUtils.getFileExtension(f));
                    fileNameField.setText(f.getName());
                }
            }
        });
        clearBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                docBody = null;
                docExtension = null;
                fileTypeField.setText("");
            }
        });
        showBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (docBody != null) {
                    JFileChooser chooser = new JFileChooser(ExchangeFactory.getPropLogEngine().readProperty("imagedir", "./"));
                    chooser.setFileFilter(new FileFilter() {

                        public boolean accept(File f) {
                            boolean ok = f.isDirectory()
                                    || f.getName().toLowerCase().endsWith(docExtension);
                            return ok;
                        }

                        @Override
                        public String getDescription() {
                            return "*." + docExtension;
                        }
                    });
                    chooser.setDialogTitle("Export to local file for view or editing");
                    chooser.setApproveButtonText("Export");
                    int retVal = chooser.showOpenDialog(null);
                    if (retVal == JFileChooser.APPROVE_OPTION) {
                        File f = chooser.getSelectedFile();
                        GeneralUtils.writeFile(f, docBody);
                        try {
                            Desktop.getDesktop().open(f);
                        } catch (IOException ex) {
                            ExchangeFactory.getPropLogEngine().logAndShowMessage(ex);
                        }
                    }
                } else {
                    GeneralUtils.errMessageBox("Sorry!", "The file body seems not loaded to the database");
                }
            }
        });

        idField.setEnabled(false);
        fileTypeField.setEnabled(false);
        organizePanels(titles, edits, null);
    }

    private JComponent getParentControl() {
        switch (fkFieldName) {
            case "userID":
                return comboPanelWithLookupBtn(parentCB = new JComboBox(DbAssistant.loadUsers()), lookupAct = new UserLookupAction(parentCB));
//            case "patient_id":
//                return comboPanelWithLookupBtn(parentCB = new JComboBox(DbAssistant.loadPatients()), lookupAct = new PatientLookupAction(parentCB));
//            case "treatment_id":
//                return comboPanelWithLookupBtn(parentCB = new JComboBox(DbAssistant.loadTreatments()), lookupAct = new TreatmentsLookupAction(parentCB));
//            case "diagnose_id":
//                return comboPanelWithLookupBtn(parentCB = new JComboBox(DbAssistant.loadDiagnoses()), lookupAct = new DiagnoseLookupAction(parentCB));
//            case "pathology_id":
//                return comboPanelWithLookupBtn(parentCB = new JComboBox(DbAssistant.loadPathologies()), lookupAct = new PathoilogyLookupAction(parentCB));
            default:
                return new JLabel("Unknown fkFieldName=" + fkFieldName);
        }
    }

    @Override
    public void loadData() {
        docExtension = null;
        Mats_addfile af = (Mats_addfile) getDbObject();
        if (af != null) {
            idField.setText(af.getPK_ID().toString());
            fileTypeField.setText(docExtension = af.getFiletype());
            fileNameField.setText(af.getName());
            switch (fkFieldName) {
                case "userID":
                    selectComboItem(parentCB, af.getUserID());
                    break;
//                case "patient_id":
//                    selectComboItem(parentCB, af.getPatientId());
//                    break;
//                case "treatment_id":
//                    selectComboItem(parentCB, af.getTreatmentId());
//                    break;
//                case "diagnose_id":
//                    selectComboItem(parentCB, af.getDiagnoseId());
//                    break;
//                case "pathology_id":
//                    selectComboItem(parentCB, af.getPathologyId());
//                    break;
            }
            descriptionField.setText(af.getDescription());
            docBody = (byte[]) af.getFilebody();
//            try {
//                blob = (org.apache.derby.client.am.ClientBlob) af.getFilebody();
//                if (blob != null) {
//                    int blobLength = (int) blob.length();
//                    docBody = blob.getBytes(1, blobLength);
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(EditFilePanel.class.getName()).log(Level.SEVERE, null, ex);
//                //ExchangeFactory.getPropLogEngine().log(ex);
//                GeneralUtils.errMessageBox(GeneralUtils.ERROR, ex.getMessage());
//            }
        } else {
            selectComboItem(parentCB, fkFieldValue);
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Mats_addfile af = (Mats_addfile) getDbObject();
        if (af == null) {
            isNew = true;
            af = new Mats_addfile(null);
            af.setAddfileID(0);
        }
        af.setFiletype(fileTypeField.getText());
        af.setName(fileNameField.getText());

        Integer parentID = getSelectedCbItem(parentCB);
        switch (fkFieldName) {
            case "userID":
                af.setUserID(parentID);
                break;
//            case "patient_id":
//                af.setPatientId(parentID);
//                break;
//            case "treatment_id":
//                af.setTreatmentId(parentID);
//                break;
//            case "diagnose_id":
//                af.setDiagnoseId(parentID);
//                break;
//            case "pathology_id":
//                af.setPathologyId(parentID);
//                break;
        }

        af.setFilebody(docBody);
        af.setDescription(descriptionField.getText());
        return saveDbRecord(af, isNew);
    }

    @Override
    public void freeResources() {
        lookupAct = null;
//        try {
//            if (blob != null) {
//                blob.free();
//                blob = null;
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(EditFilePanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

}
