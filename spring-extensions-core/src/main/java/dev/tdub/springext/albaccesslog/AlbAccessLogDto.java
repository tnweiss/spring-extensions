package dev.tdub.springext.albaccesslog;

import java.net.InetAddress;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@RequiredArgsConstructor
public class AlbAccessLogDto implements AlbAccessLog {
  private final Instant time;
  private final InetAddress clientIp;
  private final String request;
  private final Float requestProcessingTime;
  private final Float targetProcessingTime;
  private final Float responseProcessingTime;
  private final Integer targetStatusCode;
  private final String fullLog;

  public static AlbAccessLogDto from(String line) {
    StringBuilder sb = new StringBuilder();
    List<String> tokens = new ArrayList<>();

    boolean inQuotes = false;

    for (int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);

      if (c == '"') {
        inQuotes = !inQuotes;
      }

      if (c == ' ' && !inQuotes) {
        tokens.add(sb.toString());
        sb.setLength(0);
      } else {
        sb.append(c);
      }
    }

    try {
      InetAddress clientIp = Objects.equals(tokens.get(3), "-") ? null : InetAddress.getByName(tokens.get(3).split(":")[0]);
      Float requestProcessingTime = Objects.equals(tokens.get(5), "-") ? null : Float.parseFloat(tokens.get(5));
      Float targetProcessingTime = Objects.equals(tokens.get(6), "-") ? null : Float.parseFloat(tokens.get(6));
      Float responseProcessingTime = Objects.equals(tokens.get(7), "-") ? null : Float.parseFloat(tokens.get(7));
      Integer targetStatusCode = Objects.equals(tokens.get(9), "-") ? null : Integer.parseInt(tokens.get(9));
      String request = Objects.equals(tokens.get(12), "-") ? null : tokens.get(12);

      return AlbAccessLogDto.builder()
          .time(Instant.parse(tokens.get(1)))
          .clientIp(clientIp)
          .requestProcessingTime(requestProcessingTime)
          .targetProcessingTime(targetProcessingTime)
          .responseProcessingTime(responseProcessingTime)
          .request(request)
          .targetStatusCode(targetStatusCode)
          .fullLog(line)
          .build();
    } catch (Exception ex) {
      System.out.println("Failed to parse ALB access log: " + line);
      return AlbAccessLogDto.builder()
          .time(Instant.parse(tokens.get(1)))
          .fullLog(line)
          .build();
    }
  }
}
