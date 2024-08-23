package dev.tdub.springext.albaccesslog;

import java.util.ArrayList;
import java.util.List;

public class Shlex {
  public static List<String> split(String line) {
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

    return tokens;
  }
}
