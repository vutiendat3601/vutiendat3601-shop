package vn.io.vutiendat3601.shop.v2.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {
  @Bean
  ObjectMapper objectMapper() {
    final ObjectMapper objMapper = new ObjectMapper();
    objMapper.findAndRegisterModules();
    return objMapper;
  }
}
