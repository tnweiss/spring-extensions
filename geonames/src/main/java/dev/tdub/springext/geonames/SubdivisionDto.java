package dev.tdub.springext.geonames;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
public class SubdivisionDto implements Subdivision {
  private final CountryDto country;
  private final String name;
  private final String alpha2Code;
  @EqualsAndHashCode.Exclude
  private final Set<PostalCode> postalCodes;

  SubdivisionDto(CountryDto country, SubdivisionBuilder builder) {
    this.country = country;
    this.name = builder.name;
    this.alpha2Code = builder.alpha2Code;
    this.postalCodes = builder.postalCodes.stream()
        .map(b -> b.build(this))
        .collect(Collectors.toSet());
  }

  @JsonCreator
  public static Subdivision fromAlpha2Code(String alpha2Code) {
    return Geonames.getSubdivision(alpha2Code);
  }

  @JsonValue
  public String jsonValue() {
    return country.getAlpha2Code() + "-" + alpha2Code;
  }

  @Getter
  @EqualsAndHashCode
  @RequiredArgsConstructor
  static class SubdivisionBuilder {
    private final String name;
    private final String alpha2Code;
    private final List<PostalCodeDto.PostalCodeBuilder> postalCodes;

    public SubdivisionBuilder (String alpha2Code, String name) {
      this.name = name;
      this.alpha2Code = alpha2Code;
      this.postalCodes = new LinkedList<>();
    }

    public void add(String postalCode) {
      postalCodes.add(new PostalCodeDto.PostalCodeBuilder(postalCode));
    }

    public SubdivisionDto build(CountryDto country) {
      return new SubdivisionDto(country, this);
    }
  }
}
