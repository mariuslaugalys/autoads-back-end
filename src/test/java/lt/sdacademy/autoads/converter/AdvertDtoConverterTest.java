package lt.sdacademy.autoads.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import lt.sdacademy.autoads.model.dto.AdvertUserDto;
import lt.sdacademy.autoads.model.dto.UserDto;
import lt.sdacademy.autoads.model.entity.AdvertEntity;
import lt.sdacademy.autoads.model.entity.ImageEntity;
import lt.sdacademy.autoads.model.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdvertDtoConverterTest {

  private AdvertDtoConverter advertDtoConverter;

  @BeforeEach
  void setUp() {
    advertDtoConverter = new AdvertDtoConverter();
  }

  @Test
  void convert() {

    ImageEntity imageEntity = new ImageEntity();
    imageEntity.setUrl("url");

    AdvertEntity advertEntity = new AdvertEntity();
    advertEntity.setChassisType("sedan");
    advertEntity.setDriveType("front");
    advertEntity.setColor("pink");
    advertEntity.setFuelType("petrol");
    advertEntity.setKilometers(888);
    advertEntity.setManufactureYear(Short.parseShort("2018"));
    advertEntity.setModel("Passat");
    advertEntity.setPower(85);
    advertEntity.setPrice(1150);
    advertEntity.setTransmissionType("manual");
    advertEntity.setManufacturer("BMW");

    assertEquals("sedan", advertEntity.getChassisType());
    assertEquals("front", advertEntity.getDriveType());
    assertEquals("pink", advertEntity.getColor());
    assertEquals("petrol", advertEntity.getFuelType());
    assertEquals(888, advertEntity.getKilometers());
    assertEquals(Short.parseShort("2018"), advertEntity.getManufactureYear());
    assertEquals("Passat", advertEntity.getModel());
    assertEquals(85, advertEntity.getPower());
    assertEquals(1150, advertEntity.getPrice());
    assertEquals("manual", advertEntity.getTransmissionType());
    assertEquals("BMW", advertEntity.getManufacturer());

  }

  @Test
  void convertWithUser() {
    AdvertEntity advertEntity = new AdvertEntity();

    advertEntity.setManufacturer("Daihatsu");
    advertEntity.setModel("208");
    advertEntity.setTransmissionType("automatic");
    advertEntity.setManufactureYear(Short.parseShort("1980"));
    advertEntity.setPrice(10000);
    advertEntity.setPower(66);
    advertEntity.setKilometers(1200300);
    advertEntity.setFuelType("hybrid");
    advertEntity.setColor("deepskyblue");
    advertEntity.setDriveType("all");
    advertEntity.setChassisType("coupe");
    advertEntity.setVolume(BigDecimal.valueOf(1.8));
    advertEntity.setSeller(new UserEntity());
    advertEntity.setImages(new ArrayList<>());

    AdvertUserDto advertUserDto = advertDtoConverter.convertWithUser(
        advertEntity,
        (userEntity) -> UserDto.builder().build()
    );

    assertNotNull(advertUserDto.getSeller());
    assertTrue(advertUserDto.getImages().isEmpty());
    assertEquals("Daihatsu", advertUserDto.getManufacturer());
    assertEquals("208", advertUserDto.getModel());
    assertEquals("automatic", advertUserDto.getTransmissionType());
    assertEquals(Short.parseShort("1980"), advertUserDto.getManufactureYear());
    assertEquals(10000, advertUserDto.getPrice());
    assertEquals(66, advertUserDto.getPower());
    assertEquals(1200300, advertUserDto.getKilometers());
    assertEquals("hybrid", advertUserDto.getFuelType());
    assertEquals("deepskyblue", advertUserDto.getColor());
    assertEquals("all", advertUserDto.getDriveType());
    assertEquals("coupe", advertUserDto.getChassisType());
    assertEquals(BigDecimal.valueOf(1.8), advertUserDto.getVolume());

  }
}
