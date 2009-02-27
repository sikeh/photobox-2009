package photoboxme.task;

import com.sun.lwuit.Display;

/**
 * A tool allowing to respond to an event in the background possibly with
 * progress indication inspired by Swings "SwingWorker" tool. This class should
 * be used from event dispatching code to prevent the UI from blocking. State
 * can be stored in this class the separate thread and it can be used by the
 * finish method which will be invoked after running.
 * 
 * @author Shai Almog
 */
public abstract class BackgroundTask {

	/**
	 * Start this task (template method)
	 */
	public final void start() {
		if (Display.getInstance().isEdt()) {
			taskStarted();
		} else {
			Display.getInstance().callSeriallyAndWait(new Runnable() {

				public void run() {
					taskStarted();
				}
			});
		}
		new Thread(new Runnable() {

			public void run() {
				if (Display.getInstance().isEdt()) {
					taskFinished();
				} else {
					performTask();
					Display.getInstance().callSerially(this);
				}
			}
		}).start();
	}

	/**
	 * Invoked on the LWUIT EDT before spawning the background thread, this
	 * allows the developer to perform initialization easily.
	 */
	public void taskStarted() {
	}

	/**
	 * Invoked on a separate thread in the background, this task should not
	 * alter UI except to indicate progress.
	 */
	public abstract void performTask();

	/**
	 * Invoked on the LWUIT EDT after the background thread completed its
	 * execution.
	 */
	public void taskFinished() {
	}
}
