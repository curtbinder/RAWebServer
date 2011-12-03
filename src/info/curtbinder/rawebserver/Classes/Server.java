package info.curtbinder.rawebserver.Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import javax.swing.SwingUtilities;

public class Server {
	
	private static final int CONN_TIMEOUT = 3500;
	public static int CONN_PORT;
	private ExecutorService exec = Executors
			.newSingleThreadScheduledExecutor();

	private Thread serverThread;
	private COMController controller = COMController.INSTANCE();
	
	public boolean StartCOM()
	{
		if(Globals.getComPort() == null || Globals.getComPort().isEmpty())
		{
			log("COM port not yet defined.  Cannot start server.");
			return false;
		}
		try{
			controller.StartCOMListener();
			log("COM Port '" + Globals.getComPort() + "' successfully opened..");
			return true;
		}
		catch(Exception ex)
		{
			log("Unable to open COM port...Server not started.");
			return false;
		}	
		
	}
	public void StartWebServer ( ) {		
		
		
		if ( exec.isShutdown() ) {
			exec = Executors.newSingleThreadExecutor();
		}
		
		serverThread = new Thread() {
				public void run (){
					try{													
							ServerSocket socket = new ServerSocket( Integer.parseInt(Globals.getListenPort()));
							socket.setSoTimeout( CONN_TIMEOUT );
							log( "Server Started, listening on port: " + socket.getLocalPort() );								
							while ( !exec.isShutdown() ) {
								try {
									final Socket conn = socket.accept();
									if (Thread.interrupted() ) {
										throw new InterruptedException();
									}
									if (conn.isConnected()) {									
										exec.execute( new Runnable() {
											public void run ( ) {
												handleRequest( conn );
											}
										} );
									}
									if ( Thread.interrupted() ) {
										throw new InterruptedException();
									}
								} catch ( RejectedExecutionException e ) {
									if ( !exec.isShutdown() ) {
										log( "E: task submit rejected" );
									}
								} catch ( InterruptedException e ) {
									log( "Server thread interrupted" );
								} catch ( SocketTimeoutException e ) {
									// ignore the socket timeout
								}
						}
						if ( socket.isBound() ) {
							socket.close();
						}
					} catch ( IOException e ) {
						e.printStackTrace();
					}					
				}				
		};
		serverThread.start();		
	}
	
	public void stop ( ) {
		log( "Server Stop" );
		exec.shutdown();		
		serverThread.interrupt();
		if(controller.isSerialOpen())
			controller.StopCOMListener();
		
	}

	private void handleRequest ( Socket conn ) {		
		try {
			// turn off keep-alive
			conn.setKeepAlive( false );
			conn.setSoTimeout( CONN_TIMEOUT );

			String s = "";
			BufferedReader bin =
					new BufferedReader( new InputStreamReader(
						conn.getInputStream() ) );
			BufferedWriter bos =
					new BufferedWriter( new OutputStreamWriter(
						conn.getOutputStream() ) );
			s = bin.readLine();
			while ( bin.ready() ) {
				bin.readLine();
				if ( Thread.interrupted() ) {
					throw new InterruptedException();
				}
			}
			processRequest( s, bos, conn.getInetAddress().getHostAddress() );
		} catch ( IOException e ) {
			e.printStackTrace();
		} catch ( InterruptedException e ) {
			e.printStackTrace();
		} finally {
			if ( conn.isConnected() ) {
				try {					
					conn.close();
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			}
		}
	}

	private void processRequest ( String req, BufferedWriter bos, String sender ) {
		
		if(req.contains("favicon.ico"))
			return;
		log("Processing request from: " + sender);
		log( "Request: '" + req + "'" );
				
		int end = req.indexOf( ' ', req.indexOf(' ')+1);
		
	    // The command string needs to be sent to the controller in the form of "GET /r99 " when communicating over USB.  
		// The leading 'GET' and trailing 'space' are needed for the controller to process the request properly.
		String cmd = req.substring( 0, end )+ " ";
		log( "Command:  '" + cmd + "'" );
				
		if(!controller.isSerialOpen())
		{
			log("Serial port not open.  Cannot forward reqeuest to controller.");
			return;		
		}		
		try
		{
			//Should just pass the request on to the controller
			log("Forwarding reqeuest to " + Globals.getComPort());
			controller.WriteSerial(cmd);			
			
			 //* Putting the thread to sleep for a second seems to be enough time to let the controller respond...			 
			Thread.sleep(1000);
			log("Controller responded with " + controller.getNumOfBytes() + " bytes");
		}
		catch(Exception ex)
		{
			log("Error during controller I/O: " + ex.getMessage());
		}
		
		try {
			bos.write( controller.SerialResponse() );
			bos.flush();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	private void log ( final String msg ) {
		SwingUtilities.invokeLater( new Runnable() {
			public void run ( ) {
				RAWebServerApp.ui.Log( msg );
			}
		} );
	}
}
