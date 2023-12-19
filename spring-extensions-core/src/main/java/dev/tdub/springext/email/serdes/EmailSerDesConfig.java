package dev.tdub.springext.email.serdes;

import dev.tdub.springext.email.Email;
import dev.tdub.springext.util.phonenumber.PhoneNumber;
import dev.tdub.springext.util.phonenumber.PhoneNumberAttributeConverter;
import org.hibernate.annotations.ConverterRegistration;
import org.hibernate.annotations.ConverterRegistrations;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@ConverterRegistrations({
    @ConverterRegistration(domainType = Email.class, converter = EmailAttributeConverter.class)
})
@Configuration
public class EmailSerDesConfig {
  @Bean
  public Jackson2ObjectMapperBuilderCustomizer customJsonSerializer() {
    return builder -> builder.modules(new EmailJacksonModule());
  }

  @Bean
  public MongoCustomConversions mongoCustomConversions() {
    return new EmailMongoCustomConversions();
  }
}
