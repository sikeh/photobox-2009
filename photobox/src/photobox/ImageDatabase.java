package photobox;

import java.util.List;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;

/**
 * Define operations on accessing image.
 */
public interface ImageDatabase {
    /**
     * Return all image descriptors as a collection.
     *
     * @return collection of image descriptors
     */
    List<ImageDescriptor> getImages();

    /**
     * Return the image descriptor by given identifier of the image.
     *
     * @param id identifier of the image
     * @return image descriptor
     */
    ImageDescriptor getImage(int id);

    /**
     * Write the image bytes to an output stream.
     *
     * @param id           identifier of the image
     * @param outputStream an output stream
     * @param dbColumn     specify the view of the image
     */
    void streamImage(int id, OutputStream outputStream, DbColumns dbColumn);

    /**
     * Save an image into persitence storage.
     *
     * @param inputStream   input stream obtained from the image
     * @param contentLength length of the image bytes
     * @param description   description of the image
     * @throws IOException failed in saving
     */
    void storeImage(InputStream inputStream, int contentLength, String description) throws IOException;

    /**
     * Delete all image from persistence storage.
     */
    void clearDatabase();
}
