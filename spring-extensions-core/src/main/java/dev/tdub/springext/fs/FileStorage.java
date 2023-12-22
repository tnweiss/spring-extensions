package dev.tdub.springext.fs;

import java.io.InputStream;
import java.util.List;

public interface FileStorage {
  void put(byte[] data, String relativePath);

  void delete(String relativePath);

  boolean exists(String relativePath);

  InputStream get(String relativePath);

  List<String> list(String relativePath);
}
