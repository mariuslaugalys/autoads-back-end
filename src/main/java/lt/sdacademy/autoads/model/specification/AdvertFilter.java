package lt.sdacademy.autoads.model.specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class AdvertFilter {

  private Map<String, List<String>> manufacturersModels;
  private List<String> colors;
  private List<String> driveTypes;
  private List<String> chassisTypes;
  private List<String> transmissionTypes;
  private List<String> fuelTypes;
  private Integer minPrice;
  private Integer maxPrice;
  private Short minManufactureYear;
  private Short maxManufactureYear;
  private Integer minPower;
  private Integer maxPower;
  private Integer minKilometers;
  private Integer maxKilometers;
  private BigDecimal minVolume;
  private BigDecimal maxVolume;
}
