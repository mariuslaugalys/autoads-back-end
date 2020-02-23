package lt.sdacademy.autoads.repository;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Profile("deploy")
@Repository
public class RemoteImageFileRepository implements ImageFileRepository {

  @Value("${app.aws.access.key}")
  private String awsAccessKey;
  @Value("${app.aws.access.secret}")
  private String awsAccessSecret;
  @Value("${app.aws.storage.bucket}")
  private String bucketName;
  @Value("${app.aws.storage.path}")
  private String pathPrefix;

  private AmazonS3 s3client;

  @PostConstruct
  private void init() {
    AWSCredentials credentials = new BasicAWSCredentials(
        awsAccessKey,
        awsAccessSecret
    );

    s3client = AmazonS3ClientBuilder
        .standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withRegion(Regions.EU_NORTH_1)
        .build();
  }

  @Override
  public Optional<byte[]> read(String fileName) {
    try {
      return Optional.of(
          s3client.getObject(bucketName, String.format("%s/%s", pathPrefix, fileName))
              .getObjectContent()
              .readAllBytes()
      );
    } catch (Exception e) {
      e.printStackTrace();
    }

    return Optional.empty();
  }

  @Override
  public Optional<String> save(MultipartFile file, String fileName) {
    try {
      ObjectMetadata meta = new ObjectMetadata();
      meta.setContentLength(file.getSize());
      s3client.putObject(
          bucketName,
          String.format("%s/%s", pathPrefix, fileName),
          file.getInputStream(), meta
      );
      return Optional.of(fileName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  @Override
  public void delete(List<String> fileNames) {
    fileNames.forEach(fileName -> s3client.deleteObject(bucketName, fileName));
  }
}
