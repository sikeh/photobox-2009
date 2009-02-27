package photoboxme.ui;

import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.Command;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextField;
import com.sun.lwuit.layouts.BoxLayout;

import javax.microedition.io.Connector;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;
import java.io.IOException;

import photoboxme.util.Config;

/**
 * Created by IntelliJ IDEA. User: Sike Huang Date: Feb 22, 2009 Time: 11:03:23
 * PM To change this template use File | Settings | File Templates.
 */
public class SmsForm extends TemplateForm {
	private Label lblPhotoId;
	private Label lblMessage;
	private Label lblSendTo;
	private TextField tfPhotoId;
	private TextField tfMessage;
	private TextField tfSendTo;
	private Command cmdSend;
	private Command cmdHome;

	public SmsForm(TemplateForm previsousForm) {
		super(previsousForm);
		setTitle("PhotoBox Me - SMS");
	}

	protected void buildComponents() {
		setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		lblPhotoId = new Label("Photo ID:");
		tfPhotoId = new TextField("");
		lblSendTo = new Label("Send to:");
		tfSendTo = new TextField("");
		lblMessage = new Label("Message:");
		tfMessage = new TextField("");
		addComponent(lblPhotoId);
		addComponent(tfPhotoId);
		addComponent(lblSendTo);
		addComponent(tfSendTo);
		addComponent(lblMessage);
		addComponent(tfMessage);
	}

	protected void attachComponentListeners() {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	protected void buildSoftButtons() {
		cmdSend = new Command("Send");
		cmdHome = new Command("Home");
		addCommand(cmdSend);
		addCommand(cmdHome);
		setCommandListener(this);
	}

	protected void setModel() {
		tfMessage.setText("check this photo :)");
	}

	public void actionPerformed(ActionEvent actionEvent) {
		Command command = actionEvent.getCommand();
		if (command == cmdHome) {
			goBack();
		}
		if (command == cmdSend) {
			try {
				int photoId = Integer.parseInt(tfPhotoId.getText());
				String photoNumber = tfSendTo.getText();
				// sms://+46762320173
				MessageConnection mc = (MessageConnection) Connector
						.open("sms://" + photoNumber);
				TextMessage tm = (TextMessage) mc
						.newMessage(MessageConnection.TEXT_MESSAGE);
				StringBuffer sb = new StringBuffer();
				sb.append(tfMessage.getText());
				sb.append(" please visit ");
				sb.append(Config.getInstance().getImageUrl(photoId));
				tm.setPayloadText(sb.toString());
				mc.send(tm);
				mc.close();
			} catch (IOException e) {
				e.printStackTrace(); // To change body of catch statement use
										// File | Settings | File Templates.
			}

		}
	}
}
