package photobox;

/**
 * Hold constants mapped to the column names for storing different sizes of image.
 *
 * @author Sike Huang
 */
public enum DbColumns {
    /**
     * column name for image of desktop size
     */
    DESKTOP("content_desktop"),
    /**
     * column name for image of desktop thumbnail size
     */
    DESKTOP_THUMBNAIL("content_desktop_thumbnail"),
    /**
     * column name for image of mobile size
     */
    MOBILE("content_mobile"),
    /**
     * column name for image of mobile thumbnail size
     */
    MOBILE_THUMBNAIL("content_mobile_thumbnail");

    private String name;

    DbColumns(String name) {
        this.name = name;
    }

    /**
     * Return the column name
     *
     * @return column name
     */
    public String getColumnName() {
        return name;
    }
}
