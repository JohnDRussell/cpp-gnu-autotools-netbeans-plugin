/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.cppgnuautotools;

import java.io.IOException;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;

/**
 *
 * @author derby
 */
@org.openide.util.lookup.ServiceProvider(service=ProjectFactory.class)
public class AutotoolsProjectFactory implements ProjectFactory {
    
    public static final String PROJECT_AUTHORS_FILE = "AUTHORS";
    public static final String PROJECT_COPYING_FILE = "COPYING";
    public static final String PROJECT_FILE = "Makefile.am";
    public static final String PROJECT_NOT_FILE = "Makefile";
    public static final String PROJECT_CONF_FILE = "configure.ac";
    public static final String PROJECT_NOT_CONF_FILE = "configure";

    //Specifies when a project is a project, i.e.,
    //if the project directory "texts" is present:
    @Override
    public boolean isProject(FileObject projectDirectory) {
        if ((projectDirectory.getFileObject(PROJECT_FILE) != null)
            && (projectDirectory.getFileObject(PROJECT_CONF_FILE) != null)
            && (projectDirectory.getFileObject(PROJECT_NOT_FILE) == null)
            && (projectDirectory.getFileObject(PROJECT_NOT_CONF_FILE) == null)
            && (projectDirectory.getFileObject(PROJECT_AUTHORS_FILE) != null)
                && (projectDirectory.getFileObject(PROJECT_COPYING_FILE) != null)) {
            return true;
        }
        return false;
    }

    //Specifies when the project will be opened, i.e.,
    //if the project exists:
    @Override
    public Project loadProject(FileObject dir, ProjectState state) throws IOException {
        return isProject(dir) ? new AutotoolsProject(dir, state) : null;
    }

    @Override
    public void saveProject(final Project project) throws IOException, ClassCastException {
        FileObject projectRoot = project.getProjectDirectory();
        if (projectRoot.getFileObject(PROJECT_FILE) == null) {
            throw new IOException("Project dir " + projectRoot.getPath() +
                    " deleted," +
                    " cannot save project");
        }
        //Force creation of the texts dir if it was deleted:
        ((AutotoolsProject) project).getTextFolder(true);
    }
    
}
