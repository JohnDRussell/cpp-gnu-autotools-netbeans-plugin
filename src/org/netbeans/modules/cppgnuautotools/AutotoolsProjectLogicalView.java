/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.cppgnuautotools;

import java.awt.Image;
import javax.swing.Action;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.project.Project;
import static org.netbeans.modules.cppgnuautotools.OSReadAbsActTimer.ACLOCAL_RUNNABLE;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.FilterNode;
import org.openide.nodes.FilterNode.Children;
import org.openide.nodes.Node;
import org.openide.util.ContextAwareAction;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author derby
 */
public class AutotoolsProjectLogicalView implements LogicalViewProvider {
    
    @StaticResource() 
    public static final String AUTOTOOLSGNU_ICON = "org/netbeans/modules/cppgnuautotools/ATool16b.png";

    private final AutotoolsProject project;
    
    private final String computer_arch = System.getProperty("os.arch");
    
    private static Boolean Is32Bit = true;

    public AutotoolsProjectLogicalView(AutotoolsProject project) {
        this.project = project;
    }

    @Override
    public Node createLogicalView() {
        try {
            //Obtain the project directory's node:
            FileObject projectDirectory = project.getProjectDirectory();
            DataFolder projectFolder = DataFolder.findFolder(projectDirectory);
            Node nodeOfProjectFolder = projectFolder.getNodeDelegate();
            
            /**
             * If you debug to right here on 
             * Ubuntu 14.04 32 bit virtual machine
             * you will get i386.
             * According to StackOverflow web site
             * If the Ubuntu 14.04 is 64 bit
             * this will return amd64.  
             * So, I am trying both.
             */
            if (computer_arch.compareToIgnoreCase("i386") != 0)
            {
                Is32Bit = false;
            }
            if (computer_arch.compareToIgnoreCase("amd64") == 0)
            {
                Is32Bit = false;
            }
            
            //Decorate the project directory's node:
            return new ProjectNode(nodeOfProjectFolder, project);
        } catch (DataObjectNotFoundException donfe) {
            Exceptions.printStackTrace(donfe);
            //Fallback-the directory couldn't be created -
            //read-only filesystem or something evil happened
            return new AbstractNode(Children.LEAF);
        }
    }
    
    private class PrjMenuLibtoolizeActionInternal extends OSReadAbsActTimer implements ContextAwareAction {
        final AutotoolsProject project;
        
        PrjMenuLibtoolizeActionInternal(AutotoolsProject project, String label) {
            super((Project)project, Is32Bit, label, LIBTOOLIZE_RUNNABLE);
            this.project = project;
        }
        @Override public boolean isEnabled() {
            // return false;
            return true;
        }
        @Override public Action createContextAwareInstance(Lookup actionContext) {
            return this;
        }
    }
    
    public Action setAsPrjMenuLibtoolizeAction(AutotoolsProject project) {
        return new PrjMenuLibtoolizeActionInternal(project, "Run Libtoolize");
    }

    private class PrjMenuACLocalActionInternal extends OSReadAbsActTimer implements ContextAwareAction {
        final AutotoolsProject project;
        
        PrjMenuACLocalActionInternal(AutotoolsProject project, String label) {
            super((Project)project, Is32Bit, label, ACLOCAL_RUNNABLE);
            this.project = project;
        }
        @Override public boolean isEnabled() {
            // return false;
            return true;
        }
        @Override public Action createContextAwareInstance(Lookup actionContext) {
            return this;
        }
    }
    
    public Action setAsPrjMenuACLocalAction(AutotoolsProject project) {
        return new PrjMenuACLocalActionInternal(project, "Run ACLocal");
    }

    private class PrjMenuAutoHeaderActionInternal extends OSReadAbsActTimer implements ContextAwareAction {
        final AutotoolsProject project;
        
        PrjMenuAutoHeaderActionInternal(AutotoolsProject project, String label) {
            super((Project)project, Is32Bit, label, AUTOHEADER_RUNNABLE);
            this.project = project;
        }
        @Override public boolean isEnabled() {
            // return false;
            return true;
        }
        @Override public Action createContextAwareInstance(Lookup actionContext) {
            return this;
        }
    }
    
    public Action setAsPrjMenuAutoHeaderAction(AutotoolsProject project) {
        return new PrjMenuAutoHeaderActionInternal(project, "Run AutoHeader");
    }

    private class PrjMenuAutoMakeActionInternal extends OSReadAbsActTimer implements ContextAwareAction {
        final AutotoolsProject project;
        
        PrjMenuAutoMakeActionInternal(AutotoolsProject project, String label) {
            super((Project)project, Is32Bit, label, AUTOMAKE_RUNNABLE);
            this.project = project;
        }
        @Override public boolean isEnabled() {
            // return false;
            return true;
        }
        @Override public Action createContextAwareInstance(Lookup actionContext) {
            return this;
        }
    }
    
    public Action setAsPrjMenuAutoMakeAction(AutotoolsProject project) {
        return new PrjMenuAutoMakeActionInternal(project, "Run AutoMake");
    }

    private class PrjMenuAutoConfActionInternal extends OSReadAbsActTimer implements ContextAwareAction {
        final AutotoolsProject project;
        
        PrjMenuAutoConfActionInternal(AutotoolsProject project, String label) {
            super((Project)project, Is32Bit, label, AUTOCONF_RUNNABLE);
            this.project = project;
        }
        @Override public boolean isEnabled() {
            // return false;
            return true;
        }
        @Override public Action createContextAwareInstance(Lookup actionContext) {
            return this;
        }
    }
    
    public Action setAsPrjMenuAutoConfAction(AutotoolsProject project) {
        return new PrjMenuAutoConfActionInternal(project, "Run AutoConf");
    }

    private class PrjMenuConfigureActionInternal extends OSReadAbsActTimer implements ContextAwareAction {
        final AutotoolsProject project;
        
        PrjMenuConfigureActionInternal(AutotoolsProject project, String label) {
            super((Project)project, Is32Bit, label, CONFIGURE_RUNNABLE);
            this.project = project;
        }
        @Override public boolean isEnabled() {
            // return false;
            return true;
        }
        @Override public Action createContextAwareInstance(Lookup actionContext) {
            return this;
        }
    }
    
    public Action setAsPrjMenuConfigureAction(AutotoolsProject project) {
        return new PrjMenuConfigureActionInternal(project, "Run ./configure");
    }

    private class PrjMenuCleanAutotoolsActionInternal extends OSReadAbsActTimer implements ContextAwareAction {
        final AutotoolsProject project;
        
        PrjMenuCleanAutotoolsActionInternal(AutotoolsProject project, String label) {
            super((Project)project, Is32Bit, label, CLEAN_AUTOTOOLS_RUNNABLE);
            this.project = project;
        }
        @Override public boolean isEnabled() {
            // return false;
            return true;
        }
        @Override public Action createContextAwareInstance(Lookup actionContext) {
            return this;
        }
    }
    
    public Action setAsPrjMenuCleanAutotoolsAction(AutotoolsProject project) {
        return new PrjMenuCleanAutotoolsActionInternal(project, "Run Clean Autotools");
    }

    private final class ProjectNode extends FilterNode {

        final AutotoolsProject project;

        public ProjectNode(Node node, AutotoolsProject project) 
            throws DataObjectNotFoundException {
            super(node,
                    new FilterNode.Children(node),
                    new ProxyLookup(
                    new Lookup[]{
                        Lookups.singleton(project),
                        node.getLookup()
                    }));
            this.project = project;
        }

        @Override
        public Action[] getActions(boolean arg0) {
            return new Action[]{
                        CommonProjectActions.newFileAction(),
                        CommonProjectActions.copyProjectAction(),
                        CommonProjectActions.deleteProjectAction(),
                        CommonProjectActions.customizeProjectAction(),
                        CommonProjectActions.customizeProjectAction(),
                        CommonProjectActions.closeProjectAction(),
                        setAsPrjMenuLibtoolizeAction(project),
                        setAsPrjMenuACLocalAction(project),
                        setAsPrjMenuAutoHeaderAction(project),
                        setAsPrjMenuAutoMakeAction(project),
                        setAsPrjMenuAutoConfAction(project),
                        setAsPrjMenuConfigureAction(project),
                        setAsPrjMenuCleanAutotoolsAction(project)
                    };
        }

        @Override
        public Image getIcon(int type) {
            return ImageUtilities.loadImage(AUTOTOOLSGNU_ICON);
        }

        @Override
        public Image getOpenedIcon(int type) {
            return getIcon(type);
        }

        @Override
        public String getDisplayName() {
            return project.getProjectDirectory().getName();
        }

    }

    @Override
    public Node findPath(Node root, Object target) {
        //leave unimplemented for now
        return null;
    }
   
}
