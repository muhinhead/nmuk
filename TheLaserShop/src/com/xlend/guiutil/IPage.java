/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.guiutil;

import com.xlend.dbutil.ForeignKeyViolationException;
import java.sql.SQLException;

/**
 *
 * @author nick
 */
public interface IPage {
    Object getPagescan();
    void setPagescan(Object pagescan) throws SQLException, ForeignKeyViolationException;
    String getFileextension();
    void setFileextension(String fileextension) throws SQLException, ForeignKeyViolationException;
}
