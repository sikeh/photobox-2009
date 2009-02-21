package photobox;

import java.util.List;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Sike Huang
 * Date: Feb 15, 2009
 * Time: 4:38:38 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ImageDatabase {
    List<ImageDescriptor> getImages();

    ImageDescriptor getImage(int id);

    void streamImage(int id, OutputStream outputStream);

    void streamImageThumbnail(int id, OutputStream outputStream);

    void storeImage(InputStream inputStream, int contentLength, String description) throws IOException;

    void clearDatabase();
}
