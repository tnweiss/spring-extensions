package dev.tdub.springext.albaccesslog;

import java.util.List;

public interface AlbAccessLogPersistence {
  void save(List<AlbAccessLog> albAccessLog);
}
