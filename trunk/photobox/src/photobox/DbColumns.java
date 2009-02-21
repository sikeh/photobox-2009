package photobox;

/**
 * Created by IntelliJ IDEA.
 * User: Sike Huang
 * Date: Feb 21, 2009
 * Time: 4:22:37 PM
 * To change this template use File | Settings | File Templates.
 */
public enum DbColumns {
    DESKTOP("content_desktop"),
    DESKTOP_THUMBNAIL("content_desktop_thumbnail"),
    MOBILE("content_mobile"),
    MOBILE_THUMBNAIL("content_mobile_thumbnail");

    private String name;

    DbColumns(String name) {
        this.name = name;
    }

    public String getColumnName() {
        return name;
    }
}
