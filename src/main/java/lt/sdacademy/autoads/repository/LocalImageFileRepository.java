package lt.sdacademy.autoads.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Profile("develop")
@Repository
public class LocalImageFileRepository implements ImageFileRepository {

  @Value("${app.image.storage.directory}")
  String storagePath;

  @Override
  public Optional<byte[]> read(String fileName) {
    try {
      return Optional.of(Files.readAllBytes(new File(storagePath + "/" + fileName).toPath()));
    } catch (IOException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<String> save(MultipartFile file, String fileName) {
    File saved = new File(storagePath + "/" + fileName);
    try {
      file.transferTo(saved);
      return Optional.of(fileName);
    } catch (IOException e) {
      return Optional.empty();
    }
  }

  @Override
  public void delete(List<String> fileNames) {
    List<File> files = fileNames.stream()
        .map((fileName) -> new File(storagePath + "/" + fileName))
        .collect(Collectors.toList());
    files.forEach(File::delete);
  }
}
