/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */
package org.netbeans.modules.cppgnuautotools;

import java.awt.event.ActionEvent;
// import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
// import java.util.HashSet;
import java.util.List;
import java.util.Map;
// import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
// import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
// import org.netbeans.api.project.SourceGroup;
// import org.netbeans.api.project.Sources;
import org.netbeans.api.project.ui.OpenProjects;
import org.netbeans.modules.cnd.api.project.NativeProject;
import org.netbeans.modules.cnd.api.project.NativeProjectChangeSupport;
import org.netbeans.modules.cnd.api.remote.PathMap;
import org.netbeans.modules.cnd.api.remote.RemoteFileUtil;
import org.netbeans.modules.cnd.api.remote.RemoteSyncSupport;
import org.netbeans.modules.cnd.makeproject.MakeOptions;
import org.netbeans.modules.cnd.makeproject.api.BuildActionsProvider;
import org.netbeans.modules.cnd.makeproject.api.BuildActionsProvider.BuildAction;
import org.netbeans.modules.cnd.makeproject.api.BuildActionsProvider.OutputStreamHandler;
import org.netbeans.modules.cnd.makeproject.api.ProjectActionHandler;
import org.netbeans.modules.cnd.makeproject.api.DefaultProjectActionHandler;
import org.netbeans.modules.cnd.makeproject.api.ProjectActionEvent;
import org.netbeans.modules.cnd.makeproject.api.ProjectActionEvent.PredefinedType;
import org.netbeans.modules.cnd.makeproject.api.ProjectActionEvent.Type;
import org.netbeans.modules.cnd.makeproject.api.ProjectActionHandlerFactory;
// import org.netbeans.modules.cnd.makeproject.api.configurations.DebuggerChooserConfiguration;
import org.netbeans.modules.cnd.makeproject.api.configurations.MakeConfiguration;
// import org.netbeans.modules.cnd.makeproject.api.configurations.ui.CustomizerNode;
import org.netbeans.modules.cnd.makeproject.api.runprofiles.RunProfile;
// import org.netbeans.modules.cnd.makeproject.ui.MakeLogicalViewProvider;
// import org.netbeans.modules.cnd.makeproject.ui.SelectExecutablePanel;
// import org.netbeans.modules.cnd.utils.CndPathUtilitities;
import org.netbeans.modules.cnd.utils.CndUtils;
// import org.netbeans.modules.cnd.utils.cache.CndFileUtils;
import org.netbeans.modules.dlight.api.terminal.TerminalSupport;
import org.netbeans.modules.nativeexecution.api.ExecutionEnvironment;
import org.netbeans.modules.nativeexecution.api.ExecutionListener;
// import org.netbeans.modules.nativeexecution.api.util.ConnectionManager.CancellationException;
// import org.netbeans.modules.nativeexecution.api.util.HostInfoUtils;
// import org.netbeans.modules.nativeexecution.api.util.ProcessUtils;
// import org.netbeans.modules.nativeexecution.api.util.ProcessUtils.ExitStatus;
// import org.netbeans.modules.remote.spi.FileSystemProvider;
// import org.openide.DialogDescriptor;
// import org.openide.DialogDisplayer;
import org.openide.LifecycleManager;
// import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
// import org.openide.filesystems.FileUtil;
import org.openide.util.Cancellable;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
// import org.openide.util.NbBundle;
// import org.openide.util.NbPreferences;
import org.openide.util.RequestProcessor;
// import org.openide.util.Utilities;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;

/**
 * Most of the code here came from DefaultProjectActionHandler
 * as result of refactoring.
 */
public class ProjectActionSupportLocal {

    private String[] command_arguments_for_execute;
    private String outputStringData;
    private HandleEvents m_pEvents;

    private static ProjectActionSupportLocal instance;
    private static final RequestProcessor RP = new RequestProcessor("ProjectActionSupportLocal.refresh", 1); // NOI18N
    private static final Logger LOGGER = Logger.getLogger("org.netbeans.modules.cnd.makeproject"); // NOI18N
    private final List<ProjectActionHandlerFactory> handlerFactories;
    private AutoToolsProjectActionHandler m_AThandler;

    private ProjectActionSupportLocal() {
        handlerFactories = new ArrayList<ProjectActionHandlerFactory>(
                 Lookup.getDefault().lookupAll(ProjectActionHandlerFactory.class));
    }

    /**
     * Singleton pattern: instance getter.
     *
     * @return singleton instance
     */
    public static synchronized ProjectActionSupportLocal getInstance() {
        if (instance == null) {
            instance = new ProjectActionSupportLocal();
        }
        return instance;
    }

    private static boolean isFileOperationsIntensive(ProjectActionEvent pae) {
        return true;
//        Type type = pae.getType();
//        if (type == PredefinedType.BUILD || type == PredefinedType.CLEAN || type == PredefinedType.BUILD_TESTS) {
//            return true;
//        }
//        return false;
    }
    
//    private static void refreshProjectFilesOnFinish(final ProjectActionEvent curPAE, final FileOperationsNotifier fon) {
//        try {
//            if (curPAE.getType() != PredefinedType.RUN && !fon.isLastExpectedEvent(curPAE)) {
//                return;
//            }
//            final Project project = curPAE.getProject();
//            final Set<File> files = new HashSet<File>();
//            final Set<FileObject> fileObjects = new HashSet<FileObject>();
//            FileObject projectFileObject = project.getProjectDirectory();
//            File f = FileUtil.toFile(projectFileObject);
//            if (f != null) {
//                files.add(f);
//            } else {
//                fileObjects.add(projectFileObject);
//            }
//            Sources sources = ProjectUtils.getSources(project);
//            SourceGroup[] groups = sources.getSourceGroups(Sources.TYPE_GENERIC);
//            for (SourceGroup sourceGroup : groups) {
//                FileObject rootFolder = sourceGroup.getRootFolder();
//                File file = FileUtil.toFile(rootFolder);
//                if (file != null) {
//                    files.add(file);
//                } else {
//                    fileObjects.add(rootFolder);
//                }
//            }
//            // IZ#201761  -  Too long refreshing file system after build.
//            // refresh can take a lot of time for slow file systems
//            // so we use worker and schedule it out of build process if auto refresh
//            // is turned off by user in Tools->Options->Misk->Files->Enable auto-scanning of sources
//            final Runnable refresher = new Runnable() {
//                @Override
//                public void run() {
//                    final File[] array = files.toArray(new File[files.size()]);
//                    if (array.length > 0) {
//                        FileUtil.refreshFor(array);
//                    }
//                    if (!fileObjects.isEmpty()) {
//                        for (FileObject fo : fileObjects) {
//                            FileSystemProvider.scheduleRefresh(fo);
//                        }
//                    }
//                }
//            };
//            final Preferences nd = NbPreferences.root().node("org/openide/actions/FileSystemRefreshAction"); // NOI18N
//            boolean manual = (nd != null) && nd.getBoolean("manual", false);// NOI18N
//            if (manual) {
//                RP.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        FileUtil.runAtomicAction(refresher);
//                        fon.onFinish(curPAE);
//                        MakeLogicalViewProvider.refreshBrokenItems(project);
//                    }
//                });
//            } else {
//                FileUtil.runAtomicAction(refresher);
//                fon.onFinish(curPAE);
//                MakeLogicalViewProvider.refreshBrokenItems(project);
//            }
//        } catch (Exception e) {
//            LOGGER.log(Level.INFO, "Cannot refresh project files", e);
//        }
//    }

    /**
     * Checks if given action type can be handled. All registered
     * handler factories are asked.
     *
     * @param conf
     * @param context
     * @param type
     * @return
     */
    public boolean canHandle(MakeConfiguration conf, Lookup context, ProjectActionEvent.Type type) {
        // return true;
//        if (conf != null) {
//            DebuggerChooserConfiguration chooser = conf.getDebuggerChooserConfiguration();
//            CustomizerNode node = chooser.getNode();
//            if (node instanceof ProjectActionHandlerFactory) {
//                if (((ProjectActionHandlerFactory) node).canHandle(type, context, conf)) {
//                    return true;
//                }
//            }
//        }
        for (ProjectActionHandlerFactory factory : handlerFactories) {
            if (factory.canHandle(type, context, conf)) {
                return true;
            }
        }
        return false;
    }
    
    public void clearOutputData() {
        outputStringData = null;
        if (m_AThandler != null) {
            m_AThandler.clearActionOutputStringValues();
        }
    }
    
    public String getOutputData() {
        outputStringData = m_AThandler.getActionOutputStringValues();
        return outputStringData;
    }

    /**
     * Executes an array of project actions asynchronously.
     *
     * @param paes  project actions
     * @param args  Desired program to run and arguments.
     */
    public void fireActionPerformed(ProjectActionEvent[] paes, String[] args) {
        this.command_arguments_for_execute = args;
        m_pEvents = new HandleEvents(paes) ;
        m_pEvents.go(args);
        // JOptionPane.showMessageDialog(null, "===> here is outputStringData in fireActionPerformed: " + outputStringData);
    }
    
    public void cancelAllOperations() {
        if (m_AThandler != null) {
            if (m_AThandler.canCancel()) {
                m_AThandler.cancel();
            }
        }
    }
    

    // public void fireActionPerformed(ProjectActionEvent[] paes, ProjectActionHandler preferredHandler) {
    //     new HandleEvents(paes, preferredHandler).go();
    // }
////////////////////////////////////////////////////////////////////////////////

    private InputOutput mainTab = null;
    private InputOutput runTab = null;
    private HandleEvents mainTabHandler = null;
    private ArrayList<String> tabNames = new ArrayList<String>();
    private final Object lock = new Object();

    private final class ProjectFileOperationsNotifier {
        private final NativeProjectChangeSupport npcs;
        private final ProjectActionEvent startPAE;
        private ProjectActionEvent finishPAE;

        public ProjectFileOperationsNotifier(NativeProjectChangeSupport npcs, ProjectActionEvent startPAE) {
            this.npcs = npcs;
            this.startPAE = startPAE;
        }

        @Override
        public String toString() {
            return "ProjectFileOperationsNotifier{" + "npcs=" + npcs + ", startPAE=" + startPAE + ", finishPAE=" + finishPAE + '}'; // NOI18N
        }
    }
    
    private final class FileOperationsNotifier {
        private final Map<Project, ProjectFileOperationsNotifier> prjNotifier;

        public FileOperationsNotifier(Map<Project, ProjectFileOperationsNotifier> prjNotifier) {
            this.prjNotifier = prjNotifier;
        }

        private void onStart(ProjectActionEvent curPAE) {
            ProjectFileOperationsNotifier notifier = prjNotifier.get(curPAE.getProject());
            if (notifier != null && (notifier.npcs != null) && (curPAE == notifier.startPAE)) {
                notifier.npcs.fireFileOperationsStarted();
            }
        }

        public void onFinish(ProjectActionEvent curPAE) {
            ProjectFileOperationsNotifier notifier = prjNotifier.get(curPAE.getProject());
            if (notifier != null && (notifier.npcs != null) && (curPAE == notifier.finishPAE)) {
                notifier.npcs.fireFileOperationsFinished();
            }
        }

        private void finishAll() {
            for (ProjectFileOperationsNotifier notifier : prjNotifier.values()) {
                if (notifier.npcs != null) {
                    notifier.npcs.fireFileOperationsFinished();
                }
            }
        }

        private boolean isLastExpectedEvent(ProjectActionEvent curPAE) {
            ProjectFileOperationsNotifier notifier = prjNotifier.get(curPAE.getProject());
            if (notifier != null && (notifier.npcs != null) && (curPAE == notifier.finishPAE)) {
                return true;
            }
            return false;
        }
    }
    
    private final class HandleEvents implements ExecutionListener {

        private InputOutput ioTab = null;
        private InputOutput runIoTab = null;
        private final ProjectActionEvent[] paes;
        private String tabName;
        private String tabNameSeq;
        private int currentAction = 0;
        private StopAction sa = null;
        private RerunAction ra = null;
        private TermAction ta = null;
        private List<BuildAction> additional;
        private ProgressHandle progressHandle = null;
        // private final ProjectActionHandler customHandler;
        private ProjectActionHandler currentHandler = null;
        private final boolean reuseTabs;
        private final FileOperationsNotifier fon;
        private String[] command_arguments_for_execute_rerun;

        public HandleEvents(ProjectActionEvent[] paes) {
            this.paes = paes;
            // this.customHandler = customHandler;
            currentAction = 0;
            // reuseTabs = MakeOptions.getInstance().getReuse();
            reuseTabs = true;
            fon = getFileOperationsNotifier(paes);
            if (reuseTabs) {
                synchronized (lock) {
                    if (mainTabHandler == null) {
                        if (mainTab != null) {
                            mainTab.closeInputOutput();
                            mainTab = null;
                        }
                        if (runTab != null) {
                            runTab.closeInputOutput();
                            runTab = null;
                        }
                    }
                    tabName = getTabName(paes);
                    tabNameSeq = tabName;
                    if (tabNames.contains(tabName)) {
                        int seq = 2;
                        while (true) {
                            tabNameSeq = tabName + " #" + seq; // NOI18N
                            if (!tabNames.contains(tabNameSeq)) {
                                break;
                            }
                            seq++;
                        }
                    }
                    tabNames.add(tabNameSeq);
                    ioTab = getIOTab(tabNameSeq, true);
                    if (mainTabHandler == null) {
                        mainTab = ioTab;
                        mainTabHandler = HandleEvents.this;
                    }
                }
            } else {
                tabName = getTabName(paes);
                tabNameSeq = tabName;
                ioTab = getIOTab(tabName, false);
            }
        }
        
        private Action[] getActions(String name) {
            List<Action> list = new ArrayList<Action>();
            if (sa == null) {
                sa = new StopAction(this);
            }
            if (ra == null) {
                ra = new RerunAction(this);
            }
            if (ta == null) {
                ta = new TermAction(this);
            }
            list.add(sa);
            list.add(ra);
            list.add(ta);
            if (additional == null) {
                additional = BuildActionsProvider.getDefault().getActions(name, paes);
            }
            // TODO: actions should have acces to output writer. Action should listen output writer.
            // Provide parameter outputListener for DefaultProjectActionHandler.ProcessChangeListener
            list.addAll(additional);
            return list.toArray(new Action[list.size()]);
        }

        private String getTabName(ProjectActionEvent[] paes) {
            String projectName = ProjectUtils.getInformation(paes[0].getProject()).getDisplayName();
            StringBuilder name = new StringBuilder(projectName);
            name.append(" ("); // NOI18N
            for (int i = 0; i < paes.length; i++) {
                if (i >= 2) {
                    name.append("..."); // NOI18N
                    break;
                }
                name.append(paes[i].getActionName());
                if (i < paes.length - 1) {
                    name.append(", "); // NOI18N
                }
            }
            name.append(")"); // NOI18N
            if (paes.length > 0) {
                MakeConfiguration conf = paes[0].getConfiguration();
                if (!conf.getDevelopmentHost().isLocalhost()) {
                    String hkey = conf.getDevelopmentHost().getHostKey();
                    name.append(" - ").append(hkey); //NOI18N
                }
            }
            return name.toString();
        }

        private InputOutput getTab() {
            return ioTab;
        }

        private InputOutput getRunTab() {
            return runIoTab;
        }

        private ProgressHandle createProgressHandle() {
            ProgressHandle handle = ProgressHandleFactory.createHandle(tabNameSeq, new Cancellable() {

                @Override
                public boolean cancel() {
                    sa.actionPerformed(null);
                    return true;
                }
            }, new AbstractAction() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    getTab().select();
                }
            });
            handle.setInitialDelay(0);
            return handle;
        }

        private ProgressHandle createProgressHandleNoCancel() {
            ProgressHandle handle = ProgressHandleFactory.createHandle(tabNameSeq,
                    new AbstractAction() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            getTab().select();
                        }
                    });
            handle.setInitialDelay(0);
            return handle;
        }

        private InputOutput getIOTab(String name, boolean reuse) {
            Action[] actions = getActions(name);
            InputOutput tab;
            if (reuse) {
                tab = IOProvider.getDefault().getIO(name, false); // This will (sometimes!) find an existing one.
                tab.closeInputOutput(); // Close it...
            }
            tab = IOProvider.getDefault().getIO(name, actions); // Create a new ...
            try {
                tab.getOut().reset();
            } catch (IOException ioe) {
            }

            progressHandle = createProgressHandle();
            progressHandle.start();

            return tab;
        }

        private InputOutput getRunIO(ProjectActionEvent pae, boolean reuse) {
            InputOutput io = null;
            final String TERM_PROVIDER = "Terminal"; // NOI18N
            IOProvider termProvider = IOProvider.get(TERM_PROVIDER);
            if (termProvider != null) {
                String name = getTabName(new ProjectActionEvent[] {pae});
                Action[] actions = getActions(pae.getActionName());
                if (reuse) {
                    synchronized (lock) {
                        // Close buildtab from default provider
                        InputOutput buildtab = IOProvider.getDefault().getIO(name, false); // This will (sometimes!) find an existing one.
                        buildtab.closeInputOutput(); // Close it...
                        io = runIoTab;
                        if (io == null) {
                            io = termProvider.getIO(name, false);
                            io.closeInputOutput();
                        }
                        io = termProvider.getIO(name, actions);
                        runIoTab = io;
                        if (runTab == null && mainTabHandler == this) {
                            runTab = runIoTab;
                        }
                    }
                } else {
                    io = termProvider.getIO(name, actions);
                    runIoTab = io;
                }
            }
            return io;
        }


        private void reRun() {
            currentAction = 0;
            final String[] args_rerun = this.command_arguments_for_execute_rerun;
            getTab().closeInputOutput();
            synchronized (lock) {
                if (runIoTab != null) {
                    runIoTab.closeInputOutput();
                    runIoTab = null;
                }
                tabNames.add(tabNameSeq);
            }
            try {
                getTab().getOut().reset();
            } catch (IOException ioe) {
            }
            progressHandle = createProgressHandle();
            progressHandle.start();
            if (SwingUtilities.isEventDispatchThread()) {
                RequestProcessor.getDefault().post(new Runnable() {

                    @Override
                    public void run() {
                        // JOptionPane.showMessageDialog(null, "===> go args for reRun: " + args_rerun[0]);
                        go(args_rerun);
                    }
                });
            } else {
                // JOptionPane.showMessageDialog(null, "===> go args for reRun B: " + args_rerun[0]);
                go(args_rerun);
            }
        }

        private void stopProgress() {
            progressHandle.finish();
            synchronized (lock) {
                InputOutput tabToClose = ioTab;
                if (tabToClose != null && !tabToClose.isClosed()&& tabToClose.getOut() != null) {
                    PrintWriter out = tabToClose.getOut();
                    // Closing out several times is not harmful 
                    // any attempt to write to the same tab will re-open it
                    // If nothing was written to the tab at all, closing
                    // it's out still will leave it bold ...
                    // So write a space... Should not make any harm..
                    out.write(' ');
                    out.flush();
                    out.close();
                }
            }
            fon.finishAll();
        }
        
        private void go(String[] args) {
            LifecycleManager.getDefault().saveAll();
            currentHandler = null;
            sa.setEnabled(false);
            ra.setEnabled(false);

            if (currentAction >= paes.length) {
                return;
            }

            final ProjectActionEvent pae = paes[currentAction];

            if (!checkProject(pae)) {
                stopProgress();
                return;
            }
            // Will be RUN here.
            Type type = pae.getType();

            InputOutput io = ioTab;
            int consoleType = RunProfile.CONSOLE_TYPE_OUTPUT_WINDOW;
            // int consoleType = pae.getProfile().getConsoleType().getValue(); 
            boolean runInExternalTerminal = consoleType == RunProfile.CONSOLE_TYPE_EXTERNAL;            
            if (!pae.getConfiguration().getDevelopmentHost().isLocalhost() && runInExternalTerminal){
                //set to internal terminal
                consoleType = RunProfile.getDefaultConsoleType();
                // JOptionPane.showMessageDialog(null, "===> go console external");
            }            
            // Always show build log in regular output (IZ 191555)
            // and the same for test run
            // 191589 -  Regression in "C/C++ Unit Tests" framework
            if (type == PredefinedType.BUILD || type == PredefinedType.CLEAN
                    || type == PredefinedType.BUILD_TESTS || type == PredefinedType.TEST) {
                if (consoleType != RunProfile.CONSOLE_TYPE_OUTPUT_WINDOW) {
                    // how can it be?
                    // PAE creation already checked consoleType
                    // probably someone dinamically changed profile
                    assert false : "action " + type + " can not be run in " + pae.getProfile().getConsoleType().getName() + ". Use OutputWindow";
                }
                consoleType = RunProfile.CONSOLE_TYPE_OUTPUT_WINDOW;
                // JOptionPane.showMessageDialog(null, "===> go console internal");
            }

            if (consoleType == RunProfile.CONSOLE_TYPE_DEFAULT) {
                consoleType = RunProfile.getDefaultConsoleType();
                // JOptionPane.showMessageDialog(null, "===> go console default");
            }
            if (consoleType == RunProfile.CONSOLE_TYPE_INTERNAL) {
                io = getRunIO(pae, reuseTabs);
                if (io == null) {
                    io = ioTab;
                } else if (io != ioTab && ioTab != null) {
                    ioTab.getOut().close();
                }
            }

            boolean stopProgress = true;

            try {
                // if (type == PredefinedType.CUSTOM_ACTION && customHandler != null) {
                //     initHandler(customHandler, pae, paes);
                //     customHandler.execute(io);
                // } else {
                    // moved to RemoteBuildProjectActionHandler
                    //if (currentAction == 0 && !checkRemotePath(pae, err, out)) {
                    //    progressHandle.finish();
                    //    return;
                    //}
                    boolean foundFactory = false;
                    for (ProjectActionHandlerFactory factory : handlerFactories) {
                        if (factory.canHandle(pae)) {
                            ProjectActionHandler handler = currentHandler = factory.createHandler();
                            initHandler(handler, pae, paes);
                            handler.execute(io);

                            foundFactory = true;
                            break;
                        }
                    }
                    
                    m_AThandler = null;

                    m_AThandler = new AutoToolsProjectActionHandler();
                    // JOptionPane.showMessageDialog(null, "===> go try init");
                    initHandler(m_AThandler, pae, paes);

                    m_AThandler.execute(io, args);

                    outputStringData = m_AThandler.getActionOutputStringValues();
                    // JOptionPane.showMessageDialog(null, "===> Here is outputStringDataValues in go(args)" + outputStringDataValues);

                    foundFactory = true;

                    stopProgress = !foundFactory;
                // }
            } finally {
                if (stopProgress) {
                    stopProgress();
                }
            }
        }
        
        private void initHandler(ProjectActionHandler handler, ProjectActionEvent pae, ProjectActionEvent[] paes) {
            if (additional == null) {
                additional = BuildActionsProvider.getDefault().getActions(pae.getActionName(), paes);
            }
            List<OutputStreamHandler> streamHandlers = new ArrayList<OutputStreamHandler>();
            for(BuildAction action : additional) {
                if (action instanceof OutputStreamHandler) {
                    streamHandlers.add((OutputStreamHandler) action);
                }
            }
            handler.init(pae, paes, streamHandlers);
            progressHandle.finish();
            progressHandle = handler.canCancel() ? createProgressHandle() : createProgressHandleNoCancel();
            progressHandle.start();
            sa.setEnabled(handler.canCancel());
            handler.addExecutionListener(this);
        }

        public ProjectActionHandler getCurrentHandler() {
            return currentHandler;
        }

        @Override
        public void executionStarted(int pid) {
            if (additional != null) {
                for (BuildAction action : additional) {
                    action.setStep(currentAction);
                    action.executionStarted(pid);
                }
            }
            fon.onStart(paes[currentAction]);
        }

        private FileOperationsNotifier getFileOperationsNotifier(ProjectActionEvent[] paes) {
            Map<Project, ProjectFileOperationsNotifier> prj2Notifier = new HashMap<Project, ProjectFileOperationsNotifier>();
            for (ProjectActionEvent pae : paes) {
                if (isFileOperationsIntensive(pae)) {
                    Project project = pae.getProject();
                    ProjectFileOperationsNotifier notifer = prj2Notifier.get(project);
                    if (notifer == null) {
                        NativeProjectChangeSupport npcs = null;
                        try {
                            npcs = project.getLookup().lookup(NativeProjectChangeSupport.class);
                            if (npcs == null) {
                                NativeProject nativeProject = project.getLookup().lookup(NativeProject.class);
                                if (nativeProject instanceof NativeProjectChangeSupport) {
                                    npcs = (NativeProjectChangeSupport)nativeProject;
                                }
                            }
                        } catch (Exception e) {
                            // This may be ok. The project could have been removed ....
                            System.err.println("getNativeProject " + e);
                        }                        
                        notifer = new ProjectFileOperationsNotifier(npcs, pae);
                        prj2Notifier.put(project, notifer);
                    }
                    notifer.finishPAE = pae;
                }
            }
            return new FileOperationsNotifier(prj2Notifier);
        }
        
        public NativeProjectChangeSupport getNativeProjectChangeSupport(Project project) {
            NativeProject nativeProject = null;
            try {
                nativeProject = project.getLookup().lookup(NativeProject.class);
            } catch (Exception e) {
                // This may be ok. The project could have been removed ....
                System.err.println("getNativeProject " + e);
            }
            if (nativeProject instanceof NativeProjectChangeSupport) {
                return (NativeProjectChangeSupport) nativeProject;
            } else {
                return null;
            }
        }
        
        @Override
        public void executionFinished(int rc) {
            if (additional != null) {
                for (Action action : additional) {
                    ((ExecutionListener) action).executionFinished(rc);
                }
            }
            ProjectActionEvent curPAE = paes[currentAction];
            // Refresh FS
            // refreshProjectFilesOnFinish(curPAE, fon);
            if (currentAction >= paes.length - 1 || rc != 0) {
                synchronized (lock) {
                    if (mainTabHandler == this) {
                        mainTabHandler = null;
                    }
                    tabNames.remove(tabNameSeq);
                }
                sa.setEnabled(false);
                ra.setEnabled(true);
                stopProgress();
                return;
            }

//            // This code is executed in finishing ProjectActionHandler's thread.
//            // Starting next handler in this thread may lead to problems, such as
//            // new threads being created in old handler's thread group, and NetBeans
//            // thinking that old handler has not completed.
//            // So the call to go() is posted to RequestProcessor.
//            if (rc == 0) {
//                currentAction++;
//                RequestProcessor.getDefault().post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        go();
//                    }
//                });
//            }
        }

        /** checks whether the project is ok (not deleted) */
        private boolean checkProject(ProjectActionEvent pae) {
            Project project = pae.getProject();
            if (project != null) { // paranoidal null checks are better than latent NPE :)
                if (CndUtils.isUnitTestMode() || OpenProjects.getDefault().isProjectOpen(project)) { // OpenProjects don't work in test mode
                    FileObject projectDirectory = project.getProjectDirectory();
                    if (projectDirectory != null) {
                        FileObject nbproject = projectDirectory.getFileObject(MakeConfiguration.NBPROJECT_FOLDER); // NOI18N
                        return nbproject != null && nbproject.isValid();
//                        Should use the check below when working with local projects?
//                        if (nbproject != null) {
//                            // I'm more sure in java.io.File.exists() - practice shows that FileObjects might be sometimes cached...
//                            File file = FileUtil.toFile(nbproject);
//                            return file != null && file.exists();
//                        }
                        
                    }
                }
            }
            return false;
        }

    }

    private static final class StopAction extends AbstractAction {

        private HandleEvents handleEvents;

        public StopAction(HandleEvents handleEvents) {
            this.handleEvents = handleEvents;
            putValue(Action.SMALL_ICON, ImageUtilities.loadImageIcon("org/netbeans/modules/cnd/makeproject/ui/resources/stop.png", false)); // NOI18N
            putValue(Action.SHORT_DESCRIPTION, "TargetExecutor.StopAction.stop"); // NOI18N
            //System.out.println("handleEvents 1 " + handleEvents);
            //setEnabled(false); // initially, until ready
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isEnabled()) {
                return;
            }
            setEnabled(false);
            if (handleEvents.getCurrentHandler() != null) {
                handleEvents.getCurrentHandler().cancel();
            }
        }
    }

    private static final class RerunAction extends AbstractAction {

        private HandleEvents handleEvents;
        private String[] reruncommand_arguments_for_execute;

        public RerunAction(HandleEvents handleEvents) {
            this.reruncommand_arguments_for_execute = handleEvents.command_arguments_for_execute_rerun;
            this.handleEvents = handleEvents;
            putValue(Action.SMALL_ICON, ImageUtilities.loadImageIcon("org/netbeans/modules/cnd/makeproject/ui/resources/rerun.png", false)); // NOI18N
            putValue(Action.SHORT_DESCRIPTION, "TargetExecutor.RerunAction.rerun"); // NOI18N
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setEnabled(false);
            handleEvents.reRun();
        }
    }

    private  final class TermAction extends AbstractAction {

        private HandleEvents handleEvents;

        public TermAction(HandleEvents handleEvents) {
            this.handleEvents = handleEvents;
            putValue(Action.SMALL_ICON, ImageUtilities.loadImageIcon("org/netbeans/modules/dlight/terminal/ui/term.png", false)); // NOI18N
            putValue(Action.SHORT_DESCRIPTION, "TargetExecutor.TermAction.text"); // NOI18N
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = handleEvents.paes.length-1; i >=0 ; i--) {
                ProjectActionEvent pae = handleEvents.paes[i];
                String projectName = ProjectUtils.getInformation(pae.getProject()).getDisplayName();
                String dir = pae.getProfile().getRunDirectory();                                
                ExecutionEnvironment env = pae.getConfiguration().getDevelopmentHost().getExecutionEnvironment();
                if (env.isRemote()) {
                    if (RemoteFileUtil.getProjectSourceExecutionEnvironment(pae.getProject()).isLocal()) {
                        PathMap pathMap = RemoteSyncSupport.getPathMap(pae.getProject());
                        if (pathMap != null) {
                            String aDir = pathMap.getRemotePath(dir);
                            if (aDir != null) {
                                dir = aDir;
                            }
                        } else {
                            LOGGER.log(Level.SEVERE, "Path Mapper not found for project {0} - using local path {1}", new Object[]{pae.getProject(), dir}); //NOI18N
                        }
                    }
                }
                TerminalSupport.openTerminal("TargetExecutor.TermAction.tabTitle", env, dir); // NOI18N
                break;
            }
        }
    }
    
//    /** Look up i18n strings here */
//    private static String getString(String s) {
//        return NbBundle.getMessage(ProjectActionSupportLocal.class, s);
//    }
//
//    private static String getString(String s, String... arg) {
//        return NbBundle.getMessage(ProjectActionSupportLocal.class, s, arg);
//    }
}
