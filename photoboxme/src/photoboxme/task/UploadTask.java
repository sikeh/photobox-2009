package photoboxme.task;

import com.sun.lwuit.Dialog;
import com.sun.lwuit.Label;
import com.sun.lwuit.Command;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.events.ActionEvent;

import javax.microedition.media.control.VideoControl;
import javax.microedition.media.MediaException;

import photoboxme.ui.TemplateForm;
import photoboxme.ui.UploadForm;
import photoboxme.ui.SnapshotForm;
import photoboxme.util.HttpMultipartUploader;

import java.util.Hashtable;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Sike Huang
 * Date: Feb 20, 2009
 * Time: 10:36:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class UploadTask extends BackgroundTask {

    private TemplateForm previousForm;
    private Dialog dialog;

    private boolean canceled;

    private String description;
    private byte[] data;

    public UploadTask(TemplateForm previousForm, String description, byte[] data) {
        this.previousForm = previousForm;
        this.description = description;
        this.data = data;
    }

    /**
     * Pop a modeless {@code Dialog} to indicate in process before downloading.
     */
    public void taskStarted() {
        dialog = new Dialog("Progress");
        dialog.addComponent(new Label("please wait..."));
        dialog.addCommand(new Command("Cancel"));
        dialog.setCommandListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                canceled = true;
                data = null;
                dialog.dispose();
                previousForm.show();
            }
        });
        dialog.showModeless();
    }

    /**
     * Dispose the modeless {@code Dialog} popped once download completes,
     * and show the form passed in via constructor.
     */
    public void taskFinished() {
        if (canceled) {
            return;
        }
        dialog.dispose();
        TemplateForm form = new UploadForm(previousForm, data);
        form.handle();
    }

    /**
     * <p>Actual process of snapshot.<p>
     * The resulting bytes will be encapsulated into a {@code Attachment}
     * and added to existing the collection of attachments,
     * meanwhile the resource is released here.
     */
    public void performTask() {
        if (canceled) {
            return;
        }
        String url = System.getProperty("uploader-url");
        if (url == null) {
            url = "http://localhost:8080/photobox/imageUpload.do";
        }
        Hashtable params = new Hashtable();
        params.put("description", description);
        HttpMultipartUploader uploader = new HttpMultipartUploader(url, params, "image", "image", "image/jpeg", data);
        try {
            uploader.send();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}