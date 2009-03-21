package photobox;

import org.springframework.util.FileCopyUtils;

import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;
import java.awt.image.RenderedImage;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Hold Util methods for resizing image.
 *
 * @Sike Huang
 */
public class ImageResizer {

    /**
     * Resize the image to desktop size.
     *
     * @param orginalData orig bytes of the image
     * @return bytes of resized image
     * @throws IOException failed in resizing
     */
    public static byte[] resizeToDesktop(byte[] orginalData) throws IOException {
        return resize(orginalData, ImageSizes.DESKTOP.getLimit());
    }

    /**
     * Resize the image to desktop thumbnail size.
     *
     * @param orginalData orig bytes of the image
     * @return bytes of resized iamge
     * @throws IOException failed in resizing
     */
    public static byte[] resizeToDesktopThumbnail(byte[] orginalData) throws IOException {
        return resize(orginalData, ImageSizes.DESKTOP_THUMBNAIL.getLimit());
    }

    /**
     * Resize the image to mobile size.
     *
     * @param orginalData orig bytes of the image
     * @return bytes of resized iamge
     * @throws IOException failed in resizing
     */
    public static byte[] resizeToMobile(byte[] orginalData) throws IOException {
        return resize(orginalData, ImageSizes.MOBILE.getLimit());
    }

    /**
     * Resize the image to mobile thumbnail size.
     *
     * @param orginalData orig bytes of the image
     * @return bytes of resized iamge
     * @throws IOException failed in resizing
     */
    public static byte[] resizeToMobileThumbnail(byte[] orginalData) throws IOException {
        return resize(orginalData, ImageSizes.MOBILE_THUMBNAIL.getLimit());
    }

    private static byte[] resize(byte[] orginalData, double limit) throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream(orginalData);
        double ratio = 1.0d;
        BufferedImage originalBufferedImage = ImageIO.read(input);
        Image resizedImage;
        if (originalBufferedImage.getHeight() > limit || originalBufferedImage.getWidth() > limit) {
            if (originalBufferedImage.getHeight() > originalBufferedImage.getWidth()) {
                ratio = limit / originalBufferedImage.getHeight();
            } else {
                ratio = limit / originalBufferedImage.getWidth();
            }

        }
        AffineTransformOp transformOp = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
        resizedImage = transformOp.filter(originalBufferedImage, null);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write((RenderedImage) resizedImage, "jpeg", os);
        return os.toByteArray();
    }
}
