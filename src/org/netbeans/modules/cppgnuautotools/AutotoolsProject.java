/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.cppgnuautotools;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.spi.project.ActionProvider;
import org.netbeans.spi.project.CopyOperationImplementation;
import org.netbeans.spi.project.DeleteOperationImplementation;
import org.netbeans.spi.project.ProjectState;
import org.netbeans.spi.project.ui.CustomizerProvider;
import org.netbeans.spi.project.ui.support.DefaultProjectOperations;
import org.openide.filesystems.FileObject;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author derby
 */
public class AutotoolsProject implements Project {
    private final FileObject projectDir;
    private final ProjectState state;
    private Lookup lkp;

    public AutotoolsProject(FileObject projectDir, ProjectState state) {
        this.projectDir = projectDir;
        this.state = state;
    }
    
    @Override
    public FileObject getProjectDirectory() {
        return projectDir;
    }

    FileObject getTextFolder(boolean create) {
        FileObject result =
                projectDir.getFileObject(AutotoolsProjectFactory.PROJECT_FILE);
//        if (result == null && create) {
//            try {
//                result = projectDir.createFolder(AutotoolsProjectFactory.PROJECT_FILE);
//            } catch (IOException ioe) {
//                Exceptions.printStackTrace(ioe);
//            }
//        }
        return result;
    }
    
    //The project type's capabilities are registered in the project's lookup:
    @Override
    public Lookup getLookup() {
        if (lkp == null) {
            lkp = Lookups.fixed(new Object[]{
                        state, //allow outside code to mark the project as needing saving
                        new ActionProviderImpl(), //Provides standard actions like Build and Clean
                        new Info(), //Project information implementation
                        new AutotoolsDeleteOperation(),
                        new AutotoolsCopyOperation(this),
                        new AutotoolsProjectLogicalView(this), //Logical view of project implementation
                        new CustomizerProvider() {
                            @Override
                            public void showCustomizer() {
                                JOptionPane.showMessageDialog(
                                        null, 
                                        "customizer for " + 
                                        getProjectDirectory().getName() +
                                        " Configure Project, close and New Existing C/C++ Project");
                            }
                        }
                    });
        }
        return lkp;
    }

    private final class ActionProviderImpl implements ActionProvider  {

        private String[] supported = new String[]{
            ActionProvider.COMMAND_DELETE,
            ActionProvider.COMMAND_COPY,
        };

        @Override
        public String[] getSupportedActions() {
            return supported;
        }

        @Override
        public void invokeAction(String string, Lookup lookup) throws IllegalArgumentException {
            if (string.equalsIgnoreCase(ActionProvider.COMMAND_DELETE)) {
                DefaultProjectOperations.performDefaultDeleteOperation(AutotoolsProject.this);
            }
            if (string.equalsIgnoreCase(ActionProvider.COMMAND_COPY)) {
                DefaultProjectOperations.performDefaultCopyOperation(AutotoolsProject.this);
            }
        }

        @Override
        public boolean isActionEnabled(String command, Lookup lookup) throws IllegalArgumentException {
            if ((command.equals(ActionProvider.COMMAND_DELETE))) {
                return true;
            } else if ((command.equals(ActionProvider.COMMAND_COPY))) {
                return true;
            } else {
                throw new IllegalArgumentException(command);
            }
        }
    }

    private final class AutotoolsDeleteOperation implements DeleteOperationImplementation  {

        @Override
        public void notifyDeleting() throws IOException {
        }

        @Override
        public void notifyDeleted() throws IOException {
        }

        @Override
        public List<FileObject> getMetadataFiles() {
            List<FileObject> dataFiles = new ArrayList<FileObject>();
            return dataFiles;
        }

        @Override
        public List<FileObject> getDataFiles() {
            List<FileObject> dataFiles = new ArrayList<FileObject>();
            return dataFiles;
        }
    }

    private final class AutotoolsCopyOperation implements CopyOperationImplementation  {

        private final AutotoolsProject project;
        private final FileObject projectDir;

        public AutotoolsCopyOperation(AutotoolsProject project) {
            this.project = project;
            this.projectDir = project.getProjectDirectory();
        }

        @Override
        @SuppressWarnings("unchecked")
        public List<FileObject> getMetadataFiles() {
            return Collections.EMPTY_LIST;
        }

        @Override
        @SuppressWarnings("unchecked")
        public List<FileObject> getDataFiles() {
            return Collections.EMPTY_LIST;
        }

        @Override
        public void notifyCopying() throws IOException {
        }

        @Override
        public void notifyCopied(Project arg0, File arg1, String arg2) throws IOException {
        }
    }

    private final class Info implements ProjectInformation  {

        @Override
        public Icon getIcon() {
            return new ImageIcon(ImageUtilities.loadImage(
                    "org/netbeans/modules/cppgnuautotools/ATool16b.png"));
        }

        @Override
        public String getName() {
            return getProjectDirectory().getName();
        }

        @Override
        public String getDisplayName() {
            return getName();
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener pcl) {
            //do nothing, won't change
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener pcl) {
            //do nothing, won't change
        }

        @Override
        public Project getProject() {
            return AutotoolsProject.this;
        }
    }

}
