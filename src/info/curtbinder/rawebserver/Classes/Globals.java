package info.curtbinder.rawebserver.Classes;

public interface Globals {
	// Controller commands
	public static final String requestRoot = "/";
	public static final String requestWifi = "/wifi";
	public static final String requestStatus = "/r";
	public static final String requestMemoryByte = "/mb";
	public static final String requestMemoryInt = "/mi";
	public static final String requestMemoryAll = "/ma";
	public static final String requestDateTime = "/d";
	public static final String requestVersion = "/v";
	public static final String requestStatusNew = "/sr";
	public static final String requestStatusAll = "/sa";
	public static final String requestBtnPress = "/bp";
	public static final String requestModeFeeding = "/mf";
	public static final String requestModeWater = "/mw";
	public static final String requestCalibrationReload = "/cr";
	
	// App Version Information
	public static final String appTitle = "ReefAngel WebServer";
	public static final int versionMajor = 1;
	public static final int versionMinor = 0;
	public static final int versionRevision = 0;
	public static final String versionBuild = "alpha 1";
	public static final String copyrightInfo = "Copyright 2011 Curt Binder";
	public static final String url = "http://curtbinder.info/";
	
	// Icons
	public static final String bannerIconName = "/images/cb_h_banner-medium.png";
	public static final String appIconName = "/images/cb_icon-32x32.png";
	
	// Credits & Legal Stuff
	public static final String[] creditList =
			{ "Curt Binder", "Dave Molton", "Roberto Imai" };
	public static final String legal =
			"This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License. "
					+ "To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter to Creative Commons, 444 Castro Street, "
					+ "Suite 900, Mountain View, California, 94041, USA.";
}
