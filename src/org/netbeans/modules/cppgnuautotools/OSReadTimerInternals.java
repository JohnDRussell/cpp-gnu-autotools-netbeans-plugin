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
import org.netbeans.modules.cnd.makeproject.api.configurations.MakeConfiguration;

/**
 *
 * @author derby
 */
public class OSReadTimerInternals implements FunctionExecutor {
    public final Project context;
    public String m_sProjectPath;
    public String m_sAbsPath;
    public int m_nPermsInternal;
    public int m_nPermsInternalB;
    public int m_nTimerCounter;
    public int m_nTimerRepeater;
    public Timer m_nTimerA;
    public Timer m_nTimerB;
    public Object[] m_oPossibleValues;
    public String m_sSelected;
    private final static int AUTOTOOLS_TIMER_DELAY = 2000;
    
    public OSReadTimerInternals(Project context) {
        this.context = context;
        m_nTimerA = null;
        m_nTimerB = null;
        m_nPermsInternal = -1;
        m_nPermsInternalB = -1;
        m_nTimerCounter = 0;
        m_nTimerRepeater = 0;
    }
    
    @Override
    public void execute(String sResultant) {
        if (m_nTimerRepeater < 10) {
            m_sSelected = sResultant;
            // action processor
            MakeConfiguration conf = getProjectConfigInternal();
            HandleFilePermissions hFilePerm = new HandleFilePermissions();
            hFilePerm.StartProgressIndicator();
            m_nTimerCounter = 0 ;
            // JOptionPane.showMessageDialog(null, "ACLocal Starting timer actionPerformedOSRT m_sProjectPath: " + m_sProjectPath);
            specificToolPerform();
            m_nPermsInternal = hFilePerm.getAfilePermissions(m_sAbsPath, context, conf);
            int delay = AUTOTOOLS_TIMER_DELAY; //milliseconds
            ActionListener taskPerformer = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    //...Perform a task...
                    // if (m_Conf != null) {
                        specificTimerToolPerform();
                        if (m_nPermsInternal == 2)
                        {
                            m_nTimerCounter++;
                            if (m_nTimerCounter >= 10)
                            {
                                if (m_nTimerB != null) m_nTimerB.stop();
                                m_nTimerB = null;
                                if (m_nTimerA != null) m_nTimerA.stop();
                                m_nTimerA = null;
                                // JOptionPane.showMessageDialog(null, "Timer timed out.  Not running AutoTools Action!");
                            }
                        }
                    // }
                }
            };
            if (m_nPermsInternal == 2)
            {
                if (m_nTimerB != null) m_nTimerB.stop();
                m_nTimerB = null;
                m_nTimerA = new Timer(delay, taskPerformer);
                m_nTimerA.start();
                // JOptionPane.showMessageDialog(null, "ACLocal Starting timer");
            }
        } else {
            JOptionPane.showMessageDialog(null, "This action has been tried 10 times and failed.  Stopping.");
        }
    }

    public void specificToolPerform() {
        
    }
    
    public void setAbsPath(String absPath) {
        m_sAbsPath = absPath;
    }

    public Project getProjectContextInternal() {
        return null;
    }
    
    public MakeConfiguration getProjectConfigInternal() {
        return null;
    }

    public void specificTimerToolPerformSecondCheck() {
        HandleFilePermissions hFilePerm = new HandleFilePermissions();
        m_nPermsInternal = hFilePerm.getAfileOutputCalculation(false);
        MakeConfiguration conf = getProjectConfigInternal();
        // if (conf != null) {
            if (m_nPermsInternal == 0)
            {
                if (m_nTimerRepeater < 10) {
                    /** Try 10 times before giving up and firing message to user. */
                    m_nTimerRepeater++;
                    if (m_nTimerA != null) m_nTimerA.stop();
                    m_nTimerA = null;
                    m_nTimerB.stop();
                    m_nTimerB = null;
                    execute(m_sSelected);
                } else {
                    JOptionPane.showMessageDialog(null, "Netbeans Autotools Plugin failed to change permissions on file! Please Run this command again!");
                }
            }
            else if (m_nPermsInternal == 1)
            {
                if (m_nTimerA != null) m_nTimerA.stop();
                m_nTimerA = null;
                m_nTimerB.stop();
                m_nTimerB = null;
                // JOptionPane.showMessageDialog(null, "ACLocal Starting timer inside actionPerformed timer code returned 1.");
                hFilePerm.RunAfileOnProject(m_sAbsPath, m_sProjectPath, m_sSelected, context, conf);
            }
        // }
    }

    public void specificTimerToolPerform() {
        HandleFilePermissions hFilePerm = new HandleFilePermissions();
        MakeConfiguration conf = getProjectConfigInternal();
        m_nPermsInternal = hFilePerm.getAfileOutputCalculation(false);
        if (m_nPermsInternal == 0)
        {
            m_nTimerA.stop();
            m_nTimerA = null;
            // JOptionPane.showMessageDialog(null, "ACLocal Starting timer inside actionPerformed timer code returned 0");
            m_nPermsInternalB = hFilePerm.getAfilePermissions(m_sAbsPath, context, conf);
            if (hFilePerm.changeAfilePermissions(m_sAbsPath, context, conf) == 1)
            {
                m_nPermsInternalB = hFilePerm.getAfilePermissions(m_sAbsPath, context, conf);
                if (m_nPermsInternalB == 2)
                {
                    int delay = AUTOTOOLS_TIMER_DELAY; //milliseconds
                    ActionListener taskPerformerB = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            //...Perform a task...
                            specificTimerToolPerformSecondCheck();
                            if (m_nPermsInternal == 2)
                            {
                                m_nTimerCounter++;
                                if (m_nTimerCounter >= 10)
                                {
                                    m_nTimerB.stop();
                                    // JOptionPane.showMessageDialog(null, "Timer timed out.  Not running AutoTools Action!");
                                }
                            }
                        }
                    };
                    m_nTimerB = new Timer(delay, taskPerformerB);
                    m_nTimerB.start();
                    // JOptionPane.showMessageDialog(null, "ACLocal Starting timer");
                }
            }
        }
        else if (m_nPermsInternal == 1)
        {
            if (m_nTimerB != null) m_nTimerB.stop();
            m_nTimerB = null;
            m_nTimerA.stop();
            m_nTimerA = null;
            // JOptionPane.showMessageDialog(null, "ACLocal Starting timer inside actionPerformed timer code returned 1.");
            hFilePerm.RunAfileOnProject(m_sAbsPath, m_sProjectPath, m_sSelected, context, conf);
        }
    }
    
    public void internalActionPerformed(String path) {
        m_sProjectPath = path;
        ShowPopupDialogInput();
    }
    
    void ShowPopupDialogInput() {
        SelectorDialog.showMainDialog(m_oPossibleValues, (FunctionExecutor) this);
        // m_oSelected = JOptionPane.showInputDialog(null,
        //     "Choose one", "Input",
        //     JOptionPane.INFORMATION_MESSAGE, null,
        //     m_oPossibleValues, m_oPossibleValues[0]);
    }
    
}
