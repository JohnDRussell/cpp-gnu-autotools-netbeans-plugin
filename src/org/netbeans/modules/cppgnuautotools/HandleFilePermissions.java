/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.cppgnuautotools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// import java.util.Collections;
// import java.util.List;
import javax.swing.JOptionPane;
import org.netbeans.api.extexecution.ExecutionDescriptor;
// import org.netbeans.api.extexecution.ExecutionDescriptor.LineConvertorFactory;
import org.netbeans.api.extexecution.ExecutionService;
import org.netbeans.api.extexecution.ExternalProcessBuilder;
// import org.netbeans.api.extexecution.print.ConvertedLine;
import org.netbeans.api.project.Project;
import org.netbeans.modules.cnd.makeproject.api.ProjectActionEvent;
import org.netbeans.modules.cnd.makeproject.api.configurations.MakeConfiguration;
// import org.netbeans.api.extexecution.print.LineConvertor;


/**
 *
 * @author derby
 */
public class HandleFilePermissions {
    
    /**
     * This variable is only used during an Abstract operation.
     */
    private static BufferedReader m_ReaderAbstract;
    static public String strAbstractOutput;
    
    public HandleFilePermissions() {
        // m_ReaderAbstract = null;
    }
    
    public void ClearAbstractOutput() {
        strAbstractOutput= "";
        m_ReaderAbstract = null;
    }
    
    public int getAfileOutputCalculation(boolean bUseAbstract) {
        if (bUseAbstract) {

             try {
                if (m_ReaderAbstract != null)
                {
                    while (true) {
                        final String line = m_ReaderAbstract.readLine();
                        if (line == null) {
                            break;
                        }
                        strAbstractOutput += line;
                    }
                }

            } catch(IOException ex) {
                JOptionPane.showMessageDialog(null, "===> getAfileOutputCalculation got an exception: " + ex.getMessage());
                return 0;
            }
            
            // JOptionPane.showMessageDialog(null, "===> just Checked stat");
            
            if (strAbstractOutput != null) {
                // JOptionPane.showMessageDialog(null, "===> stat returns something: [" + strAbstractOutput + "]");
                if (strAbstractOutput.length() >= 3) {
                    // JOptionPane.showMessageDialog(null, "===> stat returns " + strAbstractOutput);
                    String strTrunc = strAbstractOutput.substring(0, 3);

                    if (strTrunc.compareTo("664") == 0)
                    {
                        ClearAbstractOutput();
                        return 0;
                    }
                    else if (strTrunc.compareTo("777") == 0)
                    {
                        ClearAbstractOutput();
                        return 1;
                    }
                }
            }

            return 2;
            // return 0;
        } else {
            String strOutput = ProjectActionSupportLocal.getInstance().getOutputData();

            if (strOutput == null) return 2;

            ProjectActionSupportLocal.getInstance().cancelAllOperations();

            // JOptionPane.showMessageDialog(null, "===> here is strOutput in getAfilePermissions: " + strOutput);

            String strTrunc = strOutput.substring(0, 3);

            if (strTrunc.compareTo("664") == 0)
            {
                ProjectActionSupportLocal.getInstance().clearOutputData();
                // JOptionPane.showMessageDialog(null, "===> 664 needs change here is strTrunc in getAfilePermissions: " + strTrunc);
                return 0;
            }
            else if (strTrunc.compareTo("777") == 0)
            {
                ProjectActionSupportLocal.getInstance().clearOutputData();
                // JOptionPane.showMessageDialog(null, "===> 777 is success! No change here is strTrunc in getAfilePermissions: " + strTrunc);
                return 1;
            }
        }
        return -1;
    }

    public int getAfilePermissions(String absPath, Project context, MakeConfiguration conf) {
        if (conf == null) {
            try
            {
                strAbstractOutput = "" ;
                ExecutionDescriptor descriptor = new ExecutionDescriptor().frontWindow(true);

                //descriptor = descriptor.outConvertorFactory(new LineConvertorFactory() {
                //    @Override
                //    public LineConvertor newLineConvertor() {
                //        return new GatherOutput();
                //    }
                //});
                
                ExternalProcessBuilder processBuilder = new ExternalProcessBuilder("stat").
                                                        addArgument("--printf=%a").
                                                        addArgument(absPath);

                 m_ReaderAbstract = new BufferedReader(
                     new InputStreamReader(processBuilder.call().getInputStream())
                 );

//                final BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(processBuilder.call().getInputStream())
//                );

                ExecutionService service = ExecutionService.newService(processBuilder, descriptor, "Autotools Output");

                service.run();
                
                // JOptionPane.showMessageDialog(null, "===> just Ran stat");

//                // if (reader != null)
//                // {
//                    while (true) {
//                        final String line = reader.readLine();
//                        if (line == null) {
//                            break;
//                        }
//                        strAbstractOutput += line;
//                        // JOptionPane.showMessageDialog(null, "===> just Ran stat strAbstractOutput:" + strAbstractOutput);
//                    }
                // }
                
//                if (m_ReaderAbstract != null)
//                {
//                    while (true) {
//                        final String line = m_ReaderAbstract.readLine();
//                        if (line == null) {
//                            break;
//                        }
//                        strAbstractOutput += line;
//                    }
//                }

                return 2;

            } catch(IOException ex) {
                JOptionPane.showMessageDialog(null, "===> getAfilePermissions got an exception: " + ex.getMessage());
                return 0;
            }
        }

        ProjectActionSupportLocal.getInstance().cancelAllOperations();

        String[] sCommandArgs = new String[3];
        sCommandArgs[0] = "stat";
        sCommandArgs[1] = "--printf=%a";
        sCommandArgs[2] = absPath;

        ProjectActionEvent projectActionEvent = new ProjectActionEvent(
                context,
                ProjectActionEvent.PredefinedType.BUILD,
                "stat", conf,
                null,
                false);

        ProjectActionSupportLocal.getInstance().clearOutputData();
        
        ProjectActionSupportLocal.getInstance().fireActionPerformed(new ProjectActionEvent[]{projectActionEvent}, sCommandArgs);
        String strOutput = ProjectActionSupportLocal.getInstance().getOutputData();
        
        if (strOutput == null) return 2;
        
        ProjectActionSupportLocal.getInstance().cancelAllOperations();
        
        // JOptionPane.showMessageDialog(null, "===> here is strOutput in getAfilePermissions: " + strOutput);
        
        String strTrunc = strOutput.substring(0, 3);
        
        if (strTrunc.compareTo("664") == 0)
        {
            // JOptionPane.showMessageDialog(null, "===> 664 needs change here is strTrunc in getAfilePermissions: " + strTrunc);
            ProjectActionSupportLocal.getInstance().clearOutputData();
            return 0;
        }
        else if (strTrunc.compareTo("777") == 0)
        {
            // JOptionPane.showMessageDialog(null, "===> 777 is success! No change here is strTrunc in getAfilePermissions: " + strTrunc);
            ProjectActionSupportLocal.getInstance().clearOutputData();
            return 1;
        }
        
        return 2;
    }

//    private static class GatherOutput implements LineConvertor {
//        @Override
//        public List<ConvertedLine> convert(String line) {
//            List<ConvertedLine> result = Collections.singletonList(
//                    ConvertedLine.forText("1: " + line, null));
//            strAbstractOutput += line;
//            JOptionPane.showMessageDialog(null, "===> line: " + line);
//            return result;
//        }
//    }    
    
    public int changeAfilePermissions(String absPath, Project context, MakeConfiguration conf) {
        if (conf == null) {
            try
            {
                ExecutionDescriptor descriptor = new ExecutionDescriptor().frontWindow(true);

                ExternalProcessBuilder processBuilder = new ExternalProcessBuilder("chmod").
                                                            addArgument("777").
                                                            addArgument(absPath);

                // final BufferedReader reader = new BufferedReader(
                //     new InputStreamReader(processBuilder3.call().getInputStream())
                // );

                ExecutionService service = ExecutionService.newService(processBuilder, descriptor, "Autotools Output");

                service.run();
                
                return 1;
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "===> changeAfilePermissions got an exception: " + ex.getMessage());
                return 0;
            }
        }
        
        ProjectActionSupportLocal.getInstance().cancelAllOperations();

        String sCommandTotal = absPath;
        String[] sCommandArgs = new String[3];
        sCommandArgs[0] = "chmod";
        sCommandArgs[1] = "777";
        sCommandArgs[2] = absPath;
        ProjectActionEvent projectActionEvent = new ProjectActionEvent(
                context,
                ProjectActionEvent.PredefinedType.BUILD,
                sCommandTotal, conf,
                null,
                false);
        
        ProjectActionSupportLocal.getInstance().clearOutputData();

        ProjectActionSupportLocal.getInstance().fireActionPerformed(new ProjectActionEvent[]{projectActionEvent}, sCommandArgs);
        
        return 1;
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
            ProjectActionSupportLocal.getInstance().fireActionPerformed(new ProjectActionEvent[]{projectActionEvent}, sCommandArgs);
        }
    }
    
    public void RunAfileOnProject(String absPath, String projectPath, String extraArgs, Project context, MakeConfiguration conf) {
        if (conf == null) {
            try
            {
                ExecutionDescriptor descriptor = new ExecutionDescriptor().frontWindow(true);

                if (extraArgs == null) {
                    ExternalProcessBuilder processBuilder = new ExternalProcessBuilder(absPath).
                                                                addArgument(projectPath);
                    performActualRunOnPrj(descriptor, processBuilder);
                } else {
                    if (extraArgs.length() > 0) {
                        ExternalProcessBuilder processBuilder = new ExternalProcessBuilder(absPath).
                                                                    addArgument(projectPath).
                                                                    addArgument(extraArgs);
                        performActualRunOnPrj(descriptor, processBuilder);
                    } else {
                        ExternalProcessBuilder processBuilder = new ExternalProcessBuilder(absPath).
                                                                    addArgument(projectPath);
                        performActualRunOnPrj(descriptor, processBuilder);
                    }
                }
                
                return;
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "===> RunAfileOnProject got an exception: " + ex.getMessage());
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
    }
}
