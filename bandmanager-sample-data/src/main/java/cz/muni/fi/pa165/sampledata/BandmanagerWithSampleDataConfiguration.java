package cz.muni.fi.pa165.sampledata;

import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.io.IOException;


/**
 * @author Iurii xkuznetc Kuznetcov
 */

@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = {SampleDataLoadingFacadeImpl.class})
public class BandmanagerWithSampleDataConfiguration {

    final static Logger logger = LoggerFactory.getLogger(BandmanagerWithSampleDataConfiguration.class);

    @Autowired
    SampleDataLoadingFacade sampleDataLoadingFacade;

    @PostConstruct
    public void dataLoading() throws IOException {
        logger.debug("Sample data loading begin...");
        // TODO: implement sample data loading through entities facades.
        sampleDataLoadingFacade.loadData();
        logger.debug("Sample data loading complete.");
    }
}
