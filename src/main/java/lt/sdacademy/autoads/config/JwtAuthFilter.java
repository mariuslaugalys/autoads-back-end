package lt.sdacademy.autoads.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

public class JwtAuthFilter extends BasicAuthenticationFilter {

  private static String HEADER = "Authorization";
  private static String PREFIX = "Bearer ";

  @Value("${app.authentication.signature.secret}")
  private String SECRET;

  @Value("${app.authentication.validity.period}")
  private int VALIDITY;


  public JwtAuthFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain
  ) throws ServletException, IOException {
    String requestTokenHeader = request.getHeader(HEADER);

    if (requestTokenHeader == null) {
      chain.doFilter(request, response);
      return;
    }
    if (!requestTokenHeader.startsWith(PREFIX)) {
      chain.doFilter(request, response);
      return;
    }

    String jwtToken = requestTokenHeader.substring(PREFIX.length());
    Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwtToken).getBody();

    if (!claims.getExpiration().after(new Date())) {
      chain.doFilter(request, response);
      return;
    }

    Authentication authentication = new UsernamePasswordAuthenticationToken(
        claims.getSubject(),
        null,
        new ArrayList<>()
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(request, response);
  }
}
