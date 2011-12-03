package info.curtbinder.rawebserver.Classes;

import info.curtbinder.rawebserver.UI.MainFrame;

import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
//import java.io.IOException;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.plaf.metal.MetalIconFactory;

public class RAWebServerApp {

	public static MainFrame ui;
	public static Server server;
	
	
	private static Image getImage()
	{
		Icon defaultIcon = MetalIconFactory.getTreeHardDriveIcon();
		Image img = new BufferedImage(defaultIcon.getIconWidth(), defaultIcon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		defaultIcon.paintIcon(new Panel(), img.getGraphics(),0,0);
		return img;
	}
	private static PopupMenu createPopupMenu() throws HeadlessException 
	{

		PopupMenu menu = new PopupMenu();		
		MenuItem exit = new MenuItem("Exit");		
		exit.addActionListener(new ActionListener() 
				{		
					public void actionPerformed(ActionEvent e) 
					{		
						System.exit(0);		
					}
				});

		menu.add(exit);
		return menu;
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main ( String[] args ) {
		
		TrayIcon icon = new TrayIcon(getImage(), "Reef Angel Web Server",createPopupMenu());
		icon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ui.setState(Frame.NORMAL);
				ui.setVisible(true);
			}
		});
		try {
			SystemTray.getSystemTray().add(icon);
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
