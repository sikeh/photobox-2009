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
 * Created by IntelliJ IDEA.
 * User: Sike Huang
 * Date: Feb 15, 2009
 * Time: 11:34:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageResizer {

    public static byte[] resizeToDesktop(byte[] orginalData) throws IOException {
        return resize(orginalData, ImageSizes.DESKTOP.getLimit());
    }

    public static byte[] resizeToDesktopThumbnail(byte[] orginalData) throws IOException {
        return resize(orginalData, ImageSizes.DESKTOP_THUMBNAIL.getLimit());
    }

    public static byte[] resizeToMobile(byte[] orginalData) throws IOException {
        return resize(orginalData, ImageSizes.MOBILE.getLimit());
    }

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
