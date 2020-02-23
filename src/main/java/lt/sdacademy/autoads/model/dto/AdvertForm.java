package lt.sdacademy.autoads.model.dto;

import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdvertForm {

  @NotNull(message = "Please fill in the field")
  @Positive(message = "Please enter positive value")
  @Max(value = 9999999, message = "Please enter value at max 7 digits")
  private Integer price;

  @NotNull(message = "Please fill in the field")
  @Positive(message = "Please enter positive value")
  @Max(value = 9999, message = "Please enter value at max 4 digits")
  private Short manufactureYear;

  @NotBlank(message = "Please fill in the field")
  private String transmissionType;

  @NotBlank(message = "Please fill in the field")
  private String chassisType;

  @NotNull(message = "Please fill in the field")
  @Positive(message = "Please enter positive value")
  @Max(value = 999, message = "Please enter value at max 3 digits")
  private Integer power;

  @NotBlank(message = "Please fill in the field")
  private String fuelType;

  @NotNull(message = "Please fill in the field")
  @Positive(message = "Please enter positive value")
  @Max(value = 99999999, message = "Please enter value at max 8 digits")
  private Integer kilometers;

  @NotBlank(message = "Please fill in the field")
  private String driveType;

  @NotNull(message = "Please fill in the field")
  @Positive(message = "Please enter positive value")
  @Max(value = 99, message = "Please enter value at max 99")
  private BigDecimal volume;

  @NotBlank(message = "Please fill in the field")
  @Size(min = 2, max = 15, message = "Must be between 2 and 15 letters")
  @Pattern(regexp = "[a-zA-Z]+-?[a-zA-Z]+", message = "Only letters are allowed")
  private String color;

  @NotBlank(message = "Please fill in the field")
  private String manufacturer;

  @NotBlank(message = "Please fill in the field")
  private String model;
}
