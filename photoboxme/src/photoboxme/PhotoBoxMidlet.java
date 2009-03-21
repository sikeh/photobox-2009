package photoboxme;

import java.io.IOException;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import photoboxme.ui.HomeForm;
import photoboxme.ui.TemplateForm;

import com.sun.lwuit.Display;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;

/**
 * Entry point, perform resource loading and apply theme.
 * 
 * @author Sike Huang
 *
 */
public class PhotoBoxMidlet extends MIDlet {
	protected void startApp() throws MIDletStateChangeException {
		Display.init(this);

		Resources resources = null;
		try {
			resources = Resources.open("/businessTheme.res");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UIManager.getInstance().setThemeProps(
				resources.getTheme(resources.getThemeResourceNames()[0]));

		TemplateForm menuForm = new HomeForm(this);
		menuForm.handle();
	}

	protected void pauseApp() {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	protected void destroyApp(boolean b) throws MIDletStateChangeException {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}
}
