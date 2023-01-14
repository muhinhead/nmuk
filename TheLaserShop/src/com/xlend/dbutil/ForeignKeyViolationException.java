/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.dbutil;

/**
 *
 * @author Nick Mukhin
 */
public class ForeignKeyViolationException extends Exception {
    public ForeignKeyViolationException(String s) {
        super(s);
    }
}
