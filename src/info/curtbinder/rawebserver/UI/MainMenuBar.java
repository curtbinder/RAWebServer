package info.curtbinder.rawebserver.UI;

import info.curtbinder.rawebserver.Classes.Globals;
import info.curtbinder.rawebserver.Classes.RAWebServerApp;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	private ExitAction exitAction = new ExitAction();
	private AboutAction aboutAction = new AboutAction();

	public class AboutAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public AboutAction () {
			putValue( NAME, "About" );
			// TODO add in about icon
		}

		public void actionPerformed ( ActionEvent ae ) {
			AboutDialog d =
					new AboutDialog( RAWebServerApp.ui, new ImageIcon(
						MainFrame.class.getResource( Globals.appIconName ) ),
						"RA WebServer", "Relays web requests to RA Controller" );
			d.setAppVersion(	Globals.versionMajor, Globals.versionMinor,
								Globals.versionRevision, Globals.versionBuild );
			d.setCopyright( Globals.copyrightInfo );
			d.setBanner( new ImageIcon( MainFrame.class
					.getResource( Globals.bannerIconName ) ) );
			d.setURL( Globals.url );
			d.setCreditors(Globals.creditList);
			d.setLicense(Globals.legal); 
			d.showAbout();
		}
	}

	public MainMenuBar () {
		super();

		JMenu mnFile = new JMenu( "File" );
		add( mnFile );
		// Start Webserver
		JMenuItem mntmStart = new JMenuItem( "Start Server" );
		mnFile.add( mntmStart );
		// Stop Webserver
		JMenuItem mntmStop = new JMenuItem( "Stop Server" );
		mnFile.add( mntmStop );
		mnFile.addSeparator();
		JMenuItem mntmExit = new JMenuItem( exitAction );
		mnFile.add( mntmExit );

		JMenu mnEdit = new JMenu( "Edit" );
		add( mnEdit );

		JMenu mnHelp = new JMenu( "Help" );
		add( mnHelp );
		JMenuItem mntmAbout = new JMenuItem( aboutAction );
		mnHelp.add( mntmAbout );
	}

}
