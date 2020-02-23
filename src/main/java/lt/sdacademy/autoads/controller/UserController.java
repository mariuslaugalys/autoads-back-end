package lt.sdacademy.autoads.controller;

import javax.validation.Valid;
import lt.sdacademy.autoads.model.dto.UserAdvertsDto;
import lt.sdacademy.autoads.model.dto.UserForm;
import lt.sdacademy.autoads.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public UserAdvertsDto getUser(
      @AuthenticationPrincipal String userId
  ) {
    return userService.getUser(Long.parseLong(userId));
  }

  @PutMapping
  public UserAdvertsDto updateUser(
      @RequestBody @Valid UserForm userform,
      @AuthenticationPrincipal String userId
  ) {
    return userService.updateUser(userform, Long.parseLong(userId));
  }

  @PostMapping
  public UserAdvertsDto saveUser(@RequestBody @Valid UserForm user) {
    return userService.saveUser(user);
  }
}
