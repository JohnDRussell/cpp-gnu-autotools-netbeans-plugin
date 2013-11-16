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
import org.openide.modules.InstalledFileLocator;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Project",
        id = "org.netbeans.modules.cppgnuautotools.AutoConfProject"
)
@ActionRegistration(
        iconBase = "org/netbeans/modules/cppgnuautotools/ATool16b.png",
        displayName = "#CTL_AutoConfProject"
)
// @ActionReference(path = "Menu/BuildProject", position = 273)
@ActionReference(path = "Menu/BuildProject/Autotools", position = 5)
@Messages("CTL_AutoConfProject=AutoConf On Project")
public final class AutoConfProject extends OSReadTimer implements ActionListener {

    public AutoConfProject(Project context) {
        super(context);
    }

    @Override
    public void specificToolPerform() {
        m_sAbsPath = InstalledFileLocator.getDefault().locate("autoconf_runnable", "org.netbeans.modules.cppgnuautotools", false).getAbsolutePath();
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        m_oPossibleValues = null;
        m_oPossibleValues = AutotoolsOptions.fillAutoConf() ;
        actionPerformedOSRT(false);
    }
}
