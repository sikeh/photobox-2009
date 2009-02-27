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
 * Created by IntelliJ IDEA.
 * User: Sike Huang
 * Date: Feb 7, 2009
 * Time: 12:32:17 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ImageController {
    @Autowired
    private ImageDatabase imageDatabase;

    @RequestMapping("/imageList.do")
    public String showImageList(Model model) {
        model.addAttribute("images", this.imageDatabase.getImages());
        return "imageList";
    }

    @RequestMapping("/image.do")
    public String showImage(int id, Model model) {
        model.addAttribute("image", imageDatabase.getImage(id));
        return "image";
    }

    @RequestMapping("/imageUpload.do")
    public String processImageUpload(@RequestParam("image") MultipartFile image, String description) throws IOException {
        imageDatabase.storeImage(image.getInputStream(), (int) image.getSize(), description);
        return "redirect:imageList.do";
    }

    @RequestMapping("/imageContent.do")
    public void streamImageContent(int id, OutputStream outputStream) {
        imageDatabase.streamImage(id, outputStream, DbColumns.DESKTOP);
    }

    @RequestMapping("/imageContent_m.do")
    public void streamMobileImageContent(int id, OutputStream outputStream) {
        imageDatabase.streamImage(id, outputStream, DbColumns.MOBILE);
    }

    @RequestMapping("/imageThumbnailContent.do")
    public void streamImageThumbnailContent(int id, OutputStream outputStream) {
        imageDatabase.streamImage(id, outputStream, DbColumns.DESKTOP_THUMBNAIL);
    }

    @RequestMapping("/imageThumbnailContent_m.do")
    public void streamMobileImageThumbnailContent(int id, OutputStream outputStream) {
        imageDatabase.streamImage(id, outputStream, DbColumns.MOBILE_THUMBNAIL);
    }

    @RequestMapping("/clearDatabase.do")
    public String clearDatabase() {
        this.imageDatabase.clearDatabase();
        return "redirect:imageList.do";
    }
}