package dev.tdub.springext.geonames.country;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;

import dev.tdub.springext.geonames.Geonames;
import dev.tdub.springext.geonames.subdivision.Subdivision;
import dev.tdub.springext.geonames.subdivision.SubdivisionDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class CountryDto implements Country {
  public static final CountryDto UNKNOWN = new CountryDto("NK");

  private final String name;
  private final String alpha2Code;
  @EqualsAndHashCode.Exclude
  private final Set<Subdivision> subdivisions;

  CountryDto(CountryBuilder builder) {
    this.alpha2Code = builder.getAlpha2Code();
    this.name = builder.getName();

    this.subdivisions = builder.getSubdivisions().values().stream()
        .map(b -> b.build(this))
        .collect(Collectors.toSet());
  }

  private CountryDto(String alpha2Code) {
    this.alpha2Code = alpha2Code;
    this.name = "UNKNOWN";
    this.subdivisions = Set.of();
  }

  @JsonCreator
  public static Country fromAlpha2Code(String alpha2Code) {
    if (alpha2Code == null) {
      return null;
    }

    return Geonames.getCountry(alpha2Code)
        .orElse(new CountryDto(alpha2Code));
  }

  @Getter
  @EqualsAndHashCode
  public static class CountryBuilder {
    private final String name;
    private final String alpha2Code;
    private final Map<String, SubdivisionDto.SubdivisionBuilder> subdivisions;

    public CountryBuilder (String alpha2Code, String name) {
      this.name = name;
      this.alpha2Code = alpha2Code;
      this.subdivisions = new LinkedHashMap<>();
    }

    public void add(String subdivisionName, String subdivisionAlpha2Code, String postalCode) {
      if (!subdivisions.containsKey(subdivisionAlpha2Code)) {
        subdivisions.put(subdivisionAlpha2Code, new SubdivisionDto.SubdivisionBuilder(subdivisionAlpha2Code, subdivisionName));
      }
      subdivisions.get(subdivisionAlpha2Code).add(postalCode);
    }

    public CountryDto build() {
      return new CountryDto(this);
    }
  }

  @Override
  public String toString() {
    return this.alpha2Code;
  }
}