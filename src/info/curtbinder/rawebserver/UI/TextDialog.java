package info.curtbinder.rawebserver.UI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JScrollPane;

public class TextDialog extends JDialog {

	private static final long serialVersionUID = 432907898192448125L;
	private static final int minWidth = 200;
	private static final int minHeight = 200;
	protected JScrollPane textWindow;

	public TextDialog ( JDialog owner, String title, String description ) {
		super( owner );
		setMinimumSize( new Dimension( minWidth, minHeight ) );
		createWindow( title, description );
	}

	public TextDialog ( JDialog owner, String title, String description,
						int width, int height ) {
		super( owner );
		setMinimumSize( new Dimension( width, height ) );
		createWindow( title, description );
	}

	private void createWindow ( String title, String description ) {
		JPanel contentPane = new JPanel();
		contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		contentPane.setLayout( new BoxLayout( contentPane, BoxLayout.Y_AXIS ) );
		setContentPane( contentPane );

		setTitle( title );
		JLabel lblDialogLabel = new JLabel( description );
		lblDialogLabel.setFont( new Font( "Dialog", Font.PLAIN, 12 ) );

		textWindow = new JScrollPane();
		textWindow.setAlignmentX( Component.LEFT_ALIGNMENT );

		// Button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout( new BoxLayout( buttonPanel, BoxLayout.X_AXIS ) );
		buttonPanel.setAlignmentY( Component.BOTTOM_ALIGNMENT );
		buttonPanel.setAlignmentX( Component.LEFT_ALIGNMENT );
		JButton btnClose = new JButton( "Close" );
		btnClose.setFont( new Font( "Dialog", Font.PLAIN, 12 ) );
		btnClose.addActionListener( new ActionListener() {
			public void actionPerformed ( ActionEvent ev ) {
				setVisible( false );
				dispose();
			}
		} );
		buttonPanel.add( Box.createHorizontalGlue() );
		buttonPanel.add( btnClose );

		contentPane.add( lblDialogLabel );
		contentPane.add( Box.createVerticalStrut( 5 ) );
		contentPane.add( textWindow );
		contentPane.add( Box.createVerticalStrut( 5 ) );
		contentPane.add( buttonPanel );
	}

	public void setWindowText ( String text ) {
		JTextArea ta = new JTextArea( text );
		ta.setLineWrap( true );
		ta.setWrapStyleWord( true );
		textWindow.setViewportView( ta );
	}

	public void setWindowList ( String[] array ) {
		JList list = new JList( array );
		textWindow.setViewportView( list );
	}

	public void showDialog ( ) {
		setLocationRelativeTo( getParent() );
		setVisible( true );
	}
}
