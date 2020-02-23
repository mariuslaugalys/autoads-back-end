package lt.sdacademy.autoads.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface ImageFileRepository {

  Optional<byte[]> read(String fileName);

  Optional<String> save(MultipartFile file, String fileName);

  void delete(List<String> fileNames);
}
