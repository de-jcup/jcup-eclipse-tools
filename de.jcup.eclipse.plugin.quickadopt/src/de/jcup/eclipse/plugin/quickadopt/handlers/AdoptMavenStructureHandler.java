package de.jcup.eclipse.plugin.quickadopt.handlers;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import de.jcup.eclipse.plugin.quickadopt.Activator;
import de.jcup.quickadopt.MavenStructureAdopter;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class AdoptMavenStructureHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		//
		IWorkbench workbench = Activator.getDefault().getWorkbench();
		window = workbench.getActiveWorkbenchWindow();
		ISelection someSelection = window.getActivePage().getSelection();
		if (someSelection instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) someSelection;
			Object element = selection.getFirstElement();
			if (element instanceof IProject) {
				IProject project = (IProject) element;
				try {
					handleProject(project);
				} catch (Exception e) {
					throw new ExecutionException("Cannot quick adopt project:" + project.getName(), e);
				}
				return null;
			}
		}
		MessageDialog.openInformation(window.getShell(), "Quickadopt",
				"Please select a project before executiong the maven structure adopt action...");

		return null;
	}

	private void handleProject(IProject project) throws Exception {
		System.out.println("project:" + project.getName());
		IPath path = project.getLocation();
		IFileStore fileStore = FileBuffers.getFileStoreAtLocation(path);

		File projectFolder = fileStore.toLocalFile(EFS.NONE, null);
		MavenStructureAdopter adopter = new MavenStructureAdopter();
		adopter.adopt(project.getName(), projectFolder);

	}
}
