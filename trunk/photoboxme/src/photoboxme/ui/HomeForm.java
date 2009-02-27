package photoboxme.ui;

import java.io.IOException;

import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.midlet.MIDlet;

import photoboxme.util.Config;

import com.sun.lwuit.Command;
import com.sun.lwuit.Image;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.list.DefaultListModel;
import com.sun.lwuit.util.Resources;

/**
 * Created by IntelliJ IDEA. User: Sike Huang Date: Feb 20, 2009 Time: 9:31:28
 * PM To change this template use File | Settings | File Templates.
 */
public class HomeForm extends TemplateForm {
	private MIDlet miDlet;
	private List list;
	private Command cmdExit;
	private final static String[] MENU_ITEMS = { "Snapshot", "SMS", "Browser",
			"Setting" };
	private final static String[] MENU_TIPS = { "take a snapshot and upload", "send sms to friends", "visit photobox website",
	"change photobox url" };

	public HomeForm(MIDlet miDlet) {
		this.miDlet = miDlet;
		setTitle("PhotoBox ME - Home");
	}

	protected void buildComponents() {
		setLayout(new BorderLayout());
		list = new List();
		addComponent(BorderLayout.CENTER, list);
	}

	protected void attachComponentListeners() {
		list.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent actionEvent) {
				List list = (List) actionEvent.getSource();
				switch (list.getSelectedIndex()) {
				case 0:
					TemplateForm snapshotForm = new SnapshotForm(HomeForm.this);
					snapshotForm.handle();
					break;
				case 1:
					TemplateForm smsForm = new SmsForm(HomeForm.this);
					smsForm.handle();
					break;
				case 2:
					String webUrl = Config.getInstance().getWebUrl();
					try {
						miDlet.platformRequest(webUrl);
					} catch (ConnectionNotFoundException e) {
						e.printStackTrace(); // To change body of catch
						// statement use File | Settings
						// | File Templates.
					}
					break;
				case 3:
					TemplateForm settingForm = new SettingForm(HomeForm.this);
					settingForm.handle();
					break;
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
		list.setModel(new DefaultListModel(MENU_ITEMS));
		Image[] images = new Image[4];
		Resources resources = null;
		try {
			resources = Resources.open("/resources.res");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		images[0] = resources.getImage("camera");
		images[1] = resources.getImage("sms");
		images[2] = resources.getImage("safari");
		images[3] = resources.getImage("setting");
		list.setListCellRenderer(new MenusRender(images, MENU_TIPS));
	}

	public void actionPerformed(ActionEvent actionEvent) {
		Command command = actionEvent.getCommand();
		if (command == cmdExit) {
			miDlet.notifyDestroyed();
		}
	}

	public MIDlet getMidlet() {
		return miDlet;
	}
}
