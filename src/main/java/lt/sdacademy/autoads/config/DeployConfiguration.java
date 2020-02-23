package lt.sdacademy.autoads.config;

import lt.sdacademy.autoads.repository.ImageFileRepository;
import lt.sdacademy.autoads.repository.RemoteImageFileRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("deploy")
@Configuration
public class DeployConfiguration {

  @Bean
  public ImageFileRepository imageFileRepository() {
    return new RemoteImageFileRepository();
  }
}
