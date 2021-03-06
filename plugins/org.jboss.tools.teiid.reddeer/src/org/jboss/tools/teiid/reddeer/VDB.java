package org.jboss.tools.teiid.reddeer;

import org.jboss.reddeer.eclipse.core.resources.ProjectItem;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.tools.teiid.reddeer.condition.IsInProgress;
import org.jboss.tools.teiid.reddeer.view.GuidesView;

/**
 * This class represents a virtual database.
 * 
 * @author apodhrad
 * 
 */
public class VDB {

	private ProjectItem projectItem;
	
	public VDB(ProjectItem projectItem) {
		this.projectItem = projectItem;
	}

	/**
	 * Adds a module to this VDB
	 * 
	 * @param module
	 */
//	public void addModule(String module) {
//		VDBEditor vdbEditor = getVDBEditor();
//		vdbEditor.show();
//		vdbEditor.addModel(getProject().getName(), module);
//		vdbEditor.save();
//		vdbEditor.close();
//	}

	/**
	 * Deployes this VDB
	 */
	public void deployVDB() {
		new WorkbenchShell();
		projectItem.select();
		new ContextMenu("Modeling", "Deploy").select();

		try {
			new WaitUntil(new ShellWithTextIsAvailable("Create VDB Data Source"), TimePeriod.NORMAL);
			new DefaultShell("Create VDB Data Source");
			new PushButton("Create Source").click();
		} catch (Exception e) {
		}

		new WaitWhile(new IsInProgress(), TimePeriod.VERY_LONG);
		new WaitWhile(new JobIsRunning(), TimePeriod.VERY_LONG);
	}

	/**
	 * Executes this VDB
	 */
	public void executeVDB() {
		new WorkbenchShell();
		projectItem.select();
		new ContextMenu("Modeling", "Execute VDB").select();
		new WaitWhile(new IsInProgress(), TimePeriod.VERY_LONG);
		new WaitWhile(new JobIsRunning(), TimePeriod.VERY_LONG);
	}
	
	/**
	 * Executes this VDB via Modelling actions guides
	 * @param viaGuides
	 */
	public void executeVDB(boolean viaGuides){
		if (viaGuides){
			projectItem.select();
			new GuidesView().chooseAction("Model JDBC Source", "Execute VDB");
			new DefaultShell("Execute VDB");
			//VDB should be selected from previous step, if not:
			if (! new DefaultText(0).getText().isEmpty()){
				new SWTWorkbenchBot().button("OK").click();
			} else {
				new PushButton("Cancel").click();
				executeVDB();
			}
			
		} else {
			executeVDB();
		}
		//new WaitWhile(new IsInProgress(), TimePeriod.VERY_LONG);
		//new WaitWhile(new JobIsRunning(), TimePeriod.VERY_LONG);
	}
}
