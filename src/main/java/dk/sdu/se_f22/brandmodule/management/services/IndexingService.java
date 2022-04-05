package dk.sdu.se_f22.brandmodule.management.services;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class IndexingService implements IIndexingService {

    IJsonService service = new JsonService();

    public IndexingService() {;
    }

    public List<Brand> getBrandIndex(){;
        return service.deserializeBrand();

    }

    public void updateIndexInterval(int interval){
        Timer updateIndex = new Timer();
        updateIndex.schedule(new TimerTask() {
            @Override
            public void run() {

            }
        },interval);
    }
}