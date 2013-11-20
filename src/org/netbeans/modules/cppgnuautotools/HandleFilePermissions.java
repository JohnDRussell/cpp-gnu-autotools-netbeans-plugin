/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.cppgnuautotools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;
import org.netbeans.api.extexecution.ExternalProcessBuilder;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.project.Project;
import org.netbeans.modules.cnd.makeproject.api.ProjectActionEvent;
import org.netbeans.modules.cnd.makeproject.api.configurations.MakeConfiguration;

/**
 *
 * @author Derby Russell <jdrussell51@gmail.com>
 */
public class HandleFilePermissions {
    
    /**
     * This variable is only used during an Abstract operation.
     */
    private static BufferedReader m_ReaderAbstract;
    private static BufferedReader m_ReaderAbstractError;
    private static Process m_ExecutionProcess;
    private final static String m_PermissionSet = "554";
    private static ProgressHandle m_ProgressHandle;
     
    public HandleFilePermissions() {
    }
    
    public void ClearOutputWindowDataStorage() {
        try
        {
            if (m_ReaderAbstract != null) {
                m_ReaderAbstract.close();
            }
            m_ReaderAbstract = null;
            if (m_ReaderAbstractError != null) {
                m_ReaderAbstractError.close();
            }
            m_ReaderAbstractError = null;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "===> ClearOutputWindowDataStorage got an exception: " + ex.getMessage());
        }
    }
    
    public void StartProgressIndicator() {
        if (m_ProgressHandle != null) {
            m_ProgressHandle = null;
        }
        m_ProgressHandle = ProgressHandleFactory.createHandle("Running external script");
        m_ProgressHandle.start();
    }
    
    private int performActualCalcuation(String strTrunc) {
        int nReturnValue = 0;
        
        if (strTrunc.compareTo("774") == 0)
        {
            ClearOutputWindowDataStorage();
            nReturnValue = 1;
        }
        else if (strTrunc.compareTo("775") == 0)
        {
            ClearOutputWindowDataStorage();
            nReturnValue = 1;
        }
        else if (strTrunc.compareTo("777") == 0)
        {
            ClearOutputWindowDataStorage();
            nReturnValue = 1;
        }
        else if (strTrunc.compareTo("554") == 0)
        {
            ClearOutputWindowDataStorage();
            nReturnValue = 1;
        }
        else if (strTrunc.compareTo(m_PermissionSet) == 0)
        {
            ClearOutputWindowDataStorage();
            nReturnValue = 1;
        }
        return nReturnValue;
    }

    
    public int getAfileOutputCalculation(boolean bUseAbstract) {
        String strTrunc = "" ;
        String strOutput = "" ;

        m_ProgressHandle.progress( "script running" );

        try
        {
            if (m_ReaderAbstract != null)
            {
                while (true) {
                    final String line = m_ReaderAbstract.readLine();
                    if (line == null) {
                        break;
                    }
                    strOutput += line;
                }
            }
            if (m_ReaderAbstractError != null)
            {
                while (true) {
                    final String line = m_ReaderAbstractError.readLine();
                    if (line == null) {
                        break;
                    }
                    strOutput += line;
                }
            }
            m_ExecutionProcess.waitFor();
        } catch(IOException ex) {
            JOptionPane.showMessageDialog(null, "===> getAfileOutputCalculation got an exception: " + ex.getMessage());
            ClearOutputWindowDataStorage();
            return 2;
        } catch (InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "===> getAfileOutputCalculation got an exception: " + ex.getMessage());
            ClearOutputWindowDataStorage();
            return 2;
        }
        if (strOutput.length() >= 3) {
            strTrunc = strOutput.substring(0, 3);
        }
        return performActualCalcuation(strTrunc);
    }

    public int getAfilePermissions(String absPath, Project context, MakeConfiguration conf) {
        try {
            m_ExecutionProcess = null;
            m_ReaderAbstract = null;
            m_ReaderAbstractError = null;
            m_ExecutionProcess = Runtime.getRuntime().exec("stat --printf=%a " + absPath);
            m_ReaderAbstract = new BufferedReader(new InputStreamReader(m_ExecutionProcess.getInputStream()));
            m_ReaderAbstractError = new BufferedReader(new InputStreamReader(m_ExecutionProcess.getErrorStream()));
            m_ProgressHandle.progress( "script prepared" );
            return 2;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "===> getAfilePermissions got an exception: " + ex.getMessage());
            return 0;
        }
    }

    public int changeAfilePermissions(String absPath, Project context, MakeConfiguration conf) {
        m_ProgressHandle.progress( "next script" );
        try {
            ClearOutputWindowDataStorage();
            m_ExecutionProcess = null;
            m_ReaderAbstract = null;
            m_ReaderAbstractError = null;
            m_ExecutionProcess = Runtime.getRuntime().exec("chmod " + m_PermissionSet + " " + absPath);
            m_ReaderAbstract = new BufferedReader(new InputStreamReader(m_ExecutionProcess.getInputStream()));
            m_ReaderAbstractError = new BufferedReader(new InputStreamReader(m_ExecutionProcess.getErrorStream()));
            m_ProgressHandle.progress( "next script prepared" );
            if (m_ReaderAbstract != null)
            {
                while (true) {
                    final String line = m_ReaderAbstract.readLine();
                    if (line == null) {
                        break;
                    }
                }
            }
            if (m_ReaderAbstractError != null)
            {
                while (true) {
                    final String line = m_ReaderAbstractError.readLine();
                    if (line == null) {
                        break;
                    }
                }
            }
            m_ExecutionProcess.waitFor();
            m_ProgressHandle.progress( "next script completed" );
            ClearOutputWindowDataStorage();
            return 1;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "===> changeAfilePermissions got an exception: " + ex.getMessage());
            ClearOutputWindowDataStorage();
            return 0;
        } catch (InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "===> getAfileOutputCalculation got an exception: " + ex.getMessage());
            ClearOutputWindowDataStorage();
            return 0;
        }
    }
    
    private void performActualRunOnPrj(ExecutionDescriptor descriptor, ExternalProcessBuilder processBuilder) {
        try
        {
            ExecutionService service = ExecutionService.newService(processBuilder, descriptor, "Autotools Output");
            service.run();

        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "===> RunAfileOnProject got an exception: " + ex.getMessage());
        }
    }

    private void performActualRunOn(Project context, String sCommandTotal, String[] sCommandArgs, MakeConfiguration conf) {
        if (conf != null) {
            ProjectActionEvent projectActionEvent = new ProjectActionEvent(
                    context,
                    ProjectActionEvent.PredefinedType.BUILD,
                    sCommandTotal, conf,
                    null,
                    false);
            ProjectActionSupportLocal.getInstance().clearOutputData();
            m_ProgressHandle.progress( "final script prepared" );
            ProjectActionSupportLocal.getInstance().fireActionPerformed(new ProjectActionEvent[]{projectActionEvent}, sCommandArgs);
        }
    }
    
    public void RunAfileOnProject(String absPath, String projectPath, String extraArgs, Project context, MakeConfiguration conf) {
        m_ProgressHandle.progress( "final script" );
        if (conf == null) {
            try
            {
                ExecutionDescriptor descriptor = new ExecutionDescriptor().frontWindow(true);

                if (extraArgs == null) {
                    ExternalProcessBuilder processBuilder = new ExternalProcessBuilder(absPath).
                                                                addArgument(projectPath);
                    m_ProgressHandle.progress( "final script prepared" );
                    performActualRunOnPrj(descriptor, processBuilder);
                } else {
                    if (extraArgs.length() > 0) {
                        ExternalProcessBuilder processBuilder = new ExternalProcessBuilder(absPath).
                                                                    addArgument(projectPath).
                                                                    addArgument(extraArgs);
                        m_ProgressHandle.progress( "final script prepared" );
                        performActualRunOnPrj(descriptor, processBuilder);
                    } else {
                        ExternalProcessBuilder processBuilder = new ExternalProcessBuilder(absPath).
                                                                    addArgument(projectPath);
                        m_ProgressHandle.progress( "final script prepared" );
                        performActualRunOnPrj(descriptor, processBuilder);
                    }
                }
                
                m_ProgressHandle.finish();
                m_ProgressHandle = null;
                return;
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "===> RunAfileOnProject got an exception: " + ex.getMessage());
                m_ProgressHandle.finish();
                m_ProgressHandle = null;
                return;
            }
        }

        ProjectActionSupportLocal.getInstance().cancelAllOperations();

        String sCommandTotal = absPath;
        if (extraArgs != null) {
            if (extraArgs.length() > 0) {
                String[] sCommandArgs = new String[3];
                sCommandArgs[0] = absPath;
                sCommandArgs[1] = projectPath;
                sCommandArgs[2] = extraArgs;
                performActualRunOn(context, sCommandTotal, sCommandArgs, conf);
            } else {
                String[] sCommandArgs = new String[2];
                sCommandArgs[0] = absPath;
                sCommandArgs[1] = projectPath;
                performActualRunOn(context, sCommandTotal, sCommandArgs, conf);
            }
        } else {
            String[] sCommandArgs = new String[2];
            sCommandArgs[0] = absPath;
            sCommandArgs[1] = projectPath;
            performActualRunOn(context, sCommandTotal, sCommandArgs, conf);
        }
        
        m_ProgressHandle.finish();
        m_ProgressHandle = null;
    }
}
