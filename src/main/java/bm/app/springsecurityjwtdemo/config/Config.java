package bm.app.springsecurityjwtdemo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * I could customize the process of mapping JSONs and objects to JSONs.
     * For example, I can make my mapper not serialize null fields.
     *
     * void customizeObjectMapper() {
     *         objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
     *     }
     */

    /**
     * Spring by default contains a "basic-error-controller" which is visible in Swagger.
     * If I want to I can disable it.
     */
//    @Bean
//    public GroupedOpenApi openApi() {
//        return GroupedOpenApi.builder()
//                .
//    }

}
