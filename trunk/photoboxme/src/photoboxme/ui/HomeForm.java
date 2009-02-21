package photoboxme.ui;

import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.List;
import com.sun.lwuit.Command;
import com.sun.lwuit.layouts.BoxLayout;

import javax.microedition.midlet.MIDlet;

import photoboxme.util.Alert;

/**
 * Created by IntelliJ IDEA.
 * User: Sike Huang
 * Date: Feb 20, 2009
 * Time: 9:31:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class HomeForm extends TemplateForm {
    private MIDlet miDlet;
    private List list;
    private Command cmdExit;
    private final static String[] menus = {"Snapshot", "Share", "About"};

    public HomeForm(MIDlet miDlet) {
        this.miDlet = miDlet;
        setTitle("PhotoBox ME - Home");
    }

    protected void buildComponents() {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        list = new List(menus);
        addComponent(list);
    }

    protected void attachComponentListeners() {
        list.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                List list = (List) actionEvent.getSource();
                String menu = (String) list.getSelectedItem();
                if (menu.equals("Snapshot")) {
                    if (System.getProperty("supports.recording").equalsIgnoreCase("true")) {
                        TemplateForm form = new SnapshotForm(HomeForm.this);
                        form.handle();
                    } else {
                        Alert.show("Oops, snapshot is not supported in this mobile :(");
                    }
                }
            }
        });
    }

    protected void buildSoftButtons() {
        cmdExit = new Command("Exit");
        addCommand(cmdExit);
        setCommandListener(this);
    }

    protected void setModel() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void actionPerformed(ActionEvent actionEvent) {
        Command command = actionEvent.getCommand();
        if (command == cmdExit) {
            miDlet.notifyDestroyed();
        }
    }
}
