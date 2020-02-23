package lt.sdacademy.autoads.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lt.sdacademy.autoads.model.entity.UserEntity;
import lt.sdacademy.autoads.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements UserDetailsService {

  @Value("${app.authentication.signature.secret}")
  private String SECRET;

  @Value("${app.authentication.validity.period}")
  private int VALIDITY;

  private final UserRepository userRepository;

  public SecurityService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserEntity loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity user = userRepository.findOneByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException(String.format("User \"%s\" not found", email));
    }
    return user;
  }

  public String generateToken(UserEntity user) {
    Map<String, Object> claims = new HashMap<>();
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(user.getId() + "")
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + VALIDITY))
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact();
  }
}
