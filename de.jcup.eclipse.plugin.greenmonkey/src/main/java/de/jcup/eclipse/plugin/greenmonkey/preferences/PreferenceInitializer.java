package de.jcup.eclipse.plugin.greenmonkey.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;

import de.jcup.eclipse.plugin.greenmonkey.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	 // http://www.colorspire.com/rgb-color-wheel/

    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        store.setDefault(PreferenceConstants.P_BOOLEAN__USE_LONG_NAMES_FOR_CLIPBOARD, true);
        store.setDefault(PreferenceConstants.P_BOOLEAN__FILE_COLORING_ENABLED, true);

        /* PreferenceConverter does NOT work for colors ?!!? */
        PreferenceConverter.setDefault(store, PreferenceConstants.P_COLOR_TESTFILES, new RGB(200, 242, 200));
        PreferenceConverter.setDefault(store, PreferenceConstants.P_COLOR_TESTFOLDERS, new RGB(232, 245, 232)); /*
                                                                                                                 * E8F5E8
                                                                                                                 */
        PreferenceConverter.setDefault(store, PreferenceConstants.P_COLOR_MAINFOLDERS, new RGB(244, 238, 231)); /*
                                                                                                                 * nice
                                                                                                                 * bright
                                                                                                                 * orange
                                                                                                                 */

    }

}
