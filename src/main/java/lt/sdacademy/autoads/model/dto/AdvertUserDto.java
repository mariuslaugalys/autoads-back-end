package lt.sdacademy.autoads.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AdvertUserDto {

  private Long id;
  private Integer price;
  private LocalDateTime dateAdded;
  private Short manufactureYear;
  private String transmissionType;
  private String chassisType;
  private Integer power;
  private String fuelType;
  private Integer kilometers;
  private String driveType;
  private BigDecimal volume;
  private String color;
  private String manufacturer;
  private String model;
  private UserDto seller;
  private List<String> images;
}
