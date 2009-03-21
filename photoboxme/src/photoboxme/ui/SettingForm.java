package photoboxme.ui;

import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextField;
import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.layouts.BoxLayout;

import photoboxme.util.Config;

/**
 * Screen for changing settings, where url of the PhotoBox Web can be changed and stored.
 * 
 * @author Sike Huang
 * 
 */
public class SettingForm extends TemplateForm {
	private Label label;
	private TextField textField;

	private Command cmdHome;
	private Command cmdUpdate;

	public SettingForm(TemplateForm previsousForm) {
		super(previsousForm);
		setTitle("PhotoBox ME - Setting");
	}

	protected void buildComponents() {
		setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		String webUrl = Config.getInstance().getWebUrl();
		label = new Label("web-url");
		textField = new TextField(webUrl);
		addComponent(label);
		addComponent(textField);
	}

	protected void attachComponentListeners() {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	protected void buildSoftButtons() {
		cmdHome = new Command("Home");
		cmdUpdate = new Command("Update");
		addCommand(cmdUpdate);
		addCommand(cmdHome);
		setCommandListener(this);
	}

	protected void setModel() {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	public void actionPerformed(ActionEvent actionEvent) {
		Command command = actionEvent.getCommand();
		if (command == cmdHome) {
			goBack();
		}
		if (command == cmdUpdate) {
			String webUrl = textField.getText();
			if (!webUrl.trim().equals("")) {
				Config.getInstance().setWebUrl(webUrl);
				Dialog.show("Info", "Setting is saved!", Dialog.TYPE_INFO,
						null, "Ok", null, 3000);
				goBack();
			}
		}
	}
}
