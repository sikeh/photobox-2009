package photoboxme.ui;

import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.Label;
import com.sun.lwuit.Image;
import com.sun.lwuit.Command;
import com.sun.lwuit.TextField;
import photoboxme.task.BackgroundTask;
import photoboxme.task.UploadTask;

/**
 * Screen for uploading the image.
 * 
 * @author Sike Huang
 * 
 */
public class UploadForm extends TemplateForm {
	private TemplateForm homeForm;
	private Command cmdHome;
	private Command cmdUpload;
	private Label label;
	private TextField textField;

	private byte[] data;

	public UploadForm(TemplateForm previsouForm, byte[] data) {
		super(previsouForm);
		homeForm = previsouForm;
		this.data = data;
		setTitle("PhotoBox ME - Uploader");
	}

	protected void buildComponents() {
		setScrollable(false);
		setLayout(new BorderLayout());
		label = new Label();
		addComponent(BorderLayout.CENTER, label);
		textField = new TextField();
		addComponent(BorderLayout.SOUTH, textField);
	}

	protected void attachComponentListeners() {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	protected void buildSoftButtons() {
		cmdHome = new Command("Home");
		cmdUpload = new Command("Upload");
		addCommand(cmdUpload);
		addCommand(cmdHome);
		setCommandListener(this);
	}

	protected void setModel() {
		label.setIcon(Image.createImage(data, 0, data.length).scaledWidth(
				this.getWidth()));
	}

	public void actionPerformed(ActionEvent actionEvent) {
		Command command = actionEvent.getCommand();
		if (command == cmdHome) {
			data = null;
			goBack();
		}
		if (command == cmdUpload) {
			String description = textField.getText();
			BackgroundTask task = new UploadTask(homeForm, description, data);
			task.start();
		}
	}
}
