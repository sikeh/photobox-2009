package photobox;

/**
 * Hold max widths of images of different sizes.
 *
 * @author Sike Huang
 */
public enum ImageSizes {
    /**
     * desktop size, limit the width to 500px
     */
    DESKTOP(500.0d),
    /**
     * desktop thunmail size, limit the width to 240px
     */
    DESKTOP_THUMBNAIL(240.0d),
    /**
     * mobile size, limit the width to 240px
     */
    MOBILE(240.d),
    /**
     * mobile thumbnail size, limit the width to 100px
     */
    MOBILE_THUMBNAIL(100.0d);

    private double limit;

    ImageSizes(double limit) {
        this.limit = limit;
    }

    /**
     * Return the limit of the width.
     *
     * @return max width
     */
    public double getLimit() {
        return limit;
    }
}
