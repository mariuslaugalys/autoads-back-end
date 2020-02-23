package lt.sdacademy.autoads.controller;

import lt.sdacademy.autoads.model.dto.Token;
import lt.sdacademy.autoads.model.dto.UserForm;
import lt.sdacademy.autoads.services.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@CrossOrigin
@RestController
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final SecurityService securityService;

  public AuthController(AuthenticationManager authenticationManager,
      SecurityService securityService) {
    this.authenticationManager = authenticationManager;
    this.securityService = securityService;
  }

  @PostMapping("/login")
  public Token authenticate(@RequestBody UserForm userForm) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
          userForm.getEmail(),
          userForm.getPassword()
      ));
    } catch (Exception e) {
      throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Bad credentials");
    }

    return new Token(securityService.generateToken(
        securityService.loadUserByUsername(userForm.getEmail())
    ));
  }
}
