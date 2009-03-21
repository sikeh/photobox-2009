package photoboxme.task;

import javax.microedition.media.MediaException;
import javax.microedition.media.control.VideoControl;

import photoboxme.ui.SnapshotForm;
import photoboxme.ui.TemplateForm;
import photoboxme.ui.UploadForm;

import com.sun.lwuit.Dialog;
import com.sun.lwuit.Label;

/**
 * Background task for taking a snapshot. Upon finishing the bytes of the
 * resulting image will be passed into {@link UploadForm} and shown.
 * 
 * @author Sike Huang
 * 
 */
public class SnapshotTask extends BackgroundTask {

	private TemplateForm homeForm;
	private SnapshotForm snapshotForm;
	private VideoControl videoControl;
	private Dialog dialog;

	private byte[] data;

	/**
	 * Construct the task.
	 * 
	 * @param homeForm
	 *            the form to be shown once task finished
	 * @param snapshotForm
	 * @param videoControl
	 *            the {@code VideControl} object to get snapshot on @see
	 *            javax.microedition
	 *            .media.control.VideoControl#getSnapshot(String)
	 */
	public SnapshotTask(TemplateForm homeForm, SnapshotForm snapshotForm,
			VideoControl videoControl) {
		this.homeForm = homeForm;
		this.snapshotForm = snapshotForm;
		this.videoControl = videoControl;
	}

	/**
	 * Pop a modeless {@code Dialog} to indicate in process before doing
	 * snapshot.
	 */
	public void taskStarted() {
		dialog = new Dialog("Progress");
		dialog.addComponent(new Label("please wait..."));
		dialog.showModeless();
	}

	/**
	 * Dispose the modeless {@code Dialog} popped once snapshot completes, and
	 * show {@link UploadForm}.
	 */
	public void taskFinished() {
		dialog.dispose();
		TemplateForm form = new UploadForm(homeForm, data);
		form.handle();
	}

	/**
	 * Actual process of snapshot.
	 */
	public void performTask() {
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
