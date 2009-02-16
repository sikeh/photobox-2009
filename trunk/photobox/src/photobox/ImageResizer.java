package photobox;

import javax.imageio.ImageIO;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
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
 *
 */
public class ImageResizer {
    // ref -> http://www.blogjava.net/envoydada/archive/2007/08/22/138549.html
    public static byte[] resizeToDesktop(InputStream input) throws IOException {
        double ratio = 0.0d;
        BufferedImage originalBufferedImage = ImageIO.read(input);
//        Image resizedImage = originalBufferedImage.getScaledInstance(300, 300, BufferedImage.SCALE_SMOOTH);
        Image resizedImage;
        if (originalBufferedImage.getHeight() > 300 || originalBufferedImage.getWidth() > 300) {
            if (originalBufferedImage.getHeight() > originalBufferedImage.getWidth()) {
                ratio = 300.0 / originalBufferedImage.getHeight();
            } else {
                ratio = 300.0 / originalBufferedImage.getWidth();
            }
        }
        AffineTransformOp transformOp = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
        resizedImage = transformOp.filter(originalBufferedImage, null);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write((RenderedImage) resizedImage, "jpeg", os);
        return os.toByteArray();
    }
}
