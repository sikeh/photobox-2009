package photoboxme.task;

import java.io.IOException;
import java.util.Hashtable;

import javax.microedition.io.ConnectionNotFoundException;

import photoboxme.ui.HomeForm;
import photoboxme.ui.TemplateForm;
import photoboxme.util.Config;
import photoboxme.util.HttpMultipartUploader;

import com.sun.lwuit.Dialog;
import com.sun.lwuit.Label;

/**
 * Created by IntelliJ IDEA. User: Sike Huang Date: Feb 20, 2009 Time: 10:36:07
 * PM To change this template use File | Settings | File Templates.
 */
public class UploadTask extends BackgroundTask {
	private TemplateForm menuForm;
	private Dialog dialog;

	private String description;
	private byte[] data;

	public UploadTask(TemplateForm menuForm, String description, byte[] data) {
		this.menuForm = menuForm;
		this.description = description;
		this.data = data;
	}

	/**
	 * Pop a modeless {@code Dialog} to indicate in process before downloading.
	 */
	public void taskStarted() {
		dialog = new Dialog("Progress");
		dialog.addComponent(new Label("please wait..."));
		dialog.showModeless();
	}

	/**
	 * Dispose the modeless {@code Dialog} popped once download completes, and
	 * show the form passed in via constructor.
	 */
	public void taskFinished() {
		dialog.dispose();
		boolean callBrowser = Dialog.show("Info",
				"Do you wish to view the web?", "Continue", "Dismiss");
		menuForm.show();
		if (callBrowser) {
			String webUrl = Config.getInstance().getWebUrl();
			try {
				((HomeForm) menuForm).getMidlet().platformRequest(webUrl);
			} catch (ConnectionNotFoundException e) {
				e.printStackTrace(); // To change body of catch statement use
										// File | Settings | File Templates.
			}
		}
	}

	/**
	 * <p>
	 * Actual process of snapshot.
	 * <p>
	 * The resulting bytes will be encapsulated into a {@code Attachment} and
	 * added to existing the collection of attachments, meanwhile the resource
	 * is released here.
	 */
	public void performTask() {
		String uploaderUrl = Config.getInstance().getUploaderUrl();
		Hashtable params = new Hashtable();
		params.put("description", description);
		HttpMultipartUploader uploader = new HttpMultipartUploader(uploaderUrl,
				params, "image", "image", "image/jpeg", data);
		try {
			uploader.send();
		} catch (IOException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
	}
}