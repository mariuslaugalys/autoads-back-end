package lt.sdacademy.autoads.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final AuthEntryPoint authenticationEntryPoint;

  public SecurityConfiguration(
      AuthEntryPoint authenticationEntryPoint) {
    this.authenticationEntryPoint = authenticationEntryPoint;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public JwtAuthFilter jwtAuthFilterBean() throws Exception {
    return new JwtAuthFilter(authenticationManagerBean());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .cors().and()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST,"/api/adverts/**").authenticated()
        .antMatchers(HttpMethod.POST,"/api/adverts/search").permitAll()
        .antMatchers(HttpMethod.DELETE,"/api/adverts/**").authenticated()
        .antMatchers(HttpMethod.PUT,"/api/adverts/**").authenticated()
        .antMatchers(HttpMethod.PUT,"/api/users/**").authenticated()
        .antMatchers(HttpMethod.GET,"/api/users/**").authenticated()
        .anyRequest().permitAll()
        .and()
        .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .addFilter(jwtAuthFilterBean())
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }
}
