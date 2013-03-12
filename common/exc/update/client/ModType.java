package exc.update.client;

/**
 * An enum holding the various types of mods.
 * 
 * @author Vazkii, TheWhiteWolves, Kovu
 */
public enum ModType {

	/**
	 * A mod without category; other. Displays in white.
	 */
	UNDEFINED("", true, 0xFFFFFF),

	/**
	* A tool mod. It adds features such as recording, inventory management, or something non-content related. Displays in orange.
	*/
	TOOL("Tool Mod", true, 0xFF6600),
	
	/**
	* A content mod. It adds blocks or items to the game. Displays in pink.
	*/
	CONTENT("Content Oriented Mod", true, 0xFF0066),
	
	/**
	 * A core mod. (A requirement for all the optional modules of a modular mod). Displays in blue.
	 */
	CORE("Core Mod", true, 0x0000FF),

	/**
	 * An API. Displays in red.
	 */
	API("API", true, 0xFF0000),

	/**
	 * A mod that's an addon to another. Displays in green.
	 */
	ADDON("Addon Mod", true, 0x00FF00),

	/**
	 * A mod that only appears in the GUI if the program is being ran in debug mode. Displays in purple.
	 * 
	 * @see <a href="http://help.eclipse.org/helios/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Ftasks%2Ftasks-debug-launch.htm">Eclipse debug Help page</a>
	 */
	SRC("Source Only", false, 0xA020F0);

	private String displayName;
	private boolean isRegular;
	private int hexColor;

	private ModType(String display, boolean isRegular, int hex) {
		displayName = display;
		this.isRegular = isRegular;
		hexColor = hex;
	}

	/**
	 * Gets the name to display on the GUI.
	 */
	public String getName() {
		return displayName;
	}

	/**
	 * Gets if it should only display in debug mode.
	 * 
	 * @see <a href="http://help.eclipse.org/helios/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Ftasks%2Ftasks-debug-launch.htm">Eclipse debug Help page</a>
	 */
	public boolean debugOnly() {
		return !isRegular;
	}

	/**
	 * Gets the color to display in the GUI.
	 */
	public int getHex() {
		return hexColor;
	}

}
