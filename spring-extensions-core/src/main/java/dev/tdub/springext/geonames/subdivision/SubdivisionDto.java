package dev.tdub.springext.geonames.subdivision;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;

import dev.tdub.springext.geonames.Geonames;
import dev.tdub.springext.geonames.postalcode.PostalCode;
import dev.tdub.springext.geonames.postalcode.PostalCodeDto;
import dev.tdub.springext.geonames.country.CountryDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
public class SubdivisionDto implements Subdivision {
  public static final SubdivisionDto UNKNOWN = new SubdivisionDto("NK-NK");

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

  SubdivisionDto(String alpha2Code) {
    this.country = CountryDto.UNKNOWN;
    this.name = "UNKNOWN";
    this.alpha2Code = alpha2Code;
    this.postalCodes = Set.of();
  }

  @JsonCreator
  public static Subdivision fromAlpha2Code(String alpha2Code) {
    if (alpha2Code == null) {
      return null;
    }
    return Geonames.getSubdivision(alpha2Code)
        .orElse(new SubdivisionDto(alpha2Code));
  }

  @Getter
  @EqualsAndHashCode
  @RequiredArgsConstructor
  public static class SubdivisionBuilder {
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

  @Override
  public String toString() {
    return this.alpha2Code;
  }
}
