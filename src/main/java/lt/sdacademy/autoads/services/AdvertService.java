package lt.sdacademy.autoads.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lt.sdacademy.autoads.converter.AdvertDtoConverter;
import lt.sdacademy.autoads.converter.AdvertFormConverter;
import lt.sdacademy.autoads.converter.UserDtoConverter;
import lt.sdacademy.autoads.model.dto.AdvertForm;
import lt.sdacademy.autoads.model.dto.AdvertUserDto;
import lt.sdacademy.autoads.model.entity.AdvertEntity;
import lt.sdacademy.autoads.model.entity.ImageEntity;
import lt.sdacademy.autoads.model.specification.AdvertFilter;
import lt.sdacademy.autoads.model.specification.AdvertSpecification;
import lt.sdacademy.autoads.repository.AdvertRepository;
import lt.sdacademy.autoads.repository.ImageFileRepository;
import lt.sdacademy.autoads.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AdvertService {

  private final AdvertRepository advertRepository;
  private final UserRepository userRepository;
  private final AdvertFormConverter advertFormConverter;
  private final AdvertDtoConverter advertDtoConverter;
  private final ImageFileRepository imageFileRepository;
  private final UserDtoConverter userDtoConverter;

  @Autowired
  public AdvertService(
      AdvertRepository advertRepository,
      UserRepository userRepository,
      AdvertFormConverter advertFormConverter,
      AdvertDtoConverter advertDtoConverter,
      ImageFileRepository imageFileRepository,
      UserDtoConverter userDtoConverter
  ) {
    this.advertRepository = advertRepository;
    this.userRepository = userRepository;
    this.advertFormConverter = advertFormConverter;
    this.advertDtoConverter = advertDtoConverter;
    this.imageFileRepository = imageFileRepository;
    this.userDtoConverter = userDtoConverter;
  }

  public List<AdvertUserDto> filterAdverts(
      AdvertFilter advertFilter
  ) {
    return advertDtoConverter.convertWithUser(
        advertRepository.findAll(new AdvertSpecification(advertFilter).getSpecification()),
        userDtoConverter::convert
    );
  }

  public List<AdvertUserDto> getAll() {
    return advertDtoConverter
        .convertWithUser(advertRepository.findAll(), userDtoConverter::convert);
  }

  public AdvertUserDto getAdvert(Long id) {
    return advertDtoConverter
        .convertWithUser(advertRepository.findOneById(id), userDtoConverter::convert);
  }

  @Transactional
  public AdvertUserDto create(
      AdvertForm advertForm,
      List<MultipartFile> images,
      Long userId
  ) {

    AdvertEntity advert = advertFormConverter.convert(
        advertForm,
        userRepository.findOneById(userId)
    );
    advertRepository.save(advert);

    List<String> imageUrls = IntStream.range(0, images.size())
        .mapToObj(index -> String.format("%s_%s.jpg", advert.getId(), index))
        .collect(Collectors.toList());

    IntStream.range(0, images.size()).forEach(index ->
        imageFileRepository.save(images.get(index), imageUrls.get(index))
            .orElseThrow(() ->
                new HttpClientErrorException(HttpStatus.CONFLICT, "File upload failed")
            )
    );

    List<ImageEntity> imageEntities = imageUrls.stream()
        .map(url -> ImageEntity.builder().url(url).advert(advert).build())
        .collect(Collectors.toList());

    advert.setImages(imageEntities);

    return advertDtoConverter.convertWithUser(advert, userDtoConverter::convert);
  }

  public AdvertUserDto update(Long id, AdvertForm advertForm, List<MultipartFile> files,
      Long authorizedUserId) {
    AdvertEntity advert = advertRepository.findOneById(id);
    if (advert == null) {
      throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Advert does not exist");
    }

    if (advert.getSeller().getId() != authorizedUserId) {
      throw new HttpClientErrorException(
          HttpStatus.UNAUTHORIZED,
          "Not authorized to edit this resource"
      );
    }

    List<String> oldUrls = advert.getImages().stream()
        .map(image -> image.getUrl())
        .collect(Collectors.toList());

    advert.getImages().clear();
    advertFormConverter.update(advert, advertForm);
    advertRepository.save(advert);

    List<String> fileUrls = IntStream.range(0, files.size())
        .mapToObj(index -> String.format("%s_%s.jpg", advert.getId(), index))
        .collect(Collectors.toList());

    fileUrls.forEach(url -> advert.getImages().add(
        ImageEntity.builder().url(url).advert(advert).build()
    ));
    advertRepository.save(advert);

    imageFileRepository.delete(oldUrls);

    IntStream.range(0, files.size()).forEach(index ->
        imageFileRepository.save(files.get(index), fileUrls.get(index))
            .orElseThrow(() ->
                new HttpClientErrorException(HttpStatus.CONFLICT, "File upload failed")
            )
    );

    return advertDtoConverter.convertWithUser(advert, userDtoConverter::convert);
  }

  public AdvertUserDto delete(Long id, Long authorizedUserId) {
    AdvertEntity advert = advertRepository.findOneById(id);
    if (advert == null) {
      throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Advert does not exist");
    }

    if (advert.getSeller().getId() != authorizedUserId) {
      throw new HttpClientErrorException(
          HttpStatus.UNAUTHORIZED,
          "Not authorized to edit this resource"
      );
    }

    try {
      advertRepository.deleteById(id);
    } catch (Exception e) {
      throw new HttpClientErrorException(HttpStatus.CONFLICT, "Delete failed");
    }

    imageFileRepository.delete(
        advert.getImages().stream().map(ImageEntity::getUrl).collect(Collectors.toList())
    );

    return advertDtoConverter.convertWithUser(advert,userDtoConverter::convert);
  }
}
