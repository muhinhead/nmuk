// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Mon Jan 16 10:04:18 CET 2023
// generated file: do not modify
package com.xlend.orm;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Mats_addfile extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer addfileID = null;
    private String name = null;
    private String filetype = null;
    private Object filebody = null;
    private String description = null;
    private Integer userID = null;
    private Integer itemID = null;
    private Integer incomeID = null;

    public Mats_addfile(Connection connection) {
        super(connection, "mats_addfile", "addfileID");
        setColumnNames(new String[]{"addfileID", "name", "filetype", "filebody", "description", "userID", "itemID", "incomeID"});
    }

    public Mats_addfile(Connection connection, Integer addfileID, String name, String filetype, Object filebody, String description, Integer userID, Integer itemID, Integer incomeID) {
        super(connection, "mats_addfile", "addfileID");
        setNew(addfileID.intValue() <= 0);
//        if (addfileID.intValue() != 0) {
            this.addfileID = addfileID;
//        }
        this.name = name;
        this.filetype = filetype;
        this.filebody = filebody;
        this.description = description;
        this.userID = userID;
        this.itemID = itemID;
        this.incomeID = incomeID;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Mats_addfile mats_addfile = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT addfileID,name,filetype,filebody,description,userID,itemID,incomeID FROM mats_addfile WHERE addfileID=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                mats_addfile = new Mats_addfile(getConnection());
                mats_addfile.setAddfileID(new Integer(rs.getInt(1)));
                mats_addfile.setName(rs.getString(2));
                mats_addfile.setFiletype(rs.getString(3));
                mats_addfile.setFilebody(rs.getObject(4));
                mats_addfile.setDescription(rs.getString(5));
                mats_addfile.setUserID(new Integer(rs.getInt(6)));
                mats_addfile.setItemID(new Integer(rs.getInt(7)));
                mats_addfile.setIncomeID(new Integer(rs.getInt(8)));
                mats_addfile.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return mats_addfile;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO mats_addfile ("+(getAddfileID().intValue()!=0?"addfileID,":"")+"name,filetype,filebody,description,userID,itemID,incomeID) values("+(getAddfileID().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getAddfileID().intValue()!=0) {
                 ps.setObject(++n, getAddfileID());
             }
             ps.setObject(++n, getName());
             ps.setObject(++n, getFiletype());
             ps.setObject(++n, getFilebody());
             ps.setObject(++n, getDescription());
             ps.setObject(++n, getUserID());
             ps.setObject(++n, getItemID());
             ps.setObject(++n, getIncomeID());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getAddfileID().intValue()==0) {
             stmt = "SELECT max(addfileID) FROM mats_addfile";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setAddfileID(new Integer(rs.getInt(1)));
                 }
             } finally {
                 try {
                     if (rs != null) rs.close();
                 } finally {
                     if (ps != null) ps.close();
                 }
             }
         }
         setNew(false);
         setWasChanged(false);
         if (getTriggers() != null) {
             getTriggers().afterInsert(this);
         }
    }

    public void save() throws SQLException, ForeignKeyViolationException {
        if (isNew()) {
            insert();
        } else {
            if (getTriggers() != null) {
                getTriggers().beforeUpdate(this);
            }
            PreparedStatement ps = null;
            String stmt =
                    "UPDATE mats_addfile " +
                    "SET name = ?, filetype = ?, filebody = ?, description = ?, userID = ?, itemID = ?, incomeID = ?" + 
                    " WHERE addfileID = " + getAddfileID();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getName());
                ps.setObject(2, getFiletype());
                ps.setObject(3, getFilebody());
                ps.setObject(4, getDescription());
                ps.setObject(5, getUserID());
                ps.setObject(6, getItemID());
                ps.setObject(7, getIncomeID());
                ps.execute();
            } finally {
                if (ps != null) ps.close();
            }
            setWasChanged(false);
            if (getTriggers() != null) {
                getTriggers().afterUpdate(this);
            }
        }
    }

    public void delete() throws SQLException, ForeignKeyViolationException {
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM mats_addfile " +
                "WHERE addfileID = " + getAddfileID();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setAddfileID(new Integer(-getAddfileID().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getAddfileID().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT addfileID,name,filetype,filebody,description,userID,itemID,incomeID FROM mats_addfile " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Mats_addfile(con,new Integer(rs.getInt(1)),rs.getString(2),rs.getString(3),rs.getObject(4),rs.getString(5),new Integer(rs.getInt(6)),new Integer(rs.getInt(7)),new Integer(rs.getInt(8))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Mats_addfile[] objects = new Mats_addfile[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Mats_addfile mats_addfile = (Mats_addfile) lst.get(i);
            objects[i] = mats_addfile;
        }
        return objects;
    }

    public static boolean exists(Connection con, String whereCondition) throws SQLException {
        if (con == null) {
            return true;
        }
        boolean ok = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT addfileID FROM mats_addfile " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                "WHERE " + whereCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            ok = rs.next();
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return ok;
    }

    //public String toString() {
    //    return getAddfileID() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return addfileID;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setAddfileID(id);
        setNew(prevIsNew);
    }

    public Integer getAddfileID() {
        return addfileID;
    }

    public void setAddfileID(Integer addfileID) throws ForeignKeyViolationException {
        setWasChanged(this.addfileID != null && this.addfileID != addfileID);
        this.addfileID = addfileID;
        setNew(addfileID.intValue() == 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.name != null && !this.name.equals(name));
        this.name = name;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.filetype != null && !this.filetype.equals(filetype));
        this.filetype = filetype;
    }

    public Object getFilebody() {
        return filebody;
    }

    public void setFilebody(Object filebody) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.filebody != null && !this.filebody.equals(filebody));
        this.filebody = filebody;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.description != null && !this.description.equals(description));
        this.description = description;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) throws SQLException, ForeignKeyViolationException {
        if (null != userID)
            userID = userID == 0 ? null : userID;
        if (userID!=null && !Mats_usr.exists(getConnection(),"userID = " + userID)) {
            throw new ForeignKeyViolationException("Can't set userID, foreign key violation: key");
        }
        setWasChanged(this.userID != null && !this.userID.equals(userID));
        this.userID = userID;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) throws SQLException, ForeignKeyViolationException {
        if (null != itemID)
            itemID = itemID == 0 ? null : itemID;
        setWasChanged(this.itemID != null && !this.itemID.equals(itemID));
        this.itemID = itemID;
    }

    public Integer getIncomeID() {
        return incomeID;
    }

    public void setIncomeID(Integer incomeID) throws SQLException, ForeignKeyViolationException {
        if (null != incomeID)
            incomeID = incomeID == 0 ? null : incomeID;
        setWasChanged(this.incomeID != null && !this.incomeID.equals(incomeID));
        this.incomeID = incomeID;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[8];
        columnValues[0] = getAddfileID();
        columnValues[1] = getName();
        columnValues[2] = getFiletype();
        columnValues[3] = getFilebody();
        columnValues[4] = getDescription();
        columnValues[5] = getUserID();
        columnValues[6] = getItemID();
        columnValues[7] = getIncomeID();
        return columnValues;
    }

    public static void setTriggers(Triggers triggers) {
        activeTriggers = triggers;
    }

    public static Triggers getTriggers() {
        return activeTriggers;
    }

    //for SOAP exhange
    @Override
    public void fillFromString(String row) throws ForeignKeyViolationException, SQLException {
        String[] flds = splitStr(row, delimiter);
        try {
            setAddfileID(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setAddfileID(null);
        }
        setName(flds[1]);
        setFiletype(flds[2]);
        setFilebody(flds[3]);
        setDescription(flds[4]);
        try {
            setUserID(Integer.parseInt(flds[5]));
        } catch(NumberFormatException ne) {
            setUserID(null);
        }
        try {
            setItemID(Integer.parseInt(flds[6]));
        } catch(NumberFormatException ne) {
            setItemID(null);
        }
        try {
            setIncomeID(Integer.parseInt(flds[7]));
        } catch(NumberFormatException ne) {
            setIncomeID(null);
        }
    }
}
