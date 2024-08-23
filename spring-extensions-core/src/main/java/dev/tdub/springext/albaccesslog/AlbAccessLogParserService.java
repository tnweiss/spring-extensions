package dev.tdub.springext.albaccesslog;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.GZIPInputStream;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.S3Object;

@Log4j2
@Configuration
@ConditionalOnProperty(
    value = "springext.albaccesslog.parser.enabled",
    havingValue = "true"
)
public class AlbAccessLogParserService {
  private final String bucket;
  private final String prefix;
  private final AlbAccessLogPersistence persistence;

  public AlbAccessLogParserService(
      @Value("${springext.albaccesslog.parser.bucket}") String bucket,
      @Value("${springext.albaccesslog.parser.prefix}") String prefix,
      AlbAccessLogPersistence persistence
  ) {
    this.bucket = bucket;
    this.prefix = prefix;
    this.persistence = persistence;
  }

  @Scheduled(fixedRateString = "${springext.albaccesslog.parser.interval}")
  public void parse() {
    log.debug("Parsing ALB access logs");
    try (S3Client client = S3Client.builder().build()) {
      for (String key : listFiles(client)) {
        log.trace("Parsing ALB access log: '{}'", key);
        persistence.save(parseFile(client, key));
        delete(client, key);
      }
    } catch (Exception ex) {
      log.warn("Failed to parse ALB access logs", ex);
    }
    log.debug("Finished parsing ALB access logs");
  }

  private List<String> listFiles(S3Client client) {
    return client.listObjectsV2(ListObjectsV2Request.builder()
        .bucket(bucket)
        .prefix(prefix)
        .build())
        .contents()
        .stream()
        .map(S3Object::key)
        .toList();
  }

  private void delete(S3Client client, String key) {
    client.deleteObject(DeleteObjectRequest.builder()
        .bucket(bucket)
        .key(key)
        .build());
  }

  private List<AlbAccessLog> parseFile(S3Client client, String key) throws Exception {
    byte[] data = client.getObject(GetObjectRequest.builder().bucket(bucket).key(key).build()).readAllBytes();
    return new String(new GZIPInputStream(new ByteArrayInputStream(data)).readAllBytes(), StandardCharsets.UTF_8)
      .lines()
      .map(AlbAccessLogDto::from)
      .map(AlbAccessLog.class::cast)
      .toList();
  }
}
