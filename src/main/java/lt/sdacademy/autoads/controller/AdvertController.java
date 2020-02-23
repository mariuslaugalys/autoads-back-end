package lt.sdacademy.autoads.controller;

import java.util.List;
import javax.validation.Valid;
import lt.sdacademy.autoads.model.dto.AdvertDto;
import lt.sdacademy.autoads.model.dto.AdvertForm;
import lt.sdacademy.autoads.model.dto.AdvertUserDto;
import lt.sdacademy.autoads.model.specification.AdvertFilter;
import lt.sdacademy.autoads.services.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/adverts")
@CrossOrigin
public class AdvertController {

  private final AdvertService advertService;

  @Autowired
  public AdvertController(AdvertService advertService) {
    this.advertService = advertService;
  }

  @GetMapping(value = "/{id}")
  public AdvertUserDto getAdvert(@PathVariable Long id) {
    return advertService.getAdvert(id);
  }

  @PostMapping(consumes = "multipart/form-data")
  public AdvertUserDto create(
      @ModelAttribute @Valid AdvertForm advert,
      @RequestParam("file") List<MultipartFile> images,
      @AuthenticationPrincipal String userId
  ) {
    return advertService.create(advert, images, Long.parseLong(userId));
  }

  @PutMapping(value = "/{id}", consumes = "multipart/form-data")
  public AdvertUserDto update(
      @PathVariable("id") Long id,
      @ModelAttribute @Valid AdvertForm advert,
      @RequestParam("file") List<MultipartFile> images,
      @AuthenticationPrincipal String userId
  ) {
    return advertService.update(id, advert, images, Long.parseLong(userId));
  }

  @DeleteMapping("/{id}")
  public AdvertUserDto delete(
      @PathVariable("id") Long id,
      @AuthenticationPrincipal String userId
  ){
    return advertService.delete(id,Long.parseLong(userId));
  }

  @GetMapping
  public List<AdvertUserDto> getAll() {
    return advertService.getAll();
  }

  @PostMapping("/search")
  public List<AdvertUserDto> search(@RequestBody AdvertFilter filter
  ) {
    return advertService.filterAdverts(filter);
  }
}
