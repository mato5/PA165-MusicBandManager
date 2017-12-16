package cz.muni.fi.pa165.frontend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import cz.muni.fi.pa165.frontend.serializers.CustomMapSerializer;
import cz.muni.fi.pa165.sampledata.BandmanagerWithSampleDataConfiguration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;
import java.util.Map;

@EnableWebMvc
@Configuration
@Import(BandmanagerWithSampleDataConfiguration.class)
@ComponentScan()

public class RestSpringMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModule simpleModule = new SimpleModule();

        //simpleModule.addSerializer(Map.class, new CustomMapSerializer());

        objectMapper.registerModule(simpleModule);

        converter.setObjectMapper(objectMapper);
        converters.add(converter);
    }
}
