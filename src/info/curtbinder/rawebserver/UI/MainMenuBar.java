package info.curtbinder.rawebserver.UI;

import info.curtbinder.rawebserver.Classes.COMController;
import info.curtbinder.rawebserver.Classes.Globals;
import info.curtbinder.rawebserver.Classes.RAWebServerApp;
import info.curtbinder.rawebserver.Classes.WebServerProps;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MainMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	private ExitAction exitAction = new ExitAction();
	private AboutAction aboutAction = new AboutAction();
	private StartServerAction startAction = new StartServerAction();
	private StopServerAction stopAction = new StopServerAction();
	private IPPortAction portAction = new IPPortAction();
	private JMenuItem menuStart;
	
	public JMenuItem getMenuStart ( ) {
		return menuStart;
	}
	public JMenuItem getMenuStop()
	{
		return menuStop;
	}
	private JMenuItem menuStop;
	private COMController controller = COMController.INSTANCE();
	
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
			d.setCreditors( Globals.creditList );
			d.setLicense( Globals.legal );
			d.showAbout();
		}
	}

	public class IPPortAction extends AbstractAction
	{
		private static final long serialVersionUID = 1L;
		public IPPortAction()
		{
			putValue(NAME, "Port: " + Globals.getListenPort());			
		}
		
		public void actionPerformed(ActionEvent ae)
		{	
			String userPort = (String)JOptionPane.showInputDialog(RAWebServerApp.ui,"TCP Port:","Port Entry",JOptionPane.PLAIN_MESSAGE,
					null,null,Globals.getListenPort());
			if(userPort != null && !userPort.equals(Globals.getListenPort()))
			{	
				putValue(NAME,"Port: " + userPort);
				RAWebServerApp.ui.ClearLog();	
				menuStop.doClick();
				
				WebServerProps.INSTANCE().SetProp("LISTEN_PORT", userPort);				
				RAWebServerApp.ui.Log("TCP Port changed to: " + userPort + " Start server to take effect.");
			}
		}
	}
	
	public MainMenuBar () {
		super();

		JMenu mnFile = new JMenu( "File" );
		add( mnFile );
		// Start Webserver
		menuStart = new JMenuItem( startAction);		
		mnFile.add( menuStart );
		// Stop Webserver
		menuStop = new JMenuItem( stopAction );		
		mnFile.add( menuStop );
		mnFile.addSeparator();		
		JMenuItem mntmExit = new JMenuItem( exitAction );
		mnFile.add( mntmExit );

		//Settings Menu - COM Ports and TCP Port get added to this menu when the menu selected
		final JMenu mnEdit = new JMenu( "Settings" );
		mnEdit.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent arg0) {
				PopuplatePortsInMenuItem(mnEdit);				
			}
			
			@Override
			public void menuDeselected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		add( mnEdit );

		//Help
		JMenu mnHelp = new JMenu( "Help" );
		add( mnHelp );
		JMenuItem mntmAbout = new JMenuItem( aboutAction );
		mnHelp.add( mntmAbout );
	}

	public void enableStart ( boolean enable ) {
		menuStart.setEnabled( enable );
	}

	public void enableStop ( boolean enable ) {
		menuStop.setEnabled( enable );
	} 
	
	public void PopuplatePortsInMenuItem(JMenu menuItem)
	{
		menuItem.removeAll();		
		JMenuItem listenPortMU = new JMenuItem(portAction);
		menuItem.add(listenPortMU);
		menuItem.addSeparator();		
		
		List<String> comms = controller.LocalCOMList();
		JMenuItem[] comMenuItems = new JMenuItem[comms.size()];
		ButtonGroup group = new ButtonGroup();
		
		for(int i = 0; i < comms.size(); i++)
		{
			comMenuItems[i] = new JRadioButtonMenuItem(comms.get(i));			
			comMenuItems[i].setSelected(comms.get(i).equals(Globals.getComPort()));
			comMenuItems[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {					
					final String newComPort = ((JRadioButtonMenuItem)e.getSource()).getText();
					if(newComPort.equals(Globals.getComPort()))
						return;
					
						RAWebServerApp.ui.ClearLog();						
						menuStop.doClick();				
				
					controller.SetComPortString(newComPort);
					RAWebServerApp.ui.Log("COM Port changed to: " + Globals.getComPort() + " Start server to take effect.");
				}
			});
			group.add(comMenuItems[i]);
			menuItem.add(comMenuItems[i]);			
			
		}
	}
}
