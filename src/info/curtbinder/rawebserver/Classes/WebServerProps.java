package info.curtbinder.rawebserver.Classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class WebServerProps {
	
	private static WebServerProps propInstance;
	private Properties props;	
	private InputStream inStrm;
	private WebServerProps()
	{
		try {
			
			File f = new File("app.properties");
			inStrm = new FileInputStream(f);
		}
		catch(Exception e)
		{
			inStrm = null;
		}
		try{
			if(inStrm == null)
				inStrm = getClass().getResourceAsStream("app.properties");
			
			props = new Properties();
			props.load(inStrm);
			inStrm.close();
			
		} catch (IOException e) {
		
		}
	}
	
	public String GetProp(String propertyName)
	{
		return props.getProperty(propertyName);
	}
	public void SetProp(String propName, String propVal)
	{
		try{
			FileOutputStream outputStream = new FileOutputStream("app.properties");
			props.setProperty(propName, propVal);
			props.store(outputStream,"none");
			outputStream.close();
		}
		catch(IOException ex)
		{ex.printStackTrace();}
	}
	
	//Singleton Instance
	public static synchronized WebServerProps INSTANCE()
	{
		if(propInstance == null)
			propInstance = new WebServerProps();
		return propInstance;		
	}

}
