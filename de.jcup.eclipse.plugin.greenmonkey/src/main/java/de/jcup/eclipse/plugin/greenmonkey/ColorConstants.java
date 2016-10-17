package de.jcup.eclipse.plugin.greenmonkey;

import org.eclipse.swt.graphics.RGB;

public class ColorConstants {

	// see http://www.w3schools.com/colors/colors_picker.asp
	
	/**
	 * Black will NOT be rendered at any time
	 */
	public static final RGB BLACK = new RGB(0, 0, 0);
	
	public static final RGB ULTRA_DARK_GREEN = new RGB(0,51,0);
	public static final RGB DARK_GREEN = new RGB(0,102,0);
	public static final RGB GREEN = new RGB(0,134,0); 
	public static final RGB LIGHT_GREEN = new RGB(0,208,0); 
	
	public static final RGB DARK_CYAN = new RGB(0,104,204); 
	public static final RGB CYAN = new RGB(0,204,204);
	
	public static final RGB ORANGE = new RGB(255, 153,0);
	public static final RGB LIGHT_ORANGE = new RGB(244, 238, 231);
	/**
	 * Special color which will not supported by green monkey at all - synonymous for BLACK
	 */
	public static final RGB IGNORED = BLACK;

}
