package info.curtbinder.rawebserver.UI;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class ExitAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExitAction() {
		putValue(NAME, "Exit");
		// TODO add in Close icon
	}
	
	@Override
	public void actionPerformed ( ActionEvent arg0 ) {	
		//RAWebServerApp.server.stop();		
		System.exit( 0 );
	}
}
