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
    private final static double LIMIT_DESKTOP = 500.0d;
    private final static double LIMIT_DESKTOP_THUMBNAIL = 240.0d;
    private final static double LIMIT_MOBILE = 240.0d;
    private final static double LIMIT_MOBILE_THUMBNAIL = 100.0d;

//    public static byte[] resizeToDesktop(InputStream input) throws IOException {
//        return resize(input, LIMIT_DESKTOP);
//    }

    public static byte[] resizeToDesktop(byte[] orginalData) throws IOException {
        return resize(orginalData, LIMIT_DESKTOP);
    }

    public static byte[] resizeToDesktopThumbnail(byte[] orginalData) throws IOException {
        return resize(orginalData, LIMIT_DESKTOP_THUMBNAIL);
    }

    public static byte[] resizeToMobile(byte[] orginalData) throws IOException {
        return resize(orginalData, LIMIT_MOBILE);
    }

    public static byte[] resizeToMobileThumbnail(byte[] orginalData) throws IOException {
        return resize(orginalData, LIMIT_MOBILE_THUMBNAIL);
    }

//    public static byte[] resizeToDesktopThumbnail(InputStream input) throws IOException {
//        return resize(input, LIMIT_DESKTOP_THUMBNAIL);
//    }
//
//    public static byte[] resizeToMobile(InputStream input) throws IOException {
//        return resize(input, LIMIT_MOBILE);
//    }
//
//    public static byte[] resizeToMobileThumbnail(InputStream input) throws IOException {
//        return resize(input, LIMIT_MOBILE_THUMBNAIL);
//    }

    // ref -> http://www.blogjava.net/envoydada/archive/2007/08/22/138549.html    
//    private static byte[] resize(InputStream input, double limit) throws IOException {
//        //        double ratio = 0.0d;
//        double ratio = 1.0d;
//        BufferedImage originalBufferedImage = ImageIO.read(input);
////        Image resizedImage = originalBufferedImage.getScaledInstance(300, 300, BufferedImage.SCALE_SMOOTH);
//        Image resizedImage;
//        if (originalBufferedImage.getHeight() > limit || originalBufferedImage.getWidth() > limit) {
//            if (originalBufferedImage.getHeight() > originalBufferedImage.getWidth()) {
//                ratio = limit / originalBufferedImage.getHeight();
//            } else {
//                ratio = limit / originalBufferedImage.getWidth();
//            }
//
//        }
//        AffineTransformOp transformOp = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
//        resizedImage = transformOp.filter(originalBufferedImage, null);
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        ImageIO.write((RenderedImage) resizedImage, "jpeg", os);
//        return os.toByteArray();
//    }

    private static byte[] resize(byte[] orginalData, double limit) throws IOException {
        //        double ratio = 0.0d;
        ByteArrayInputStream input = new ByteArrayInputStream(orginalData);
        double ratio = 1.0d;
        BufferedImage originalBufferedImage = ImageIO.read(input);
//        Image resizedImage = originalBufferedImage.getScaledInstance(300, 300, BufferedImage.SCALE_SMOOTH);
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
