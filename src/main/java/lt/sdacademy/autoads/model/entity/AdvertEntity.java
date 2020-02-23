package lt.sdacademy.autoads.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "advert")
public class AdvertEntity extends AbstractEntity {

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "date_added", nullable = false)
    private LocalDateTime dateAdded;

    @Column(name = "manufacture_year", nullable = false)
    private Short manufactureYear;

    @Column(name = "transmission_type", nullable = false)
    private String transmissionType;

    @Column(name = "chassis_type", nullable = false)
    private String chassisType;

    @Column(name = "power", nullable = false)
    private Integer power;

    @Column(name = "fuel_type", nullable = false)
    private String fuelType;

    @Column(name = "kilometers", nullable = false)
    private Integer kilometers;

    @Column(name = "drive_type", nullable = false)
    private String driveType;

    @Column(name = "volume", nullable = false, precision = 5, scale = 3)
    private BigDecimal volume;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "model", nullable = false)
    private String model;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private UserEntity seller;

    @OneToMany(mappedBy = "advert", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ImageEntity> images;
}
