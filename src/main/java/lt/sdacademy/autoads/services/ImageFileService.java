package lt.sdacademy.autoads.services;

import lt.sdacademy.autoads.repository.ImageFileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class ImageFileService {

  private final ImageFileRepository imageFileRepository;

  public ImageFileService(ImageFileRepository imageFileRepository) {
    this.imageFileRepository = imageFileRepository;
  }

  public byte[] get(String fileName) {
    return imageFileRepository.read(fileName)
        .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "File not found"));
  }
}
