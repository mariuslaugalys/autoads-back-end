package lt.sdacademy.autoads.converter;

import java.util.ArrayList;
import lt.sdacademy.autoads.model.dto.UserForm;
import lt.sdacademy.autoads.model.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserFormConverter {

  public UserEntity convert(UserForm userForm) {
    return UserEntity.builder()
        .adverts(new ArrayList<>())
        .name(userForm.getName())
        .email(userForm.getEmail())
        .firstName(userForm.getFirstName())
        .lastName(userForm.getLastName())
        .phone(userForm.getPhone())
        .build();
  }
}
