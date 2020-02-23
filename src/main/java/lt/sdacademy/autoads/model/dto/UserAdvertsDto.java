package lt.sdacademy.autoads.model.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class UserAdvertsDto {

  private String name;
  private String email;
  private String firstName;
  private String lastName;
  private String phone;
  private List<AdvertUserDto> adverts;
}
