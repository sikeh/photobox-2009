package photobox;

/**
 * Created by IntelliJ IDEA.
 * User: Sike Huang
 * Date: Feb 21, 2009
 * Time: 4:14:33 PM
 * To change this template use File | Settings | File Templates.
 */
public enum ImageSizes {
    DESKTOP(500.0d),
    DESKTOP_THUMBNAIL(240.0d),
    MOBILE(240.d),
    MOBILE_THUMBNAIL(100.0d);

    private double limit;

    ImageSizes(double limit) {
        this.limit = limit;
    }

    public double getLimit() {
        return limit;
    }
}
