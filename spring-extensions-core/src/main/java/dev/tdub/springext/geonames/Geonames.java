package dev.tdub.springext.geonames;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import dev.tdub.springext.error.exceptions.InternalServerException;
import dev.tdub.springext.geonames.country.Country;
import dev.tdub.springext.geonames.country.CountryDto;
import dev.tdub.springext.geonames.postalcode.PostalCode;
import dev.tdub.springext.geonames.subdivision.Subdivision;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Geonames {
  private static final String LOAD_VALIDATION_PROPERTY = "dev.tdub.springext.geonames.load-validation";

  private static final String COUNTRIES_DATA_FILE_NAME_DEFAULT = "countries.tsv";
  private static final String COUNTRIES_DATA_FILE_NAME_PROPERTY = "geonames.data.countries";

  private static final String SUBDIVISIONS_DATA_FILE_NAME_DEFAULT = "subdivisions.tsv";
  private static final String SUBDIVISIONS_DATA_FILE_NAME_PROPERTY = "geonames.data.subdivisions";

  private static final String POSTAL_CODES_DATA_FILE_NAME_DEFAULT = "postal-codes.tsv";
  private static final String POSTAL_CODES_DATA_FILE_NAME_PROPERTY = "geonames.data.postal-codes";

  private static final CSVParser csvParser = new CSVParserBuilder()
      .withSeparator('\t')
      .build();
  
  private static final Map<String, Country> countries;
  private static final Map<String, Subdivision> subdivisions;
  private static final Map<String, PostalCode> postalCodes;


  static {
    if (System.getProperty(LOAD_VALIDATION_PROPERTY, "false").equals("false")) {
      countries = Map.of();
      subdivisions = Map.of();
      postalCodes = Map.of();

      log.warn("Skipping Geonames validations. Set '{}' to 'true' to enable validation", LOAD_VALIDATION_PROPERTY);
    } else {
      Map<String, String> countryCodes = loadCountries();
      Map<String, String> subdivisionCodes = loadSubdivisions();
      List<GeonameRecord> postalCodeRecords = loadPostalCodes();

      Map<String, CountryDto.CountryBuilder> builder = new HashMap<>();
      for (GeonameRecord record: postalCodeRecords) {
        String countryName = countryCodes.get(record.getCountryCode());
        String subdivisionName = subdivisionCodes.get(record.getSubdivisionCode());
        String postalCode = record.getPostalCode();

        if (!builder.containsKey(record.getCountryCode())) {
          builder.put(record.getCountryCode(),
                  new CountryDto.CountryBuilder(record.getCountryCode(), countryName));
        }
        builder.get(record.getCountryCode())
                .add(subdivisionName, record.getSubdivisionCode(), postalCode);
      }

      countries = builder.entrySet().stream()
              .map(e -> Map.entry(e.getKey(), e.getValue().build()))
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

      subdivisions = Collections.unmodifiableMap(countries.values().stream()
              .flatMap(country -> country.getSubdivisions().stream())
              .collect(Collectors.toMap(Subdivision::getAlpha2Code, Function.identity())));

      postalCodes = Collections.unmodifiableMap(subdivisions.values().stream()
              .flatMap(city -> city.getPostalCodes().stream())
              .collect(Collectors.toMap(PostalCode::getCode, Function.identity())));

      log.info("Loaded "
              + countries.size() + " countries and "
              + subdivisions.size() + " subdivisions and "
              + postalCodes.size() + " postal codes");
    }
  }

  public static Optional<Country> getCountry(String alpha2Code) {
    return Optional.ofNullable(countries.get(alpha2Code));
  }

  public static Collection<Country> getCountries() {
    return countries.values();
  }

  public static Optional<Subdivision> getSubdivision(String alpha2Code) {
    return Optional.ofNullable(subdivisions.get(alpha2Code));
  }

  public static Collection<Subdivision> getSubdivisions(String countryCode) {
    return getCountry(countryCode)
        .map(Country::getSubdivisions)
        .orElse(Set.of());
  }

  public static Optional<PostalCode> getPostalCode(String postalCode) {
    return Optional.ofNullable(postalCodes.get(postalCode));
  }

  private static Map<String, String> loadCountries() {
    String filename = System.getProperty(COUNTRIES_DATA_FILE_NAME_PROPERTY, COUNTRIES_DATA_FILE_NAME_DEFAULT);
    List<String[]> data = readTsv(filename);

    Map<String, String> output = new HashMap<>();
    for (String[] row: data) {
      output.put(row[0], row[1]);
    }

    return output;
  }

  private static Map<String, String> loadSubdivisions() {
    String filename = System.getProperty(SUBDIVISIONS_DATA_FILE_NAME_PROPERTY, SUBDIVISIONS_DATA_FILE_NAME_DEFAULT);
    List<String[]> data = readTsv(filename);

    Map<String, String> output = new HashMap<>();
    for (String[] row: data) {
      output.put(row[0], row[1]);
    }

    return output;
  }

  private static List<GeonameRecord> loadPostalCodes() {
    String filename = System.getProperty(POSTAL_CODES_DATA_FILE_NAME_PROPERTY, POSTAL_CODES_DATA_FILE_NAME_DEFAULT);
    List<String[]> data = readTsv(filename);

    List<GeonameRecord> output = new ArrayList<>();
    for (String[] row: data) {
      output.add(new GeonameRecord(row[0], row[1], row[2]));
    }

    return output;
  }

  private static List<String[]> readTsv(String filepath) {
    try (InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(filepath)) {
      if (inputStream == null) {
        throw new InternalServerException("Failed to load Geonames data from " + filepath + " in classpath");
      }
      Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_16);
      try (CSVReader csv = new CSVReaderBuilder(reader).withCSVParser(csvParser).build()) {
        List<String[]> output = new ArrayList<>();
        String[] nextRecord;
        while((nextRecord = csv.readNext()) != null) {
          output.add(nextRecord);
        }
        return output;
      } catch (CsvValidationException ex) {
        log.error("Failed to read " + filepath + " in classpath", ex);
        throw new InternalServerException("Failed to load Geonames data");
      }
    } catch (IOException e) {
      log.error("Failed to read " + filepath + " in classpath", e);
      throw new InternalServerException("Failed to load Geonames data");
    }
  }

  @Getter
  @RequiredArgsConstructor
  private static class GeonameRecord {
    private final String countryCode;
    private final String subdivisionCode;
    private final String postalCode;
  }
}
