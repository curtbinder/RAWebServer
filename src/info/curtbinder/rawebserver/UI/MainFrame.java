package info.curtbinder.rawebserver.UI;

import info.curtbinder.rawebserver.Classes.Globals;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.ImageObserver;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int minWidth = 380; // 370;
	private static final int minHeight = 420;	
	private JPanel contentPane;
	private JTextArea ta;
	private JScrollPane scrollPane;
	private MainMenuBar mainMenu;

	public MainMenuBar getMainMenu ( ) {
		return mainMenu;
	}


	public MainFrame () {
		setTitle( Globals.appTitle );
		// setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		//setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		// center on screen
		setBounds( 100, 100, minWidth, minHeight );
		setMinimumSize( new Dimension( minWidth, minHeight ) );
		mainMenu = new MainMenuBar();
		setJMenuBar( mainMenu );

		contentPane = new JPanel();
		contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		contentPane.setLayout( new BoxLayout( contentPane, BoxLayout.Y_AXIS ) );
		setContentPane( contentPane );

		ta = new JTextArea();
		ta.setLineWrap(true);
		ta.setWrapStyleWord( true );
		ta.setEditable( false );
		DefaultCaret caret = (DefaultCaret) ta.getCaret();
		caret.setUpdatePolicy( DefaultCaret.ALWAYS_UPDATE );
		scrollPane = new JScrollPane( ta );
		contentPane.add( scrollPane );
		
		JButton clearBtn = new JButton();
		clearBtn.setText("Clear Output Window");		
		clearBtn.setAlignmentX(CENTER_ALIGNMENT);
		clearBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ClearLog();				
			}
		});
		contentPane.add(Box.createVerticalStrut(5));
		contentPane.add(clearBtn);
		
		//Handle the minimized event and hide the frame entirely.
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
				setVisible(false);
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void Log ( String msg ) {	
		ta.insert( msg +"\n",0 );		
	}
	
	public void ClearLog()
	{
		ta.setText("");
	}
}
