package lt.sdacademy.autoads.converter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import lt.sdacademy.autoads.model.dto.AdvertForm;
import lt.sdacademy.autoads.model.entity.AdvertEntity;
import lt.sdacademy.autoads.model.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class AdvertFormConverter {

  public AdvertEntity convert(AdvertForm advertForm, UserEntity seller) {
    return AdvertEntity.builder()
        .seller(seller)
        .dateAdded(LocalDateTime.now())
        .images(new ArrayList<>())
        .manufacturer(advertForm.getManufacturer())
        .model(advertForm.getModel())
        .manufactureYear(advertForm.getManufactureYear())
        .volume(advertForm.getVolume())
        .fuelType(advertForm.getFuelType())
        .power(advertForm.getPower())
        .transmissionType(advertForm.getTransmissionType())
        .driveType(advertForm.getDriveType())
        .chassisType(advertForm.getChassisType())
        .color(advertForm.getColor())
        .kilometers(advertForm.getKilometers())
        .price(advertForm.getPrice())
        .build();
  }

  public void update(AdvertEntity advertEntity, AdvertForm advertForm) {
    advertEntity.setManufacturer(advertForm.getManufacturer());
    advertEntity.setColor(advertForm.getColor());
    advertEntity.setChassisType(advertForm.getChassisType());
    advertEntity.setDriveType(advertForm.getDriveType());
    advertEntity.setFuelType(advertForm.getFuelType());
    advertEntity.setKilometers(advertForm.getKilometers());
    advertEntity.setModel(advertForm.getModel());
    advertEntity.setPower(advertForm.getPower());
    advertEntity.setPrice(advertForm.getPrice());
    advertEntity.setTransmissionType(advertForm.getTransmissionType());
    advertEntity.setVolume(advertForm.getVolume());
    advertEntity.setManufactureYear(advertForm.getManufactureYear());
  }
}
