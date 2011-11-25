package info.curtbinder.rawebserver.UI;

import info.curtbinder.rawebserver.Classes.Globals;
import java.awt.Dimension;

import javax.swing.BoxLayout;
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

	public MainFrame () {
		setTitle( Globals.appTitle );
		// setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
		// center on screen
		setBounds( 100, 100, minWidth, minHeight );
		setMinimumSize( new Dimension( minWidth, minHeight ) );
		setJMenuBar( new MainMenuBar() );

		contentPane = new JPanel();
		contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		contentPane.setLayout( new BoxLayout( contentPane, BoxLayout.Y_AXIS ) );
		setContentPane( contentPane );

		ta = new JTextArea();
		ta.setLineWrap( true );
		ta.setWrapStyleWord( true );
		ta.setEditable( false );
		DefaultCaret caret = (DefaultCaret) ta.getCaret();
		caret.setUpdatePolicy( DefaultCaret.ALWAYS_UPDATE );
		scrollPane = new JScrollPane( ta );
		contentPane.add( scrollPane );
	}

	public void Log ( String msg ) {
		ta.append( msg );
		ta.append( "\n" );
	}
}
