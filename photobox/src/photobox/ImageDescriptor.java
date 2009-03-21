package photobox;

import java.util.Date;

/**
 * Represent an image.
 *
 * @author Sike Huang
 */
public class ImageDescriptor {
    private final int id;
    private final String description;
    private final Date timestamp;

    /**
     * Deafult constructor.
     *
     * @param id          identifier of the image (primary key)
     * @param description textual description of the image
     * @param timestamp   creation time of the image
     */
    public ImageDescriptor(int id, String description, Date timestamp) {
        this.id = id;
        this.description = description;
        this.timestamp = timestamp;
    }

    /**
     * Returen identifier (primary key) of the image.
     *
     * @return identifier (primary key)
     */
    public int getId() {
        return id;
    }

    /**
     * Return textual description of the image.
     *
     * @return textual description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Return creation time of the image.
     *
     * @return creation time
     */
    public Date getTimestamp() {
        return timestamp;
    }
}
