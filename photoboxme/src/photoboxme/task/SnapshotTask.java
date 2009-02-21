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

/**
 * Created by IntelliJ IDEA.
 * User: Sike Huang
 * Date: Feb 20, 2009
 * Time: 10:36:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class SnapshotTask extends BackgroundTask {

    private TemplateForm previousForm;
    private SnapshotForm snapshotForm;
    private VideoControl videoControl;
    private Dialog dialog;

    private boolean canceled;

    private byte[] data;

    /**
     * Create a background task to take a snapshot, upon finishing the created image will be encapsulated into an {@code Attachment} object,
     * this object will be added into the existing {@code Vector} of {@code Attachment}s.
     *
     * @param previousForm the form to be shown once task finished
     * @param snapshotForm
     * @param videoControl the {@code VideControl} object to get snapshot on  @see javax.microedition.media.control.VideoControl#getSnapshot(String)
     */
    public SnapshotTask(TemplateForm previousForm, SnapshotForm snapshotForm, VideoControl videoControl) {
        this.previousForm = previousForm;
        this.snapshotForm = snapshotForm;
        this.videoControl = videoControl;
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
                snapshotForm.release();
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
        try {
            data = videoControl.getSnapshot(null);
        } catch (MediaException ex) {
            ex.printStackTrace();
        } finally {
            // release resource
            snapshotForm.release();
        }
    }
}
