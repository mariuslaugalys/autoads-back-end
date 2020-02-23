package lt.sdacademy.autoads.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForm {

  @NotBlank(message = "Please fill in the field")
  @Size(min = 3, max = 50, message = "User name must be between 3 and 50 characters")
  private String name;

  @Email(message = "Email should be valid")
  @Size(max = 50, message = "Password must be at max 50 characters")
  private String email;

  @NotBlank(message = "Please fill in the field")
  @Pattern(regexp = "[a-zA-Z]+", message = "Only uppercase and lowercase letters are allowed")
  private String firstName;

  @NotBlank(message = "Please fill in the field")
  @Pattern(regexp = "[a-zA-Z]+", message = "Only uppercase and lowercase letters are allowed")
  private String lastName;

  @NotBlank(message = "Please fill in the field")
  @Size(min = 8, max = 60, message = "Password must be between 8 and 60 characters")
  private String password;

  @NotBlank(message = "Please fill in the field")
  @Pattern(regexp = "\\+?\\d+", message = "Not a valid phone number")
  @Size(min = 8, max = 15, message = "Not a valid phone number")
  private String phone;
}
