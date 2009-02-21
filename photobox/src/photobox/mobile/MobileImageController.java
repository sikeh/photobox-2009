package photobox.mobile;

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

import photobox.ImageDatabase;
import photobox.DbColumns;

/**
 * Created by IntelliJ IDEA.
 * User: Sike Huang
 * Date: Feb 7, 2009
 * Time: 12:32:17 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class MobileImageController {
    @Autowired
    private ImageDatabase imageDatabase;

    @RequestMapping("/imageList.mobi")
    public String showImageList(Model model) {
        model.addAttribute("images", this.imageDatabase.getImages());
        return "imageList";
    }

    @RequestMapping("/image.mobi")
    public String showImage(int id, Model model) {
        model.addAttribute("image", imageDatabase.getImage(id));
        return "image";
    }

    @RequestMapping("/imageUpload.mobi")
    public void processImageUpload(@RequestParam("image") MultipartFile image, String description, OutputStream outputStream) throws IOException {
        imageDatabase.storeImage(image.getInputStream(), (int) image.getSize(), description);
        outputStream.write(1234);
    }

    @RequestMapping("/imageContent.mobi")
    public void streamImageContent(int id, OutputStream outputStream) {
        imageDatabase.streamImage(id, outputStream, DbColumns.MOBILE);
    }

    @RequestMapping("/imageThumbnailContent.mobi")
    public void streamImageThumbnailContent(int id, OutputStream outputStream) {
        imageDatabase.streamImage(id, outputStream, DbColumns.MOBILE_THUMBNAIL);
    }
}