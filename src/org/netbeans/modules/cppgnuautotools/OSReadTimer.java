/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.cppgnuautotools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;
import org.netbeans.modules.cnd.makeproject.api.configurations.ConfigurationSupport;
import org.netbeans.modules.cnd.makeproject.api.configurations.MakeConfiguration;

/**
 *
 * @author derby
 */
public class OSReadTimer extends OSReadTimerInternals {
    public final MakeConfiguration m_Conf;
    
    public OSReadTimer(Project context) {
        super(context);
        m_Conf = ConfigurationSupport.getProjectActiveConfiguration(context);
    }
    
    @Override
    public Project getProjectContextInternal() {
        return context;
    }
    
    @Override
    public MakeConfiguration getProjectConfigInternal() {
        return m_Conf;
    }

    public void actionPerformedOSRT(boolean bUseLookup) {
        boolean bFoundProject = false;
        for (Project p : OpenProjects.getDefault().getOpenProjects()) {
            Service s = p.getLookup().lookup(Service.class);
            if ((s != null) || (bUseLookup)) {
                bFoundProject = true;
                if (m_Conf != null) {
                    internalActionPerformed(s.mpp());
//                    HandleFilePermissions hFilePerm = new HandleFilePermissions();
//                    m_sProjectPath = s.mpp();
//                    m_nTimerCounter = 0 ;
//                    // JOptionPane.showMessageDialog(null, "ACLocal Starting timer actionPerformedOSRT m_sProjectPath: " + m_sProjectPath);
//                    specificToolPerform();
//                    m_nPermsInternal = hFilePerm.getAfilePermissions(m_sAbsPath, context, m_Conf);
//                    int delay = 1000; //milliseconds
//                    ActionListener taskPerformer = new ActionListener() {
//                        @Override
//                        public void actionPerformed(ActionEvent evt) {
//                            //...Perform a task...
//                            if (m_Conf != null) {
//                                specificTimerToolPerform();
//                                if (m_nPermsInternal == 2)
//                                {
//                                    m_nTimerCounter++;
//                                    if (m_nTimerCounter >= 10)
//                                    {
//                                        if (m_nTimerB != null) m_nTimerB.stop();
//                                        m_nTimerB = null;
//                                        if (m_nTimerA != null) m_nTimerA.stop();
//                                        m_nTimerA = null;
//                                        // JOptionPane.showMessageDialog(null, "Timer timed out.  Not running AutoTools Action!");
//                                    }
//                                }
//                            }
//                        }
//                    };
//                    if (m_nPermsInternal == 2)
//                    {
//                        if (m_nTimerB != null) m_nTimerB.stop();
//                        m_nTimerB = null;
//                        m_nTimerA = new Timer(delay, taskPerformer);
//                        m_nTimerA.start();
//                        // JOptionPane.showMessageDialog(null, "ACLocal Starting timer");
//                    }
                }
            }
            return;
        }
        if (!bFoundProject) {
            JOptionPane.showMessageDialog(null, "ACLocal On Project does not apply to this type of project!");
        }
    }
}
