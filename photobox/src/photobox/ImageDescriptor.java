package photobox;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Sike Huang
 * Date: Feb 15, 2009
 * Time: 4:39:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageDescriptor {
    private final int id;
    private final String description;
    private final Date timestamp;

    public ImageDescriptor(int id, String description, Date timestamp) {
        this.id = id;
        this.description = description;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
