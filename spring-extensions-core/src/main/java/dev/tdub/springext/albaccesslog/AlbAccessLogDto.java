package dev.tdub.springext.albaccesslog;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
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
  private final String requestMethod;
  private final String requestUrl;
  private final Integer requestProcessingTime;
  private final Integer targetProcessingTime;
  private final Integer responseProcessingTime;
  private final Integer targetStatusCode;
  private final String fullLog;

  public static AlbAccessLogDto from(String line) {
    List<String> tokens = Shlex.split(line);

    try {
      Integer targetStatusCode = Objects.equals(tokens.get(9), "-") ? null : Integer.parseInt(tokens.get(9));

      String request = Objects.equals(tokens.get(12), "-") ? null : tokens.get(12);
      String requestMethod = request == null ? null : request.split(" ")[0];
      String requestUrl = request == null ? null : request.split(" ")[1];

      return AlbAccessLogDto.builder()
          .time(Instant.parse(tokens.get(1)))
          .clientIp(parseIp(tokens.get(3)))
          .requestProcessingTime(parseFloatMsToIntS(tokens.get(5)))
          .targetProcessingTime(parseFloatMsToIntS(tokens.get(6)))
          .responseProcessingTime(parseFloatMsToIntS(tokens.get(7)))
          .requestMethod(requestMethod)
          .requestUrl(requestUrl)
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

  private static Integer parseFloatMsToIntS(String floatValue) {
    return Objects.equals(floatValue, "-1") ? null : Math.round(Float.parseFloat(floatValue) * 1000);
  }

  private static InetAddress parseIp(String ip) throws UnknownHostException {
    return Objects.equals(ip, "-") ? null : InetAddress.getByName(ip.split(":")[0]);
  }
}
