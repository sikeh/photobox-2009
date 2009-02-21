package photoboxme.ui;

import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.MediaComponent;
import com.sun.lwuit.Command;
import com.sun.lwuit.layouts.BorderLayout;

import javax.microedition.media.Player;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.control.VideoControl;
import java.io.IOException;

import photoboxme.task.SnapshotTask;
import photoboxme.task.BackgroundTask;
import photoboxme.util.Alert;

/**
 * Created by IntelliJ IDEA.
 * User: Sike Huang
 * Date: Feb 20, 2009
 * Time: 10:00:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class SnapshotForm extends TemplateForm {
    private TemplateForm menuForm;
    private Player player;
    private VideoControl videoControl;

    private Command cmdCapture;
    private Command cmdHome;

    private MediaComponent mediaComponent;

    public SnapshotForm(TemplateForm previsousForm) {
        super(previsousForm);
        menuForm = previsousForm;
        setTitle("PhotoBox ME - Snapshot");
    }

    protected void buildComponents() {
        setScrollable(false);
        setLayout(new BorderLayout());
        startPlayer();
    }

    protected void attachComponentListeners() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected void buildSoftButtons() {
        cmdCapture = new Command("Capture");
        cmdHome = new Command("Home");
        addCommand(cmdCapture);
        addCommand(cmdHome);
        setCommandListener(this);
    }

    protected void setModel() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void actionPerformed(ActionEvent actionEvent) {
        Command command = actionEvent.getCommand();
        if (command == cmdHome) {
            release();
            goBack();
        }
        if (command == cmdCapture) {
            mediaComponent.setVisible(false);
            BackgroundTask task = new SnapshotTask(menuForm, this, videoControl);
            task.start();
        }
    }

    public void release() {
        if (mediaComponent != null) {
            mediaComponent.stop();
            mediaComponent = null;
        }
        if (player != null) {
            player.close();
            player = null;
        }
    }

    private void startPlayer() {
        release();
        try {
            player = Manager.createPlayer("capture://video");
            player.prefetch();
            player.realize();
            videoControl = (VideoControl) player.getControl("VideoControl");
            mediaComponent = new MediaComponent(player);
            addComponent(BorderLayout.CENTER, mediaComponent);
            mediaComponent.start();
        } catch (MediaException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
