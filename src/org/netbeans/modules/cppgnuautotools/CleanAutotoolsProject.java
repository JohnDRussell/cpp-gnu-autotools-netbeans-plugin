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
        id = "org.netbeans.modules.cppgnuautotools.CleanAutotoolsProject"
)
@ActionRegistration(
        iconBase = "org/netbeans/modules/cppgnuautotools/ATool16b.png",
        displayName = "#CTL_CleanAutotoolsProject"
)
@ActionReference(path = "Menu/BuildProject/Autotools", position = 8, separatorBefore = 7)
@Messages("CTL_CleanAutotoolsProject=Clean Autotools Files")
public final class CleanAutotoolsProject extends OSReadTimer implements ActionListener {

    public CleanAutotoolsProject(Project context) {
        super(context);
    }

    @Override
    public void specificToolPerform() {
        m_sAbsPath = InstalledFileLocator.getDefault().locate("clean_autotools_runnable", "org.netbeans.modules.cppgnuautotools", false).getAbsolutePath();
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        m_oPossibleValues = null;
        m_oPossibleValues = AutotoolsOptions.fillAutotoolsClean();
        actionPerformedOSRT(false);
    }
}
