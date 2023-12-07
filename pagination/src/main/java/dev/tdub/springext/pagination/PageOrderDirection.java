package dev.tylerweiss.springext.pagination;

import dev.tylerweiss.springext.error.exceptions.ClientException;

public enum PageOrderDirection {
  ASC,
  DESC;

  public static PageOrderDirection from(String direction) {
    return switch (direction) {
      case "ASC" -> ASC;
      case "DESC" -> DESC;
      default -> throw new ClientException("Unknown sort direction '%s'".formatted(direction));
    };
  }
}
