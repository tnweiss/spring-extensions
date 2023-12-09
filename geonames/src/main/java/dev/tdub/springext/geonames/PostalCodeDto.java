package dev.tdub.springext.geonames;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
public class PostalCodeDto implements PostalCode {
  private final SubdivisionDto subdivision;
  private final String code;

  PostalCodeDto(SubdivisionDto city, PostalCodeBuilder builder) {
    this.subdivision = city;
    this.code = builder.getCode();
  }

  PostalCodeDto(String code) {
    this.subdivision = null;
    this.code = code;
  }

  @JsonCreator
  public static PostalCode fromPostalCode(String postalCode) {
    return Geonames.getPostalCode(postalCode).orElse(new PostalCodeDto(postalCode));
  }

  @Getter
  @EqualsAndHashCode
  @RequiredArgsConstructor
  static class PostalCodeBuilder {
    private final String code;

    public PostalCodeDto build(SubdivisionDto subdivision) {
      return new PostalCodeDto(subdivision, this);
    }
  }
}
