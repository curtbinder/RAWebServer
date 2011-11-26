package info.curtbinder.rawebserver.Classes;

import info.curtbinder.rawebserver.UI.MainFrame;

import java.awt.EventQueue;
//import java.io.IOException;

public class RAWebServerApp {

	public static MainFrame ui;
	public static Server server;
	
	/**
	 * @param args
	 */
	public static void main ( String[] args ) {
		server = new Server();
		EventQueue.invokeLater(new Runnable() {
			public void run ( ) {
				try {
					ui = new MainFrame();
					ui.setVisible(true);
					ui.getMainMenu().getMenuStart().doClick();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		/*
		server = new Server();
		try {
			server.start();
		} catch ( IOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
	}

}
