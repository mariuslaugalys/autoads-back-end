package lt.sdacademy.autoads.controller;

import java.io.IOException;
import lt.sdacademy.autoads.services.ImageFileService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/images")
@CrossOrigin
public class ImageController {

  private final ImageFileService imageFileService;

  public ImageController(ImageFileService imageFileService) {
    this.imageFileService = imageFileService;
  }

  @GetMapping(value = "/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
  @ResponseBody
  public byte[] getImage(@PathVariable("fileName") String fileName) throws IOException {
    return imageFileService.get(fileName);
  }

}
