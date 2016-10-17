package de.jcup.eclipse.plugin.greenmonkey.decorator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.RGB;

import de.jcup.eclipse.plugin.greenmonkey.Activator;
import de.jcup.eclipse.plugin.greenmonkey.ColorManager;
import de.jcup.eclipse.plugin.greenmonkey.preferences.PreferenceConstants;

public class FilePathLabelDecorator implements ILightweightLabelDecorator {

    private static final String SRC_TEST_RESOURCES = "/src/test/resources";
    private static final String SRC_TEST_JAVA = "/src/test/java";
    private static final String SRC_MAIN_WEBAPP = "/src/main/webapp";
    private static final String SRC_MAIN_RESOURCES = "/src/main/resources";
    private static final String SRC_MAIN_JAVA = "/src/main/java";
    private static RGB rgbTestFolder;
    private static RGB rgbTestFile;
    private static RGB rgbMainFolder;

    private Collection<ILabelProviderListener> listeners;

    private static List<FilePathLabelDecorator> instances = new ArrayList<FilePathLabelDecorator>();
    private static boolean fileColoringEnabled;
	private static RGB rgbMainFolderBG;
	private static RGB rgbTestPackageBG;
	private static RGB rgbTestPackage;
	private static RGB rgbTestFolderBG;
	private static RGB rgbTestFileBG;

    public FilePathLabelDecorator() {
        listeners = new ArrayList<ILabelProviderListener>();
        instances.add(this);
    }

    public static void onPreferencesChanged() {
        reloadPreferences();

        /* inform all listeners of all instances */
        for (FilePathLabelDecorator decorator : instances) {
            decorator.triggerLabelProviderChangeEvent();
        }
    }

    private void triggerLabelProviderChangeEvent() {
        LabelProviderChangedEvent event = new LabelProviderChangedEvent(this);
        for (ILabelProviderListener listener : listeners) {
            listener.labelProviderChanged(event);
        }

    }

    private static void reloadPreferences() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        
        rgbTestFile = PreferenceConverter.getColor(store, PreferenceConstants.P_COLOR_TESTFILES);
        rgbTestFileBG = PreferenceConverter.getColor(store, PreferenceConstants.P_COLOR_TESTFILES_BG);

        rgbTestFolder = PreferenceConverter.getColor(store, PreferenceConstants.P_COLOR_TESTFOLDERS);
        rgbTestFolderBG = PreferenceConverter.getColor(store, PreferenceConstants.P_COLOR_TESTFOLDERS_BG);
        
        rgbTestPackage = PreferenceConverter.getColor(store, PreferenceConstants.P_COLOR_TESTPACKAGES);
        rgbTestPackageBG = PreferenceConverter.getColor(store, PreferenceConstants.P_COLOR_TESTPACKAGES_BG);
        
        rgbMainFolder = PreferenceConverter.getColor(store, PreferenceConstants.P_COLOR_MAINFOLDERS);
        rgbMainFolderBG = PreferenceConverter.getColor(store, PreferenceConstants.P_COLOR_MAINFOLDERS_BG);
        
        fileColoringEnabled = store.getBoolean(PreferenceConstants.P_BOOLEAN__FILE_COLORING_ENABLED);
    }

    @Override
    public void decorate(Object element, IDecoration decoration) {
        if (rgbTestFile == null) {
            reloadPreferences();
        }
        if (!fileColoringEnabled) {
            return;
        }
        ColorManager cm = Activator.getDefault().getColorManager();
        /* ------------------------------------------------------------- */
        /* ----------------------Packages------------------------------ */
        /* ------------------------------------------------------------- */
        if (element instanceof IMember){
        	/* member found)
        	 */
        	IMember member = (IMember) element;
        	String elementName = member.getElementName();
        	
        }
        if (element instanceof IPackageFragment) {
            /* we do NOT render packages with colors - too much.. */
        	IPackageFragment fragment = (IPackageFragment) element;
        	IResource resource = fragment.getAdapter(IResource.class);
        	if (resource!=null){
        		String portableString = resource.getFullPath().toPortableString();
        		if (isInTestFolderPath(portableString)){
        			changeColor(decoration, cm, rgbTestPackage, rgbTestPackageBG);
        		}
        	}
           return;
        }

        /* ------------------------------------------------------------- */
        /* ----------------------Resolve as resource-------------------- */
        /* ------------------------------------------------------------- */
        if (!(element instanceof IResource)) {
            if (element instanceof IAdaptable) {
                IAdaptable adaptable = (IAdaptable) element;
                element = adaptable.getAdapter(IResource.class);
            } else {
                return;
            }
        }
        /* ------------------------------------------------------------- */
        /* ----------------------Resources------------------------------ */
        /* ------------------------------------------------------------- */
        if (element instanceof IResource) {
            IResource resource = (IResource) element;
            if (isOneOfTheTestFolders(resource)) {
                changeColor(decoration, cm, rgbTestFolder, rgbTestFolderBG);
            } else if (isTestFile(resource)) {
                changeColor(decoration, cm, rgbTestFile, rgbTestFileBG);
            } else if (isOneOfTheMainFolders(resource)) {
                changeColor(decoration, cm, rgbMainFolder, rgbMainFolderBG);
            }
        }

    }

    private void changeColor(IDecoration decoration, ColorManager cm, RGB color, RGB backgroundColor) {
        if (isColorAccepted(color)) {
        	decoration.setForegroundColor(cm.getColor(color));
        }
        
        if (isColorAccepted(backgroundColor)) {
            decoration.setBackgroundColor(cm.getColor(backgroundColor));
        }
    }

    private boolean isTestFile(IResource resource) {
        if (resource instanceof IFolder) {
            return false;
        }
        String portableString = resource.getFullPath().toPortableString();
        return isInTestFolderPath(portableString);
    }

    private boolean isOneOfTheTestFolders(IResource resource) {
        String portableString = resource.getFullPath().toPortableString();
        return isOneOfTheTestFolders(portableString);
    }

    private boolean isOneOfTheMainFolders(IResource resource) {
        String portableString = resource.getFullPath().toPortableString();
        return isOneOfTheMainFolders(portableString);
    }

    private boolean isInTestFolderPath(String path) {
        boolean is = false;
        is = is || path.contains(SRC_TEST_JAVA); /* MAVEN way */
        is = is || path.contains(SRC_TEST_RESOURCES); /* MAVEN way */
        return is;
    }

    private boolean isOneOfTheTestFolders(String path) {
        boolean is = false;
        is = is || path.endsWith(SRC_TEST_JAVA); /* MAVEN way */
        is = is || path.endsWith(SRC_TEST_RESOURCES); /* MAVEN way */
        return is;
    }

    private boolean isOneOfTheMainFolders(String path) {
        boolean is = false;
        is = is || path.endsWith(SRC_MAIN_JAVA); /* MAVEN way */
        is = is || path.endsWith(SRC_MAIN_RESOURCES); /* MAVEN way */
        is = is || path.endsWith(SRC_MAIN_WEBAPP); /* MAVEN way */
        return is;
    }

    @Override
    public void addListener(ILabelProviderListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(ILabelProviderListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void dispose() {
        instances.remove(this);
    }

    private boolean isColorAccepted(RGB rgb) {
        int combined = rgb.red + rgb.blue + rgb.green;
        if (combined == 0) {
            /* black - not accepted */
            return false;
        }
        return true;
    }

    @Override
    public boolean isLabelProperty(Object element, String data) {
        return false;
    }

}
