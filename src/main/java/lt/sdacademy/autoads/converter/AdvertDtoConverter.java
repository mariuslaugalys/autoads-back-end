package lt.sdacademy.autoads.converter;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import lt.sdacademy.autoads.model.dto.AdvertDto;
import lt.sdacademy.autoads.model.dto.AdvertUserDto;
import lt.sdacademy.autoads.model.dto.UserDto;
import lt.sdacademy.autoads.model.entity.AdvertEntity;
import lt.sdacademy.autoads.model.entity.ImageEntity;
import lt.sdacademy.autoads.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AdvertDtoConverter {

  @Value("${app.image.host}")
  private String imageHost;

  public AdvertDto convert(AdvertEntity advertEntity) {
    return AdvertDto.builder()
        .dateAdded(LocalDateTime.now())
        .images(advertEntity.getImages().stream()
            .sorted(Comparator.comparing(ImageEntity::getUrl))
            .map(image -> imageHost + "/" + image.getUrl())
            .collect(toList())
        )
        .id(advertEntity.getId())
        .manufacturer(advertEntity.getManufacturer())
        .model(advertEntity.getModel())
        .manufactureYear(advertEntity.getManufactureYear())
        .volume(advertEntity.getVolume())
        .fuelType(advertEntity.getFuelType())
        .power(advertEntity.getPower())
        .transmissionType(advertEntity.getTransmissionType())
        .driveType(advertEntity.getDriveType())
        .chassisType(advertEntity.getChassisType())
        .color(advertEntity.getColor())
        .kilometers(advertEntity.getKilometers())
        .price(advertEntity.getPrice())
        .build();
  }

  public List<AdvertDto> convert(List<AdvertEntity> adverts) {
    return adverts.stream().map(this::convert).collect(toList());
  }

  public AdvertUserDto convertWithUser(
      AdvertEntity advertEntity,
      Function<UserEntity, UserDto> userMapper
  ) {
    return AdvertUserDto.builder()
        .seller(userMapper.apply(advertEntity.getSeller()))
        .dateAdded(LocalDateTime.now())
        .images(advertEntity.getImages().stream()
            .sorted(Comparator.comparing(ImageEntity::getUrl))
            .map(image -> imageHost + "/" + image.getUrl())
            .collect(toList())
        )
        .id(advertEntity.getId())
        .manufacturer(advertEntity.getManufacturer())
        .model(advertEntity.getModel())
        .manufactureYear(advertEntity.getManufactureYear())
        .volume(advertEntity.getVolume())
        .fuelType(advertEntity.getFuelType())
        .power(advertEntity.getPower())
        .transmissionType(advertEntity.getTransmissionType())
        .driveType(advertEntity.getDriveType())
        .chassisType(advertEntity.getChassisType())
        .color(advertEntity.getColor())
        .kilometers(advertEntity.getKilometers())
        .price(advertEntity.getPrice())
        .build();
  }

  public List<AdvertUserDto> convertWithUser(
      List<AdvertEntity> adverts,
      Function<UserEntity, UserDto> userMapper
  ) {
    return adverts.stream().map((entity) -> convertWithUser(entity, userMapper)).collect(toList());
  }
}
