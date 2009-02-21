package photoboxme.ui;

import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.Label;
import com.sun.lwuit.Image;
import com.sun.lwuit.Command;
import com.sun.lwuit.TextField;
import photoboxme.util.HttpMultipartUploader;
import photoboxme.task.BackgroundTask;
import photoboxme.task.UploadTask;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: Sike Huang
 * Date: Feb 20, 2009
 * Time: 10:40:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class UploadForm extends TemplateForm {
    private Command cmdHome;
    private Command cmdUpload;
    private Label label;
    private TextField textField;

    private byte[] data;

    public UploadForm(TemplateForm previsouForm, byte[] data) {
        super(previsouForm);
        this.data = data;
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
        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected void buildSoftButtons() {
        cmdHome = new Command("Home");
        cmdUpload = new Command("Upload");
        addCommand(cmdHome);
        addCommand(cmdUpload);
        setCommandListener(this);
    }

    protected void setModel() {
        label.setIcon(Image.createImage(data, 0, data.length).scaledWidth(this.getWidth()));
    }

    public void actionPerformed(ActionEvent actionEvent) {
        Command command = actionEvent.getCommand();
        if (command == cmdHome) {
            data = null;
            goBack();
        }
        if (command == cmdUpload) {
            String description = textField.getText();
            BackgroundTask task = new UploadTask(this, description, data);
            task.start();
        }
    }
}
