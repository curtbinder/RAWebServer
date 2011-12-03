package info.curtbinder.rawebserver.Classes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import gnu.io.*;

public class COMController implements SerialPortEventListener
{
	 private CommPortIdentifier comPort;
	 private String comPortStr;
	 private Enumeration<?> portIds; 	 
	 private SerialPort serialPort;
	 private InputStream inStream;
	 private OutputStream outStream;
	 private byte[] readBuff;
	 private int numOfBytes;
	 private String serialResponse = "";
	 
	 private final int COM_TIMEOUT=1000;
	 //Singleton instance
	 private static COMController controller;
	 
	 private COMController()
	 {
		 comPortStr = WebServerProps.INSTANCE().GetProp("COM_PORT");
		 if(comPortStr != null && !comPortStr.isEmpty())		 
			 SetComPort();			 
		 
		 
	 }
	 
	 public static synchronized COMController INSTANCE()
	 {
		 if(controller == null)
			 controller = new COMController();
		 return controller;
	 }
	 
	 public void RefreshPorts()
	 {
		 portIds = CommPortIdentifier.getPortIdentifiers();		 
	 }

	 public List<String> LocalCOMList()
	 {
		 RefreshPorts();
		 List<String> comms = new ArrayList<String>();	
		 while(portIds.hasMoreElements())
		 {
			 CommPortIdentifier cpi = (CommPortIdentifier) portIds.nextElement();
			 if (cpi.getPortType() == CommPortIdentifier.PORT_SERIAL)
					 comms.add(cpi.getName());
		 }
		 return comms;
	 }
	 
	 
	 private void SetComPort()
	 {  
		 RefreshPorts();
		 while(portIds.hasMoreElements())
		 {
			 CommPortIdentifier cpi = (CommPortIdentifier)portIds.nextElement();
			 if(cpi.getPortType() == CommPortIdentifier.PORT_SERIAL && cpi.getName().equals(comPortStr))
			 {
				 comPort = cpi;
				 break;
			 }			 
		 }
	 }
	 
	 public boolean isSerialOpen()
	 {
		 return serialPort != null;
	 }
	 
	 public void SetComPortString(String port)
	 {
		 comPortStr = port;
		 WebServerProps.INSTANCE().SetProp("COM_PORT", port);
		 SetComPort();
	 }
	 public String GetComPortString()
	 {
		 return comPortStr;
	 }

	 //Open Serial COM Port
	 public void StartCOMListener() throws Exception	 
	 {
		 if(comPort == null)
			 throw new Exception("Local COM port not yet set.");		 
		 try{
			 serialPort = (SerialPort)comPort.open("RAWebServer",COM_TIMEOUT);
		 }
		 catch(PortInUseException ex)
		 {
			 System.out.println("COM Port: " + comPortStr + " in use!" );
			 throw new Exception("Local COM port already in use by another process.");
		 }
		
		 inStream = serialPort.getInputStream();
		 outStream = serialPort.getOutputStream();
		 serialPort.addEventListener(this);
		 serialPort.notifyOnDataAvailable(true);
		 
		 try
		 {
			 serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			 serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
			 serialPort.enableReceiveTimeout(COM_TIMEOUT);
		 }
		 catch(UnsupportedCommOperationException ex)
		 {
			 System.out.println("Unsupported COM Operation");
			 throw new Exception("Unsupported COM Operation.  Unable to setup serial port.");			 
		 }
		 
	 }
	 //Close Serial COM
	 public void StopCOMListener()
	 {
		 if(serialPort != null)
		 {
			try
			{
				inStream.close();
				outStream.close();
				serialPort.close();
			}
			catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}
		 }
	 }
	 
	 /*
	  * This is the event handler for receiving responses back of the serial port	
	  */
	 public void serialEvent(SerialPortEvent evnt)
	 {
		 numOfBytes = 0;
		 serialResponse = "";
		  
		 switch(evnt.getEventType())
		 {
		 	case SerialPortEvent.DATA_AVAILABLE :
		 		readBuff = new byte[1024];
		 		
		 		try
		 		{
		 			if(inStream.available() > 0)
		 			{
		 				numOfBytes = inStream.read(readBuff);		 				
		 				System.out.println("Received " + numOfBytes + " Bytes");
		 				serialResponse = new String(new String(readBuff,0,numOfBytes));
		 				System.out.println(serialResponse);		 				
		 			}		 			
		 		}
		 		catch(IOException ex)
		 		{
		 			System.out.println("IO Exceptioon reading serial response: " + ex.getMessage());
		 			numOfBytes = 0;
		 			serialResponse = "";
		 		}		 		
		 		break;
		 }
	 }
	 
	 public void WriteSerial(String toWrite) throws Exception
	 {
		 if(comPort == null || serialPort == null)
			 throw new Exception("Serial COM Port is not initialized");
		
		 try{
			 System.out.println("Writing \"" + toWrite + "\" to controller");
			 outStream.write(toWrite.getBytes());
		 }
		 catch(Exception ex)
		 {
			 System.out.println("Error writing to controller: " + ex.getMessage());			 
			 throw new Exception("Error writing command to controller\n" + ex.getMessage());			 
		 }
	 }

	 public int getNumOfBytes()
	 {return numOfBytes;}
	 public String SerialResponse()
	 {return serialResponse;}
	
}
