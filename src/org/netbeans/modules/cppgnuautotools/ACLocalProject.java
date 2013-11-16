/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.modules.cppgnuautotools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.modules.InstalledFileLocator;

/**
 * Still need libtoolize, autoheader
 * And each tool needs an option edit box somehow.
 * 
 * Also when cleaning autotools remove the aclocal directory cache too!
 * autom4te.cache
 * @author derby
 */

@ActionID(
        category = "Project",
        id = "org.netbeans.modules.cppgnuautotools.ACLocalProject"
)
@ActionRegistration(
        iconBase = "org/netbeans/modules/cppgnuautotools/ATool16b.png",
        displayName = "#CTL_ACLocalProject"
)
@ActionReference(path = "Menu/BuildProject/Autotools", position = 2)
@Messages("CTL_ACLocalProject=ACLocal On Project")
public final class ACLocalProject extends OSReadTimer implements ActionListener {

    public ACLocalProject(Project context) {
        super(context);
    }

    @Override
    public void specificToolPerform() {
        m_sAbsPath = InstalledFileLocator.getDefault().locate("aclocal_runnable", "org.netbeans.modules.cppgnuautotools", false).getAbsolutePath();
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        m_oPossibleValues = null;
        m_oPossibleValues = AutotoolsOptions.fillACLocal() ;
        actionPerformedOSRT(false);
    }
}
