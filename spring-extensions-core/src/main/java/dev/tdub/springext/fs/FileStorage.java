package dev.tdub.springext.fs;

import java.io.InputStream;

public interface FileStorage {
  void put(byte[] data, String relativePath);

  void delete(String relativePath);

  boolean exists(String relativePath);

  InputStream get(String relativePath);
}
