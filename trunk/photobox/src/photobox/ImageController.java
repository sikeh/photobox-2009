package photobox;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.OutputStream;
import java.io.IOException;

/**
 * Web controller for handling requests.
 *
 * @author Sike Huang
 */
@Controller
public class ImageController {
    @Autowired
    private ImageDatabase imageDatabase;

    /**
     * Handle request for list all images.
     *
     * @param model the model to be render in the view, where images are stored.
     * @return image list view
     */
    @RequestMapping("/imageList.do")
    public String showImageList(Model model) {
        model.addAttribute("images", this.imageDatabase.getImages());
        return "imageList";
    }

    /**
     * Handle request for show a single image.
     *
     * @param id    identifier of the image
     * @param model the model to be render in the view, where the specific image is stored.
     * @return image view
     */
    @RequestMapping("/image.do")
    public String showImage(int id, Model model) {
        model.addAttribute("image", imageDatabase.getImage(id));
        return "image";
    }

    /**
     * Handle request for uploading an image.
     *
     * @param image       image contained in the multipart post
     * @param description description of the image
     * @return redirect to image list view
     * @throws IOException failed in uploading
     */
    @RequestMapping("/imageUpload.do")
    public String processImageUpload(@RequestParam("image") MultipartFile image, String description) throws IOException {
        imageDatabase.storeImage(image.getInputStream(), (int) image.getSize(), description);
        return "redirect:imageList.do";
    }

    /**
     * Handle request for streaming an image of desktop size to the view.
     *
     * @param id           identifier of the image
     * @param outputStream output stream
     */
    @RequestMapping("/imageContent.do")
    public void streamImageContent(int id, OutputStream outputStream) {
        imageDatabase.streamImage(id, outputStream, DbColumns.DESKTOP);
    }

    /**
     * Handle request for streaming an image of mobile size to the view.
     *
     * @param id           identifier of the image
     * @param outputStream output stream
     */
    @RequestMapping("/imageContent_m.do")
    public void streamMobileImageContent(int id, OutputStream outputStream) {
        imageDatabase.streamImage(id, outputStream, DbColumns.MOBILE);
    }

    /**
     * Handle request for streaming an image of desktop thumbnail size to the view.
     *
     * @param id           identifier of the image
     * @param outputStream output stream
     */
    @RequestMapping("/imageThumbnailContent.do")
    public void streamImageThumbnailContent(int id, OutputStream outputStream) {
        imageDatabase.streamImage(id, outputStream, DbColumns.DESKTOP_THUMBNAIL);
    }

    /**
     * Handle request for streaming an image of mobile thumbnail size to the view.
     *
     * @param id           identifier of the image
     * @param outputStream output stream
     */
    @RequestMapping("/imageThumbnailContent_m.do")
    public void streamMobileImageThumbnailContent(int id, OutputStream outputStream) {
        imageDatabase.streamImage(id, outputStream, DbColumns.MOBILE_THUMBNAIL);
    }

    /**
     * Handle request for deleting all images.
     *
     * @return redirect to image list view
     */
    @RequestMapping("/clearDatabase.do")
    public String clearDatabase() {
        this.imageDatabase.clearDatabase();
        return "redirect:imageList.do";
    }
}
