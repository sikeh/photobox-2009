package photoboxme.util;

import com.sun.lwuit.Dialog;

/**
 * Created by IntelliJ IDEA. User: Sike Huang Date: Feb 21, 2009 Time: 12:13:33
 * AM To change this template use File | Settings | File Templates.
 */
public class Alert {
	public static void show(String message) {
		Dialog.show("Alert", message, Dialog.TYPE_ALARM, null, "Dismiss", null,
				3000);
	}
}
