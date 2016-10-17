package de.jcup.eclipse.plugin.greenmonkey.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.jcup.eclipse.plugin.greenmonkey.Activator;
import de.jcup.eclipse.plugin.greenmonkey.decorator.FilePathLabelDecorator;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private Group infoGroup;
	private BooleanFieldEditor fileColoringBooleanFieldEditor;

	public PreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
//		setDescription("Green Monkey");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {
		
		fileColoringBooleanFieldEditor = new BooleanFieldEditor(PreferenceConstants.P_BOOLEAN__FILE_COLORING_ENABLED,
				"&Enable coloring", getFieldEditorParent()) {
			@Override
			protected void valueChanged(boolean oldValue, boolean newValue) {
				setInfoGroupEnabled(newValue);
				super.valueChanged(oldValue, newValue);
			}

		};
		addField(fileColoringBooleanFieldEditor);

		infoGroup = new Group(getFieldEditorParent(), SWT.BORDER_SOLID);
		infoGroup.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 4, 1));

		addHeadline("Main folders", infoGroup);
		addField(new ColorFieldEditor(PreferenceConstants.P_COLOR_MAINFOLDERS, "Foreground color",
				infoGroup));
		addField(new ColorFieldEditor(PreferenceConstants.P_COLOR_MAINFOLDERS_BG, "Background color",
				infoGroup));
		appendBlankSeparator(infoGroup);
		
		addHeadline("Test folders:", infoGroup);
		addField(new ColorFieldEditor(PreferenceConstants.P_COLOR_TESTFOLDERS, "Foreground color",
				infoGroup));
		addField(new ColorFieldEditor(PreferenceConstants.P_COLOR_TESTFOLDERS_BG, "Background color",
				infoGroup));
		appendBlankSeparator(infoGroup);
		
		addHeadline("Test packages:", infoGroup);
		addField(new ColorFieldEditor(PreferenceConstants.P_COLOR_TESTPACKAGES, "Foreground color",
				infoGroup));
		addField(new ColorFieldEditor(PreferenceConstants.P_COLOR_TESTPACKAGES_BG, "Background color",
				infoGroup));
		appendBlankSeparator(infoGroup);
		
		addHeadline("Test files:", infoGroup);
		addField(new ColorFieldEditor(PreferenceConstants.P_COLOR_TESTFILES, "Foreground color", infoGroup));
		addField(new ColorFieldEditor(PreferenceConstants.P_COLOR_TESTFILES_BG, "Background color",
				infoGroup));
		appendBlankSeparator(infoGroup);
		
		Label labelInfo = new Label(infoGroup, SWT.NONE);
		setLabelItalic(labelInfo);
		labelInfo.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 2, 1));
		labelInfo.setText("Information: BLACK - will be ignored on rendering. So use it as turn off flag!");

		/*
		 * update info group children status - when coloring not enabled
		 * childrens are disabled
		 */
		boolean valueColoringEnabled = getPreferenceStore()
				.getBoolean(PreferenceConstants.P_BOOLEAN__FILE_COLORING_ENABLED);
		setInfoGroupEnabled(valueColoringEnabled);
	}

	private void addHeadline(String string, Composite parent) {
		addLabel(string, parent);
		appendLineSeparator(parent);
	}

	private void addLabel(String string, Composite parent) {
		addLabel(string, parent, false);
	}

	private void addLabel(String string, Composite parent, boolean italic) {
		Label labelInfo = new Label(parent, SWT.NONE);
		if (italic) {
			setLabelItalic(labelInfo);
		}
		labelInfo.setText(string);
		labelInfo.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 2, 1));

	}

	private void setInfoGroupEnabled(boolean newValue) {
		if (infoGroup != null) {
			// infoGroup.setEnabled(newValue);
			for (Control c : infoGroup.getChildren()) {
				c.setEnabled(newValue);
			}
		}
	}

	private void setLabelItalic(Label label) {
		FontData fontData = label.getFont().getFontData()[0];
		Font font = new Font(label.getDisplay(), new FontData(fontData.getName(), fontData.getHeight(), SWT.ITALIC));
		label.setFont(font);
	}

	private void appendBlankSeparator() {
		appendBlankSeparator(getFieldEditorParent());
	}
	
	private void appendBlankSeparator(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 2, 1));
	}

	private void appendLineSeparator() {
		appendLineSeparator(getFieldEditorParent());
	}

	private void appendLineSeparator(Composite parent) {
		Label label = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1));
	}

	@Override
	public boolean performOk() {
		boolean result = super.performOk();
		/* always update label decorator colors */
		FilePathLabelDecorator.onPreferencesChanged();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
	}

}