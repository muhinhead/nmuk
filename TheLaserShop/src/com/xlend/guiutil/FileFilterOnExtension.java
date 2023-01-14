/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.guiutil;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author nick
 */
public class FileFilterOnExtension extends FileFilter {

    String extension;

    public FileFilterOnExtension(String extension) {
        super();
        this.extension = extension;
    }

    @Override
    public boolean accept(File f) {
        boolean ok = f.isDirectory()
                || f.getName().toLowerCase().endsWith(extension);
        return ok;
    }

    @Override
    public String getDescription() {
        return "*." + extension;
    }
}