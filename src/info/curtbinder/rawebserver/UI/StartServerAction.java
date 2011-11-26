package info.curtbinder.rawebserver.UI;

import info.curtbinder.rawebserver.Classes.RAWebServerApp;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class StartServerAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	public StartServerAction() {
		putValue(NAME, "Start Server");
	}
	
	@Override
	public void actionPerformed ( ActionEvent arg0 ) {
		RAWebServerApp.server.start();
		RAWebServerApp.ui.getMainMenu().enableStart(false);
		RAWebServerApp.ui.getMainMenu().enableStop(true);
	}
}
