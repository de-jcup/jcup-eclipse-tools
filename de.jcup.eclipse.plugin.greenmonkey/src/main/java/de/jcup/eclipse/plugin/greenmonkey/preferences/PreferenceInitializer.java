package de.jcup.eclipse.plugin.greenmonkey.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;

import de.jcup.eclipse.plugin.greenmonkey.Activator;
import de.jcup.eclipse.plugin.greenmonkey.ColorConstants;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	// http://www.colorspire.com/rgb-color-wheel/

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_BOOLEAN__FILE_COLORING_ENABLED, true);

		/* PreferenceConverter does NOT work for colors ?!!? */
		PreferenceConverter.setDefault(store, PreferenceConstants.P_COLOR_TESTFILES, ColorConstants.DARK_GREEN);
		PreferenceConverter.setDefault(store, PreferenceConstants.P_COLOR_TESTFILES_BG, ColorConstants.IGNORED);
		
		PreferenceConverter.setDefault(store, PreferenceConstants.P_COLOR_TESTPACKAGES, ColorConstants.GREEN);
		PreferenceConverter.setDefault(store, PreferenceConstants.P_COLOR_TESTPACKAGES_BG, ColorConstants.IGNORED);
		
		PreferenceConverter.setDefault(store, PreferenceConstants.P_COLOR_TESTFOLDERS, ColorConstants.IGNORED); 
		PreferenceConverter.setDefault(store, PreferenceConstants.P_COLOR_TESTFOLDERS_BG, ColorConstants.IGNORED); 
						
		PreferenceConverter.setDefault(store, PreferenceConstants.P_COLOR_MAINFOLDERS, ColorConstants.IGNORED);
		PreferenceConverter.setDefault(store, PreferenceConstants.P_COLOR_MAINFOLDERS_BG, ColorConstants.IGNORED);
				

	}

}
