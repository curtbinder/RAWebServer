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
		try{
			
			
			RAWebServerApp.ui.ClearLog();
			if(RAWebServerApp.server.StartCOM()){
				RAWebServerApp.server.StartWebServer();
				RAWebServerApp.ui.getMainMenu().enableStart(false);
				RAWebServerApp.ui.getMainMenu().enableStop(true);
			}
			else
			{
				RAWebServerApp.ui.getMainMenu().enableStart(true);
				RAWebServerApp.ui.getMainMenu().enableStop(false);
			}
	    }
		catch(Exception ex)
		{
			RAWebServerApp.ui.Log(ex.getMessage());		
		}
		
	}
}
