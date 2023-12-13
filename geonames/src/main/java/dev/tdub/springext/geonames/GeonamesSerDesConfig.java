package dev.tdub.springext.geonames;

import dev.tdub.springext.geonames.country.CountryAttributeConverter;
import dev.tdub.springext.geonames.postalcode.PostalCodeAttributeConverter;
import dev.tdub.springext.geonames.subdivision.SubdivisionAttributeConverter;
import org.hibernate.annotations.ConverterRegistration;
import org.hibernate.annotations.ConverterRegistrations;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@ConverterRegistrations({
    @ConverterRegistration(domainType = Country.class, converter = CountryAttributeConverter.class),
    @ConverterRegistration(domainType = Subdivision.class, converter = SubdivisionAttributeConverter.class),
    @ConverterRegistration(domainType = PostalCode.class, converter = PostalCodeAttributeConverter.class)
})
@Configuration
public class GeonamesSerDesConfig {
  @Bean
  public Jackson2ObjectMapperBuilderCustomizer customJsonSerializer() {
    return builder -> builder.modules(new GeonamesJacksonModule());
  }

  @Bean
  public MongoCustomConversions mongoCustomConversions() {
    return new GeonamesMongoCustomConversions();
  }
}
