package dev.tdub.springext.fs;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import dev.tdub.springext.error.exceptions.ClientException;
import dev.tdub.springext.error.exceptions.InternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class LocalFileStorage implements FileStorage {
  private final File rootPath;

  public LocalFileStorage(String rootPath) {
    this.rootPath = new File(rootPath);

    if (!this.rootPath.exists()) {
      log.warn("Root path does not exist, creating: {}", rootPath);

      if (!this.rootPath.mkdirs()) {
        throw new IllegalStateException("Failed to create root path: " + rootPath);
      }
    }
  }

  @Override
  public void put(byte[] data, String relativePath) {
    File file = new File(rootPath, relativePath);
    makeParentDirs(file, relativePath);

    try {
      Files.write(file.toPath(), data);
    } catch (Exception e) {
      log.error(e);
      throw new InternalServerException("Failed to write file: " + relativePath);
    }
  }

  @Override
  public void delete(String relativePath) {
    File file = new File(rootPath, relativePath);
    if (!file.delete()) {
      log.error("Failed to delete file: '{}'", file.getPath());
      throw new ClientException("Failed to delete file: " + relativePath);
    }
  }

  @Override
  public boolean exists(String relativePath) {
    return new File(rootPath, relativePath).exists();
  }

  @Override
  public InputStream get(String relativePath) {
    try {
      return Files.newInputStream(new File(rootPath, relativePath).toPath());
    } catch (Exception e) {
      log.error(e);
      throw new InternalServerException("Failed to read file: " + relativePath);
    }
  }

  @Override
  public List<String> list(String relativePath) {
    File dir = new File(rootPath, relativePath);
    if (!dir.exists() || !dir.isDirectory()) {
      log.error("Directory does not exist: '{}'", dir.getPath());
      throw new ClientException("Directory does not exist: " + relativePath);
    }

    if (dir.list() == null) {
      log.error("Failed to list directory: '{}'", dir.getPath());
      throw new InternalServerException("Failed to list directory: " + relativePath);
    }

    return Arrays.stream(Objects.requireNonNull(dir.list()))
        .map(p -> p.replaceFirst(this.rootPath.getPath(), ""))
        .toList();
  }

  private void makeParentDirs(File file, String relativePath) {
    File parent = file.getParentFile();
    if (!parent.mkdirs()) {
      log.error("Failed to create parent directories for: '{}'", file.getPath());
      throw new InternalServerException("Failed to create parent directories for: " + relativePath);
    }
  }
}
