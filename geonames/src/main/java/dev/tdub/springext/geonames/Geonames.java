package dev.tdub.springext.geonames;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import dev.tdub.springext.error.exceptions.InternalServerException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Geonames {
  private static final String COUNTRIES_DATA_FILE_NAME_DEFAULT = "countries.tsv";
  private static final String COUNTRIES_DATA_FILE_NAME_PROPERTY = "geonames.data.countries";

  private static final String SUBDIVISIONS_DATA_FILE_NAME_DEFAULT = "subdivisions.tsv";
  private static final String SUBDIVISIONS_DATA_FILE_NAME_PROPERTY = "geonames.data.subdivisions";

  private static final String POSTAL_CODES_DATA_FILE_NAME_DEFAULT = "postal-codes.tsv";
  private static final String POSTAL_CODES_DATA_FILE_NAME_PROPERTY = "geonames.data.postal-codes";
  
  private static final Map<String, Country> countries;
  private static final Map<String, Subdivision> subdivisions;
  private static final Map<String, PostalCode> postalCodes;


  static {
    Map<String, String> countryCodes = loadCountries();
    Map<String, String> subdivisionCodes = loadSubdivisions();
    List<GeonameRecord> postalCodeRecords = loadPostalCodes();

    Map<String, CountryDto.CountryBuilder> builder = new HashMap<>();
    for (GeonameRecord record: postalCodeRecords) {
      String countryName = countryCodes.get(record.getCountryCode());
      String subdivisionName = subdivisionCodes.get(record.getSubdivisionCode());
      String postalCode = record.getPostalCode();

      if (!builder.containsKey(record.getCountryCode())) {
        builder.put(record.getCountryCode(), new CountryDto.CountryBuilder(record.getCountryCode(), countryName));
      }
      builder.get(record.getCountryCode()).add(subdivisionName, record.getSubdivisionCode(), postalCode);
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

  public static Country getCountry(String alpha2Code) {
    return Optional.ofNullable(countries.get(alpha2Code))
        .orElseThrow(() -> new IllegalArgumentException("Invalid country code: " + alpha2Code));
  }

  public static Collection<Country> getCountries() {
    return countries.values();
  }

  public static Subdivision getSubdivision(String alpha2Code) {
    return Optional.ofNullable(subdivisions.get(alpha2Code))
        .orElseThrow(() -> new IllegalArgumentException("Invalid subdivision code: " + alpha2Code));
  }

  public static Collection<Subdivision> getSubdivisions(String countryCode) {
    return getCountry(countryCode).getSubdivisions();
  }

  public static PostalCode getPostalCode(String postalCode) {
    return Optional.ofNullable(postalCodes.get(postalCode))
        .orElseThrow(() -> new IllegalArgumentException("Invalid postal code: " + postalCode));
  }

  private static Map<String, String> loadCountries() {
    Map<String, String> output = new HashMap<>();

    try (CSVReader csvReader = reader(getCountriesPath())) {
      String[] nextRecord;

      while ((nextRecord = csvReader.readNext()) != null) {
        output.put(nextRecord[0], nextRecord[1]);
      }
    } catch (IOException | CsvValidationException e) {
      log.error("Failed to read data.csv", e);
      throw new InternalServerException("Failed to read data.csv");
    }

    return output;
  }

  private static Map<String, String> loadSubdivisions() {
    Map<String, String> output = new HashMap<>();

    try (CSVReader csvReader = reader(getSubdivisionsPath())) {
      String[] nextRecord;

      while ((nextRecord = csvReader.readNext()) != null) {
        output.put(nextRecord[0], nextRecord[1]);
      }
    } catch (IOException | CsvValidationException e) {
      log.error("Failed to read data.csv", e);
      throw new InternalServerException("Failed to read data.csv");
    }

    return output;
  }

  private static List<GeonameRecord> loadPostalCodes() {
    List<GeonameRecord> output = new ArrayList<>();

    try (CSVReader csvReader = reader(getPostalCodesPath())) {
      String[] nextRecord;

      while ((nextRecord = csvReader.readNext()) != null) {
        output.add(new GeonameRecord(nextRecord[0], nextRecord[1], nextRecord[2]));
      }
    } catch (IOException | CsvValidationException e) {
      log.error("Failed to read data.csv", e);
      throw new InternalServerException("Failed to read data.csv");
    }

    return output;
  }

  private static CSVReader reader(String filepath) throws IOException {
    return new CSVReaderBuilder(
        new FileReader(filepath, StandardCharsets.UTF_16))
          .withCSVParser(new com.opencsv.CSVParserBuilder().withSeparator('\t').build())
        .build();
  }

  private static String getCountriesPath() {
    String countriesDataFilePath = System.getProperty(COUNTRIES_DATA_FILE_NAME_PROPERTY, COUNTRIES_DATA_FILE_NAME_DEFAULT);
    URL countriesDataFile = Geonames.class.getClassLoader().getResource(countriesDataFilePath);

    if (countriesDataFile == null) {
      log.error("Failed to find " + countriesDataFilePath + " in classpath");
      throw new InternalServerException("Failed to load Geonames data");
    }

    return countriesDataFile.getPath();
  }

  private static String getSubdivisionsPath() {
    String subdivisionsDataFilePath = System.getProperty(SUBDIVISIONS_DATA_FILE_NAME_PROPERTY, SUBDIVISIONS_DATA_FILE_NAME_DEFAULT);
    URL subdivisionsDataFile = Geonames.class.getClassLoader().getResource(subdivisionsDataFilePath);

    if (subdivisionsDataFile == null) {
      log.error("Failed to find " + subdivisionsDataFilePath + " in classpath");
      throw new InternalServerException("Failed to load Geonames data");
    }

    return subdivisionsDataFile.getPath();
  }

  private static String getPostalCodesPath() {
    String postalCodesDataFilePath = System.getProperty(POSTAL_CODES_DATA_FILE_NAME_PROPERTY, POSTAL_CODES_DATA_FILE_NAME_DEFAULT);
    URL postalCodesDataFile = Geonames.class.getClassLoader().getResource(postalCodesDataFilePath);

    if (postalCodesDataFile == null) {
      log.error("Failed to find " + postalCodesDataFilePath + " in classpath");
      throw new InternalServerException("Failed to load Geonames data");
    }

    return postalCodesDataFile.getPath();
  }

  @Getter
  @RequiredArgsConstructor
  private static class GeonameRecord {
    private final String countryCode;
    private final String subdivisionCode;
    private final String postalCode;
  }
}
