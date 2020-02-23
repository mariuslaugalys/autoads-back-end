package lt.sdacademy.autoads.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lt.sdacademy.autoads.model.dto.AdvertDto;
import lt.sdacademy.autoads.model.dto.AdvertUserDto;
import lt.sdacademy.autoads.model.dto.UserAdvertsDto;
import lt.sdacademy.autoads.model.dto.UserDto;
import lt.sdacademy.autoads.model.entity.AdvertEntity;
import lt.sdacademy.autoads.model.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDtoConverterTest {

  private UserDtoConverter userDtoConverter;

  @BeforeEach
  void setUp() {
    userDtoConverter = new UserDtoConverter();
  }

  @Test
  void convert() {
    UserEntity userEntity = new UserEntity();

    userEntity.setEmail("jonas@jonas.com");
    userEntity.setName("John");
    userEntity.setFirstName("Jonas");
    userEntity.setLastName("Jonaitis");
    userEntity.setPhone("867438877");

    UserDto userDto = userDtoConverter.convert(userEntity);

    assertEquals("jonas@jonas.com", userDto.getEmail());
    assertEquals("John", userDto.getName());
    assertEquals("Jonas", userDto.getFirstName());
    assertEquals("Jonaitis", userDto.getLastName());
    assertEquals("867438877", userDto.getPhone());
  }

  @Test
  void convertWithAdverts() {
    UserEntity userEntity = new UserEntity();

    userEntity.setEmail("jonas@jonas.com");
    userEntity.setName("John");
    userEntity.setFirstName("Jonas");
    userEntity.setLastName("Jonaitis");
    userEntity.setPhone("8-67438877");
    userEntity.setAdverts(Arrays.asList(
        new AdvertEntity(),
        new AdvertEntity(),
        new AdvertEntity()
    ));

    Function<List<AdvertEntity>, List<AdvertUserDto>> advertmapper = (advertEntities) -> advertEntities
        .stream()
        .map(advertEntity -> AdvertUserDto.builder().build())
        .collect(Collectors.toList());

    UserAdvertsDto userAdvertsDto = userDtoConverter.convertWithAdverts(userEntity, advertmapper);

    assertEquals(3, userAdvertsDto.getAdverts().size());
    assertEquals("John", userAdvertsDto.getName());
    assertEquals("Jonas", userAdvertsDto.getFirstName());
    assertEquals("Jonaitis", userAdvertsDto.getLastName());
    assertEquals("8-67438877", userAdvertsDto.getPhone());
  }
}
