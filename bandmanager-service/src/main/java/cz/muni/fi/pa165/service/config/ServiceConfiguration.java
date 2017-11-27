package cz.muni.fi.pa165.service.config;


import cz.fi.muni.pa165.dto.SongDTO;
import cz.muni.fi.pa165.PersistanceTestingContext;
import cz.muni.fi.pa165.entity.Song;
import cz.muni.fi.pa165.service.AlbumServiceImpl;
import cz.muni.fi.pa165.service.SongServiceImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistanceTestingContext.class)
@ComponentScan(basePackageClasses = {SongServiceImpl.class, AlbumServiceImpl.class})
public class ServiceConfiguration {
    @Bean
    public Mapper dozer() {
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new DozerCustomConfig());
        return dozer;
    }
    
    public class DozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(Song.class, SongDTO.class);
        }
    }
}
