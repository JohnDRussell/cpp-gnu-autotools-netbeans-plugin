/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.cppgnuautotools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.netbeans.api.project.Project;
import org.netbeans.modules.cnd.makeproject.api.configurations.MakeConfiguration;
import org.openide.modules.InstalledFileLocator;

/**
 *
 * @author derby
 */
public class OSReadAbsActTimer extends AbstractAction implements FunctionExecutor {
    public final Project context;
    public String m_sProjectPath;
    public String m_sAbsPath;
    public int m_nPermsInternal;
    public int m_nPermsInternalB;
    public int m_nTimerCounter;
    public Timer m_nTimerA;
    public Timer m_nTimerB;
    private final int m_nTypeRunnable;
    public final static int LIBTOOLIZE_RUNNABLE = 1;
    public final static int ACLOCAL_RUNNABLE = 2;
    public final static int AUTOHEADER_RUNNABLE = 3;
    public final static int AUTOMAKE_RUNNABLE = 4;
    public final static int AUTOCONF_RUNNABLE = 5;
    public final static int CONFIGURE_RUNNABLE = 6;
    public final static int CLEAN_AUTOTOOLS_RUNNABLE = 7;
    public Object[] m_oPossibleValues;
    public String m_sSelected;
    private final static int AUTOTOOLS_TIMER_DELAY = 2000;

    public OSReadAbsActTimer(Project context, String label, int nTypeRunnable) {
        super(label);
        this.context = context;
        this.m_nTypeRunnable = nTypeRunnable;
        m_nTimerA = null;
        m_nTimerB = null;
        m_nPermsInternal = -1;
        m_nPermsInternalB = -1;
        m_nTimerCounter = 0;
    }
    
    @Override
    public void execute(String sResultant) {
        m_sSelected = sResultant;
        MakeConfiguration conf = getProjectConfigInternal();
        HandleFilePermissions hFilePerm = new HandleFilePermissions();
        hFilePerm.ClearAbstractOutput();
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
        // JOptionPane.showMessageDialog(null, "===> m_nPermsInternal returns " + m_nPermsInternal);
        if (m_nPermsInternal == 2)
        {
            if (m_nTimerB != null) m_nTimerB.stop();
            m_nTimerB = null;
            m_nTimerA = new Timer(delay, taskPerformer);
            m_nTimerA.start();
            // JOptionPane.showMessageDialog(null, "ACLocal Starting timer");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        switch (m_nTypeRunnable) {
            case 1:
                m_oPossibleValues = null;
                m_oPossibleValues = AutotoolsOptions.fillLibtoolize();
                m_sAbsPath = InstalledFileLocator.getDefault().locate("libtoolize_runnable", "org.netbeans.modules.cppgnuautotools", false).getAbsolutePath();
                break;
            case 2:
                m_oPossibleValues = null;
                m_oPossibleValues = AutotoolsOptions.fillACLocal();
                m_sAbsPath = InstalledFileLocator.getDefault().locate("aclocal_runnable", "org.netbeans.modules.cppgnuautotools", false).getAbsolutePath();
                break;
            case 3:
                m_oPossibleValues = null;
                m_oPossibleValues = AutotoolsOptions.fillAutoHeader();
                m_sAbsPath = InstalledFileLocator.getDefault().locate("autoheader_runnable", "org.netbeans.modules.cppgnuautotools", false).getAbsolutePath();
                break;
            case 4:
                m_oPossibleValues = null;
                m_oPossibleValues = AutotoolsOptions.fillAutoMake();
                m_sAbsPath = InstalledFileLocator.getDefault().locate("automake_runnable", "org.netbeans.modules.cppgnuautotools", false).getAbsolutePath();
                break;
            case 5:
                m_oPossibleValues = null;
                m_oPossibleValues = AutotoolsOptions.fillAutoConf();
                m_sAbsPath = InstalledFileLocator.getDefault().locate("autoconf_runnable", "org.netbeans.modules.cppgnuautotools", false).getAbsolutePath();
                break;
            case 6:
                m_oPossibleValues = null;
                m_oPossibleValues = AutotoolsOptions.fillConfigure();
                m_sAbsPath = InstalledFileLocator.getDefault().locate("configure_runnable", "org.netbeans.modules.cppgnuautotools", false).getAbsolutePath();
                break;
            case 7:
                m_oPossibleValues = null;
                m_oPossibleValues = AutotoolsOptions.fillAutotoolsClean();
                m_sAbsPath = InstalledFileLocator.getDefault().locate("clean_autotools_runnable", "org.netbeans.modules.cppgnuautotools", false).getAbsolutePath();
                break;
        } 

        // OSReadTimerInternals lOSReader = new OSReadTimerInternals((Project)project);
        String path = context.getProjectDirectory().getPath();
        if (path != null) {
            // String absPath = InstalledFileLocator.getDefault().locate("aclocal_runnable", "org.netbeans.modules.cppgnuautotools", false).getAbsolutePath();
            // lOSReader.setAbsPath(absPath);
            // lOSReader.internalActionPerformed(path);
            internalActionPerformed(path);
        } else {
            if (context != null) {
                if (context.getClass() != null) {
                    if (context.getClass().getName() != null) {
                        // This just creates a popup message if you comment out the assert above.
                        JOptionPane.showMessageDialog(
                                null, 
                                "Path was null for project: " + context.getClass().getName());
                    } else {
                        // This just creates a popup message if you comment out the assert above.
                        JOptionPane.showMessageDialog(
                                null, 
                                "Path was null for project which getClass().getName() is also null");
                    }
                } else {
                    // This just creates a popup message if you comment out the assert above.
                    JOptionPane.showMessageDialog(
                            null, 
                            "Path was null for project which getClass() is also null");
                }
            } else {
                // This just creates a popup message if you comment out the assert above.
                JOptionPane.showMessageDialog(
                        null, 
                        "Path was null for project which is also null");
            }
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
        m_nPermsInternal = hFilePerm.getAfileOutputCalculation(true);
        MakeConfiguration conf = getProjectConfigInternal();
        // if (conf != null) {
            if (m_nPermsInternal == 0)
            {
                JOptionPane.showMessageDialog(null, "Netbeans Autotools Plugin failed to change permissions on file!");
                if (m_nTimerA != null) m_nTimerA.stop();
                m_nTimerA = null;
                m_nTimerB.stop();
                m_nTimerB = null;
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
        m_nPermsInternal = hFilePerm.getAfileOutputCalculation(true);
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
    }
}
