package lt.sdacademy.autoads.config;

import lt.sdacademy.autoads.repository.ImageFileRepository;
import lt.sdacademy.autoads.repository.LocalImageFileRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@Profile("develop")
public class DevelopConfiguration {

  @Bean
  public ImageFileRepository imageFileRepository() {
    return new LocalImageFileRepository();
  }
}
