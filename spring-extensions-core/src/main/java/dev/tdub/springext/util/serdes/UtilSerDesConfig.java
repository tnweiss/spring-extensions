package dev.tdub.springext.util.serdes;

import dev.tdub.springext.util.email.Email;
import dev.tdub.springext.util.email.EmailAttributeConverter;
import dev.tdub.springext.util.phonenumber.PhoneNumber;
import dev.tdub.springext.util.phonenumber.PhoneNumberAttributeConverter;
import org.hibernate.annotations.ConverterRegistration;
import org.hibernate.annotations.ConverterRegistrations;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@ConverterRegistrations({
    @ConverterRegistration(domainType = Email.class, converter = EmailAttributeConverter.class),
    @ConverterRegistration(domainType = PhoneNumber.class, converter = PhoneNumberAttributeConverter.class),
})
@Configuration
public class UtilSerDesConfig {
  @Bean
  public Jackson2ObjectMapperBuilderCustomizer customJsonSerializer() {
    return builder -> builder.modules(new UtilJacksonModule());
  }

  @Bean
  public MongoCustomConversions mongoCustomConversions() {
    return new UtilMongoCustomConversions();
  }
}
