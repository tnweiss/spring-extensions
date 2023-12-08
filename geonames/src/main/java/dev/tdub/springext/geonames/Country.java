package dev.tdub.springext.geonames;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * ISO 3166 Country Codes
 * https://www.iso.org/obp/ui/#search
 */
@Getter
@EqualsAndHashCode
public class Country {
  private final String name;
  private final String alpha2Code;
  @EqualsAndHashCode.Exclude
  private final Set<Subdivision> subdivisions;

  Country (CountryBuilder builder) {
    this.alpha2Code = builder.getAlpha2Code();
    this.name = builder.getName();

    this.subdivisions = builder.getSubdivisions().values().stream()
        .map(b -> b.build(this))
        .collect(Collectors.toSet());
  }

  @JsonCreator
  public static Country fromAlpha2Code(String alpha2Code) {
    return Geonames.getCountry(alpha2Code);
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
    private final Map<String, Subdivision.SubdivisionBuilder> subdivisions;

    public CountryBuilder (String alpha2Code, String name) {
      this.name = name;
      this.alpha2Code = alpha2Code;
      this.subdivisions = new LinkedHashMap<>();
    }

    public void add(String subdivisionName, String subdivisionAlpha2Code, String postalCode) {
      if (!subdivisions.containsKey(subdivisionAlpha2Code)) {
        subdivisions.put(subdivisionAlpha2Code, new Subdivision.SubdivisionBuilder(subdivisionAlpha2Code, subdivisionName));
      }
      subdivisions.get(subdivisionAlpha2Code).add(postalCode);
    }

    public Country build() {
      return new Country(this);
    }
  }
}
