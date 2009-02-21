package photoboxme;

import com.sun.lwuit.Display;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import photoboxme.ui.HomeForm;
import photoboxme.ui.TemplateForm;

/**
 * Created by IntelliJ IDEA.
 * User: Sike Huang
 * Date: Feb 20, 2009
 * Time: 9:30:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhotoBoxMidlet extends MIDlet {
    protected void startApp() throws MIDletStateChangeException {
        Display.init(this);
        TemplateForm menuForm = new HomeForm(this);
        menuForm.handle();
    }

    protected void pauseApp() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected void destroyApp(boolean b) throws MIDletStateChangeException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
