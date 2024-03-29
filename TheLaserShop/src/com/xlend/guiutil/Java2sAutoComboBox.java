package com.xlend.guiutil;

import java.awt.event.ItemEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxEditor;

/**
 *
 * @author Nick Mukhin
 */
public class Java2sAutoComboBox extends JComboBox {

//    public Java2sAutoComboBox(Object[] loadAllLogins) {
//        super(loadAllLogins);
//    }

    private class AutoTextFieldEditor extends BasicComboBoxEditor {

        private Java2sAutoTextField getAutoTextFieldEditor() {
            return (Java2sAutoTextField) editor;
        }

        AutoTextFieldEditor(java.util.List list, boolean strict) {
            editor = new Java2sAutoTextField(list, Java2sAutoComboBox.this);
            ((Java2sAutoTextField)editor).setStrict(strict);
        }
    }

    public Java2sAutoComboBox(java.util.List list) {
        this(list, true);
    }
    
    public Java2sAutoComboBox(java.util.List list, boolean strict) {
        isFired = false;
        autoTextFieldEditor = new AutoTextFieldEditor(list, strict);
        setEditable(true);
        setModel(new DefaultComboBoxModel(list.toArray()) {

            protected void fireContentsChanged(Object obj, int i, int j) {
                if (!isFired) {
                    super.fireContentsChanged(obj, i, j);
                }
            }
        });
        setEditor(autoTextFieldEditor);
    }

    public boolean isCaseSensitive() {
        return autoTextFieldEditor.getAutoTextFieldEditor().isCaseSensitive();
    }

    public void setCaseSensitive(boolean flag) {
        autoTextFieldEditor.getAutoTextFieldEditor().setCaseSensitive(flag);
    }

    public boolean isStrict() {
        return autoTextFieldEditor.getAutoTextFieldEditor().isStrict();
    }

    public void setStrict(boolean flag) {
        autoTextFieldEditor.getAutoTextFieldEditor().setStrict(flag);
    }

    public java.util.List getDataList() {
        return autoTextFieldEditor.getAutoTextFieldEditor().getDataList();
    }

    public void setDataList(java.util.List list) {
        autoTextFieldEditor.getAutoTextFieldEditor().setDataList(list);
        setModel(new DefaultComboBoxModel(list.toArray()));
    }

    void setSelectedValue(Object obj) {
        if (isFired) {
            return;
        } else {
            isFired = true;
            setSelectedItem(obj);
            fireItemStateChanged(new ItemEvent(this, 701, selectedItemReminder,
                    1));
            isFired = false;
            return;
        }
    }

    public void setText(String txt) {
        setSelectedValue(txt);
        getAutoTextFieldEditor().setItem(txt);
    }
    
    public AutoTextFieldEditor getAutoTextFieldEditor() {
        return autoTextFieldEditor;
    }
    
    protected void fireActionEvent() {
        if (!isFired) {
            super.fireActionEvent();
        }
    }
    private AutoTextFieldEditor autoTextFieldEditor;
    private boolean isFired;
}
