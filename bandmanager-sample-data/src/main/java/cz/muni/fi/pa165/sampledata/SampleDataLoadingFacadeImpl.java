package cz.muni.fi.pa165.sampledata;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {
    @Override
    public void loadData() throws IOException {
        // TODO: implement sample data loading through entities facades.
    }
}
