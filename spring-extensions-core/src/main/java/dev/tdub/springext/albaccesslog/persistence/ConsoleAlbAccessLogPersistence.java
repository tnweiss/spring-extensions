package dev.tdub.springext.albaccesslog.persistence;

import java.util.List;

import dev.tdub.springext.albaccesslog.AlbAccessLog;
import dev.tdub.springext.albaccesslog.AlbAccessLogPersistence;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ConsoleAlbAccessLogPersistence implements AlbAccessLogPersistence {
    @Override
    public void save(List<AlbAccessLog> albAccessLog) {
        albAccessLog.forEach(log::debug);
    }
}
