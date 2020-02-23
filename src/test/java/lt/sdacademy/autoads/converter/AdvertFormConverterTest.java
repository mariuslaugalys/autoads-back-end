package lt.sdacademy.autoads.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import lt.sdacademy.autoads.model.dto.AdvertForm;
import lt.sdacademy.autoads.model.entity.AdvertEntity;
import lt.sdacademy.autoads.model.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdvertFormConverterTest {

  private AdvertFormConverter advertFormConverter;

  @BeforeEach
  void setUp() {
    advertFormConverter = new AdvertFormConverter();
  }

  @Test
  void convert() {
    AdvertForm advertForm = new AdvertForm();
    UserEntity userEntity = new UserEntity();

    advertForm.setModel(" ");
    advertForm.setManufactureYear(Short.parseShort("2018"));
    advertForm.setManufacturer("Lamborghini");
    advertForm.setVolume(BigDecimal.valueOf(1));
    advertForm.setFuelType("Petrol");
    advertForm.setPower(700);
    advertForm.setTransmissionType("Auto");
    advertForm.setDriveType("Rear");
    advertForm.setChassisType("Coupe");
    advertForm.setColor("Orange");
    advertForm.setKilometers(1000);
    advertForm.setPrice(150000);
    userEntity.setEmail("egidijus@test.com");

    AdvertEntity advertEntity = advertFormConverter.convert(advertForm, userEntity);

    assertEquals("egidijus@test.com", advertEntity.getSeller().getEmail());
    assertNotNull(advertEntity.getDateAdded());
    assertTrue(advertEntity.getImages().isEmpty());

    assertEquals(" ", advertForm.getModel());
    assertEquals(Short.parseShort("2018"), advertEntity.getManufactureYear());
    assertEquals("Lamborghini", advertEntity.getManufacturer());
    assertEquals(BigDecimal.valueOf(1), advertEntity.getVolume());
    assertEquals("Petrol", advertEntity.getFuelType());
    assertEquals(700, advertEntity.getPower());
    assertEquals("Auto", advertEntity.getTransmissionType());
    assertEquals("Rear", advertEntity.getDriveType());
    assertEquals("Coupe", advertEntity.getChassisType());
    assertEquals("Orange", advertEntity.getColor());
    assertEquals(1000, advertEntity.getKilometers());
    assertEquals(150000, advertEntity.getPrice());
  }

  @Test
  void update() {
    AdvertEntity advertEntity = new AdvertEntity();
    AdvertForm advertForm = new AdvertForm();

    advertForm.setManufacturer("Porsche");
    advertForm.setColor("Red");
    advertForm.setChassisType("Coupe");
    advertForm.setDriveType("Rear");
    advertForm.setFuelType("Petrol");
    advertForm.setKilometers(1000);
    advertForm.setModel(" ");
    advertForm.setPower(100);
    advertForm.setPrice(1000);
    advertForm.setTransmissionType("Auto");
    advertForm.setVolume(BigDecimal.valueOf(1));

    advertFormConverter.update(advertEntity, advertForm);

    assertEquals("Porsche", advertEntity.getManufacturer());
    assertEquals("Red", advertEntity.getColor());
    assertEquals("Coupe", advertEntity.getChassisType());
    assertEquals("Rear", advertEntity.getDriveType());
    assertEquals("Petrol", advertEntity.getFuelType());
    assertEquals(1000, advertEntity.getKilometers());
    assertEquals(" ", advertEntity.getModel());
    assertEquals(100, advertEntity.getPower());
    assertEquals(1000, advertEntity.getPrice());
    assertEquals("Auto", advertEntity.getTransmissionType());
    assertEquals(BigDecimal.valueOf(1), advertForm.getVolume());
  }
}
