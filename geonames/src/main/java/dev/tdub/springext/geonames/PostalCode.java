package dev.tdub.springext.geonames;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
public class PostalCode {
  private final Subdivision subdivision;
  private final String code;

  PostalCode(Subdivision city, PostalCodeBuilder builder) {
    this.subdivision = city;
    this.code = builder.getCode();
  }

  @JsonCreator
  public static PostalCode fromPostalCode(String postalCode) {
    return Geonames.getPostalCode(postalCode);
  }

  @JsonValue
  public String jsonValue() {
    return code;
  }

  @Getter
  @EqualsAndHashCode
  @RequiredArgsConstructor
  static class PostalCodeBuilder {
    private final String code;

    public PostalCode build(Subdivision subdivision) {
      return new PostalCode(subdivision, this);
    }
  }
}
