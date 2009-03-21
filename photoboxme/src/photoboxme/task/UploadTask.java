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
 * Background task for uploading the image.
 * 
 * @author Sike Huang
 * 
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
	 * Pop a modeless {@code Dialog} to indicate in process before uploading.
	 */
	public void taskStarted() {
		dialog = new Dialog("Progress");
		dialog.addComponent(new Label("please wait..."));
		dialog.showModeless();
	}

	/**
	 * Dispose the modeless {@code Dialog} popped once uploading completes, and
	 * navigate back to {@link HomeForm}.
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
	 * Actual process of uploading an image using HTTP Multipart POST.
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