package lt.sdacademy.autoads.converter;

import java.util.List;
import java.util.function.Function;
import lt.sdacademy.autoads.model.dto.AdvertDto;
import lt.sdacademy.autoads.model.dto.AdvertUserDto;
import lt.sdacademy.autoads.model.dto.UserAdvertsDto;
import lt.sdacademy.autoads.model.dto.UserDto;
import lt.sdacademy.autoads.model.entity.AdvertEntity;
import lt.sdacademy.autoads.model.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

  public UserDto convert(UserEntity userEntity) {
    return UserDto.builder()
        .email(userEntity.getEmail())
        .name(userEntity.getName())
        .firstName(userEntity.getFirstName())
        .lastName(userEntity.getLastName())
        .phone(userEntity.getPhone())
        .build();
  }

  public UserAdvertsDto convertWithAdverts(
      UserEntity userEntity,
      Function<List<AdvertEntity>, List<AdvertUserDto>> advertMapper
  ) {
    return UserAdvertsDto.builder()
        .adverts(advertMapper.apply(userEntity.getAdverts()))
        .email(userEntity.getEmail())
        .name(userEntity.getName())
        .firstName(userEntity.getFirstName())
        .lastName(userEntity.getLastName())
        .phone(userEntity.getPhone())
        .build();
  }
}
