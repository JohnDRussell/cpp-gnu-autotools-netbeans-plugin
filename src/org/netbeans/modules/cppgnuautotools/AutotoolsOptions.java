/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.cppgnuautotools;

/**
 *
 * @author derby
 */
public class AutotoolsOptions {
    
    public static Object[] fillLibtoolize() {
        Object[] oPossibleValues = new Object[6];
        oPossibleValues[0] = "Select Options for libtoolize";
        oPossibleValues[1] = "Options";
        oPossibleValues[2] = "--verbose";
        oPossibleValues[3] = "--quiet";
        oPossibleValues[4] = "--no-warn";
        oPossibleValues[5] = "--debug";
        return oPossibleValues;
    }
    
    public static Object[] fillACLocal() {
        Object[] oPossibleValues = new Object[4];
        oPossibleValues[0] = "Select Options for aclocal";
        oPossibleValues[1] = "Options";
        oPossibleValues[2] = "--install";
        oPossibleValues[3] = "-I m4";
        return oPossibleValues;
    }

    public static Object[] fillAutoHeader() {
        Object[] oPossibleValues = new Object[12];
        oPossibleValues[0] = "Select Options for autoheader";
        oPossibleValues[1] = "Options";
        oPossibleValues[2] = "--verbose";
        oPossibleValues[3] = "--debug";
        oPossibleValues[4] = "--force";
        oPossibleValues[5] = "--include=directory";
        oPossibleValues[6] = "--prepend-include=directory";
        oPossibleValues[7] = "--warnings=obsolete";
        oPossibleValues[8] = "--warnings=all";
        oPossibleValues[9] = "--warnings=none";
        oPossibleValues[10] = "--warnings=error";
        oPossibleValues[11] = "--warnings=no-";
        return oPossibleValues;
    }
    
    public static Object[] fillAutoMake() {
        Object[] oPossibleValues = new Object[12];
        oPossibleValues[0] = "Select Options for automake";
        oPossibleValues[1] = "Options";
        oPossibleValues[2] = "--add-missing";
        oPossibleValues[3] = "--force-missing";
        oPossibleValues[4] = "--verbose";
        oPossibleValues[5] = "--copy";
        oPossibleValues[6] = "--libdir=directory";
        oPossibleValues[7] = "--print-libdir";
        oPossibleValues[8] = "--ignore-deps";
        oPossibleValues[9] = "--include-deps";
        oPossibleValues[10] = "--warnings=obsolete";
        oPossibleValues[11] = "--warnings=all";
        return oPossibleValues;
    }
    
    public static Object[] fillAutoConf() {
        Object[] oPossibleValues = new Object[12];
        oPossibleValues[0] = "Select Options for autoconf";
        oPossibleValues[1] = "Options";
        oPossibleValues[2] = "--verbose";
        oPossibleValues[3] = "--debug";
        oPossibleValues[4] = "--force";
        oPossibleValues[5] = "--include=directory";
        oPossibleValues[6] = "--output=file";
        oPossibleValues[7] = "--warnings=obsolete";
        oPossibleValues[8] = "--warnings=all";
        oPossibleValues[9] = "--warnings=none";
        oPossibleValues[10] = "--warnings=error";
        oPossibleValues[11] = "--warnings=no-";
        return oPossibleValues;
    }
    
    public static Object[] fillConfigure() {
        Object[] oPossibleValues = new Object[12];
        oPossibleValues[0] = "Select Options for ./configure";
        oPossibleValues[1] = "Options";
        oPossibleValues[2] = "--prefix=directory";
        oPossibleValues[3] = "--exec-prefix=directory";
        oPossibleValues[4] = "--with-var-prefix=directory";
        oPossibleValues[5] = "--with-python=/path/to/python";
        oPossibleValues[6] = "--with-username=";
        oPossibleValues[7] = "--with-groupname=";
        oPossibleValues[8] = "--with-mail-gid=";
        oPossibleValues[9] = "--with-mailhost=";
        oPossibleValues[10] = "--with-urlhost=";
        oPossibleValues[11] = "--with-gcc=no";
        return oPossibleValues;
    }

    public static Object[] fillAutotoolsClean() {
        Object[] oPossibleValues = new Object[2];
        oPossibleValues[0] = "No Options for Clean";
        oPossibleValues[1] = "Options";
        return oPossibleValues;
    }
    
}
