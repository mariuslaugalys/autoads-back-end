package lt.sdacademy.autoads.model.specification;

import java.util.Collection;
import javax.persistence.criteria.Predicate;
import lt.sdacademy.autoads.model.entity.AdvertEntity;
import org.springframework.data.jpa.domain.Specification;

public class AdvertSpecification {

  private Specification<AdvertEntity> specification;

  public AdvertSpecification(AdvertFilter advertFilter) {
    specification = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.conjunction();

    addGreaterOrEqual(advertFilter.getMinPrice(), "price");
    addLessOrEqual(advertFilter.getMaxPrice(), "price");

    addGreaterOrEqual(advertFilter.getMinManufactureYear(), "manufactureYear");
    addLessOrEqual(advertFilter.getMaxManufactureYear(), "manufactureYear");

    addGreaterOrEqual(advertFilter.getMinPower(), "power");
    addLessOrEqual(advertFilter.getMaxPower(), "power");

    addGreaterOrEqual(advertFilter.getMinKilometers(), "kilometers");
    addLessOrEqual(advertFilter.getMaxKilometers(), "kilometers");

    addGreaterOrEqual(advertFilter.getMinVolume(), "volume");
    addLessOrEqual(advertFilter.getMaxVolume(), "volume");

    addIsIn(advertFilter.getColors(), "color");
    addIsIn(advertFilter.getDriveTypes(), "driveType");
    addIsIn(advertFilter.getChassisTypes(), "chassisType");
    addIsIn(advertFilter.getTransmissionTypes(), "transmissionType");
    addIsIn(advertFilter.getFuelTypes(), "fuelType");

    if (advertFilter.getManufacturersModels() == null || advertFilter.getManufacturersModels().isEmpty()) {
      return;
    }
    specification = specification.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.or(
        advertFilter.getManufacturersModels().entrySet().stream()
            .map(entry -> {
              if (entry.getValue().isEmpty()) {
                return criteriaBuilder.equal(root.get("manufacturer"), entry.getKey());
              }
              return criteriaBuilder.and(
                  criteriaBuilder.equal(root.get("manufacturer"), entry.getKey()),
                  root.get("model").in(entry.getValue())
              );
            })
            .toArray(Predicate[]::new)
        )
    );
  }

  public Specification<AdvertEntity> getSpecification() {
    return specification;
  }

  private <T extends Comparable> void addGreaterOrEqual(T minValue, String columnName) {
    if (minValue != null) {
      specification = specification.and((root, criteriaQuery, criteriaBuilder) ->
          criteriaBuilder.greaterThanOrEqualTo(root.get(columnName), minValue));
    }
  }

  private <T extends Comparable> void addLessOrEqual(T maxValue, String columnName) {
    if (maxValue != null) {
      specification = specification.and((root, criteriaQuery, criteriaBuilder) ->
          criteriaBuilder.lessThanOrEqualTo(root.get(columnName), maxValue));
    }
  }

  private <T extends Collection> void addIsIn(T collection, String columnName) {
    if (collection != null) {
      specification = specification.and((root, criteriaQuery, criteriaBuilder) ->
          root.get(columnName).in(collection)
      );
    }
  }
}
