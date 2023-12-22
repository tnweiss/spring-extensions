package dev.tdub.springext.fs;

import java.io.InputStream;
import java.util.List;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

@RequiredArgsConstructor
public class S3FileStorage implements FileStorage {
  private final String bucketName;
  private final S3Client s3Client;

  @Override
  public void put(byte[] data, String relativePath) {
    PutObjectRequest request = PutObjectRequest.builder()
        .bucket(bucketName)
        .key(relativePath)
        .build();
    RequestBody body = RequestBody.fromBytes(data);
    s3Client.putObject(request, body);
  }

  @Override
  public void delete(String relativePath) {
    DeleteObjectRequest request = DeleteObjectRequest.builder()
        .bucket(bucketName)
        .key(relativePath)
        .build();
    s3Client.deleteObject(request);
  }

  @Override
  public boolean exists(String relativePath) {
    HeadObjectRequest request = HeadObjectRequest.builder()
        .bucket(bucketName)
        .key(relativePath)
        .build();
    try {
      return s3Client.headObject(request) != null;
    } catch (NoSuchKeyException ex) {
      return false;
    }
  }

  @Override
  public InputStream get(String relativePath) {
    GetObjectRequest request = GetObjectRequest.builder()
        .bucket(bucketName)
        .key(relativePath)
        .build();
    return s3Client.getObjectAsBytes(request)
        .asInputStream();
  }

  @Override
  public List<String> list(String relativePath) {
    ListObjectsRequest request = ListObjectsRequest.builder()
        .bucket(bucketName)
        .prefix(relativePath)
        .build();
    return s3Client.listObjects(request)
        .contents()
        .stream()
        .map(S3Object::key)
        .toList();
  }
}
