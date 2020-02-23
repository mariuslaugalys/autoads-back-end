package lt.sdacademy.autoads.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lt.sdacademy.autoads.model.dto.UserForm;
import lt.sdacademy.autoads.model.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserFormConverterTest {

  private UserFormConverter userFormConverter;

  @BeforeEach
  void setUp() {
    userFormConverter = new UserFormConverter();
  }

  @Test
  void convert() {
    UserForm userForm = new UserForm();

    userForm.setFirstName("Jonas");
    userForm.setLastName("Maironis");
    userForm.setName("jonmairo");
    userForm.setEmail("jonas.maironis@test.com");
    userForm.setPhone("850012345");

    UserEntity userEntity = userFormConverter.convert(userForm);

    assertEquals("Jonas", userEntity.getFirstName());
    assertEquals("Maironis", userEntity.getLastName());
    assertEquals("jonmairo", userEntity.getName());
    assertEquals("jonas.maironis@test.com", userEntity.getEmail());
    assertEquals("850012345", userEntity.getPhone());
  }
}
