package photoboxme.util;

import com.sun.lwuit.Dialog;

/**
 * Util for showing alert.
 * 
 * @author Sike Huang
 * 
 */
public class Alert {

	/**
	 * Show a warning alert of given message within 3000ms.
	 * 
	 * @param message
	 *            message to be shown in the alert
	 */
	public static void show(String message) {
		Dialog.show("Alert", message, Dialog.TYPE_ALARM, null, "Dismiss", null,
				3000);
	}
}
