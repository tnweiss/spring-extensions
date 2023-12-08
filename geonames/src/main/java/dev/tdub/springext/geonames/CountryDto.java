package dev.tdub.springext.geonames;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class CountryDto implements Country {
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

  @JsonValue
  public String jsonValue() {
    return alpha2Code;
  }

  @Getter
  @EqualsAndHashCode
  static class CountryBuilder {
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
}
