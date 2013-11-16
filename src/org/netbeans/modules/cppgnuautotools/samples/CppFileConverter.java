/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.cppgnuautotools.samples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.zip.ZipInputStream;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

/**
 *
 * @author derby
 */
public abstract class CppFileConverter {
    
    public static void writeFile(ZipInputStream str, FileObject fo) throws IOException {
        OutputStream out = fo.getOutputStream();
        try {
            FileUtil.copy(str, out);
        } finally {
            out.close();
        }
    }

    public static void filterProjectCppText(FileObject fo, ZipInputStream str, String name) throws IOException {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader( str ));
            OutputStream out;
            char strChars[] = new char[100];
            int nRead = 0;
            String wholeFile = "";
            while (true) {
                nRead = in.read(strChars, 0, 100);
                if (nRead > 0) wholeFile += new String(strChars, 0, nRead);
                if (nRead < 100) break ;
                // JOptionPane.showMessageDialog(null, "===> just Ran stat strAbstractOutput:" + strAbstractOutput);
            }
            
            wholeFile = wholeFile.replace("${[REPLACE-GNU-VERSION-AUTOCREATOR]}", "1.0.0");
            wholeFile = wholeFile.replace("${[REPLACE-GNU-AUTOCREATOR]}", name);
            out = fo.getOutputStream();
            out.write(wholeFile.getBytes("UTF-8"));

            try {
                out.close();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            writeFile(str, fo);
        }
    }
    
}
