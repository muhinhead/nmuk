package com.xlend.dbutil;

/**
 *
 * @author Nick Mukhin
 */
public class ComboItem {
    private int id;
    private String value;
    
    public ComboItem(int id, String value) {
        this.id = id;
        this.value = value;
    }
    
    public String toString() {
        return getValue();
    }

//    @Override
//    public boolean equals(Object ob) {
//        ComboItem other = (ComboItem) ob;
//        return this.id == other.id && this.value.equals(other.getValue());
//    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
}
