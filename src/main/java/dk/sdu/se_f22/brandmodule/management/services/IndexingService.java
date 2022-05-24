package dk.sdu.se_f22.brandmodule.management.services;

import dk.sdu.se_f22.brandmodule.management.persistence.Persistence;
import dk.sdu.se_f22.sharedlibrary.models.Brand;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class IndexingService implements IIndexingService {

    private final IJsonService service;
    private final Persistence persistence;

    public IndexingService() {
        service = new JsonService();
        persistence = new Persistence();
    }

    public List<Brand> getBrandIndex() {
        return service.deserializeBrand();
    }

    public void StartIndexInterval() {
        Timer updateIndex = new Timer();
        updateIndex.schedule(new TimerTask() {
            @Override
            public void run() {
                //Ensure that brands are indexed in regular intervals
                persistence.BIM2.indexBrands(persistence.getAllBrands());
            }
        }, 0, persistence.getIndexingInterval());
    }
}