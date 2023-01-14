/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.dbutil;

import com.xlend.orm.dbobject.DbObject;
import java.util.Vector;

/**
 *
 * @author nick
 */
public interface IMessageSender extends java.rmi.Remote {
    public DbObject[] getDbObjects(Class dbobClass, String whereCondition, 
            String orderCondition) throws java.rmi.RemoteException;
    public DbObject saveDbObject(DbObject dbob) throws java.rmi.RemoteException;
    public void deleteObject(DbObject dbob) throws java.rmi.RemoteException;
    public DbObject loadDbObjectOnID(Class dbobClass, int id) 
            throws java.rmi.RemoteException;
    public Vector[] getTableBody(String select) throws java.rmi.RemoteException;
    public Vector[] getTableBody(String select, int page, int pagesize) throws java.rmi.RemoteException;
    public int getCount(String select) throws java.rmi.RemoteException;
    public boolean truncateTable(String tableName) throws java.rmi.RemoteException;
    public void startTransaction(String transactionName) throws java.rmi.RemoteException;
    public void commitTransaction() throws java.rmi.RemoteException;
    public void rollbackTransaction(String transactionName) throws java.rmi.RemoteException;
    public String getServerVersion() throws java.rmi.RemoteException;
    public void callProcedure(String procName) throws java.rmi.RemoteException;
}