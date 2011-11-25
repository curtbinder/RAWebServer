package info.curtbinder.rawebserver.Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import javax.swing.SwingUtilities;

public class Server {
	private static final int DEFAULT_PORT = 2000;
	private static final int CONN_TIMEOUT = 3000;
	private final ExecutorService exec = Executors
			.newSingleThreadScheduledExecutor();
	private int port;

	public Server () {
		init( DEFAULT_PORT );
	}

	public Server ( int port ) {
		init( port );
	}

	private void init ( int port ) {
		log( "Init with port: " + port );
		this.port = port;
	}

	public void start ( ) throws IOException {
		ServerSocket socket = new ServerSocket( port );
		log( "Server Started, listening on port: " + socket.getLocalPort() );
		while ( !exec.isShutdown() ) {
			try {
				final Socket conn = socket.accept();
				exec.execute( new Runnable() {
					public void run ( ) {
						handleRequest( conn );
					}
				} );
			} catch ( RejectedExecutionException e ) {
				if ( !exec.isShutdown() ) {
					log( "E: task submit rejected" );
				}
			}
		}
	}

	public void stop ( ) {
		log( "Server Stop" );
		exec.shutdown();
	}

	private void handleRequest ( Socket conn ) {
		log( "New connection from: " + conn.getInetAddress().getHostAddress() );
		try {
			// turn off keepalive
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
			}
			processRequest( s, bos );
		} catch ( IOException e ) {
			e.printStackTrace();
		} finally {
			if ( conn.isConnected() ) {
				try {
					log( "Closing" );
					conn.close();
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			}
		}
	}

	private void processRequest ( String req, BufferedWriter bos ) {
		log( "Request: '" + req + "'" );
		int len = req.length();
		int start = 0, end = len;
		start = req.indexOf( ' ' ) + 1;
		end = req.indexOf( ' ', start );
		// Should keep the actual command in the string
		// only removed for generic processing temporarily
		String cmd = req.substring( start, end );
		log( "Command:  '" + cmd + "'" );

		// Should just pass the request on to the controller
		// sendCmdToController(cmd);
		String response = "<html><head><title>Response</title></head><body>";
		response += sendCmdToController(cmd);
		response += "</body></html>";
		
		try {
			bos.write( response );
			bos.flush();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	private String sendCmdToController ( String cmd ) {
		String response = "";
		// Delete all this when communicating to controller
		// Just send the command, read the response, send the response
		// Do not need to process the response
		if ( cmd.equalsIgnoreCase( Globals.requestRoot ) ) {
			response = "Request Root";
		} else if ( cmd.equalsIgnoreCase( Globals.requestWifi ) ) {
			response = "Request Wifi Page";
		} else if ( cmd.equalsIgnoreCase( Globals.requestModeWater ) ) {
			response = "Request Water Change Mode";
		} else if ( cmd.equalsIgnoreCase( Globals.requestCalibrationReload ) ) {
			response = "Request Calibration Reload";
		} else if ( cmd.equalsIgnoreCase( Globals.requestModeFeeding ) ) {
			response = "Request Feeding Mode";
		} else if ( cmd.equalsIgnoreCase( Globals.requestBtnPress ) ) {
			response = "Request Button Press";
		} else if ( cmd.equalsIgnoreCase( Globals.requestStatusAll ) ) {
			response = "Request Status All";
		} else if ( cmd.equalsIgnoreCase( Globals.requestStatusNew ) ) {
			response = "Request Status New";
		} else if ( cmd.equalsIgnoreCase( Globals.requestMemoryAll ) ) {
			response = "Request Memory All";
		} else if ( cmd.equalsIgnoreCase( Globals.requestVersion ) ) {
			response = "Request Version";
		} else if ( cmd.startsWith( Globals.requestDateTime ) ) {
			// start or entire cmd
			response = "Request Date Time";
		} else if ( cmd.startsWith( Globals.requestMemoryInt ) ) {
			// start
			response = "Request Memory Int";
		} else if ( cmd.startsWith( Globals.requestMemoryByte ) ) {
			// start
			response = "Request Memory Byte";
		} else if ( cmd.startsWith( Globals.requestStatus ) ) {
			// start
			response = "Request Status";
		} else {
			response = "Unknown Request";
		}
		return response;
	}

	private void log ( final String msg ) {
		SwingUtilities.invokeLater( new Runnable() {
			public void run ( ) {
				RAWebServerApp.ui.Log( msg );
			}
		} );
	}
}
