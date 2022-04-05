package dk.sdu.se_f22.brandmodule.management.services;
import dk.sdu.se_f22.brandmodule.management.persistence.Persistence;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class IndexingService implements IIndexingService {

    IJsonService service = new JsonService();
    Persistence p = new Persistence();
    public IndexingService() {;
    }

    public List<Brand> getBrandIndex(){;
        return service.deserializeBrand();

    }

    public void StartIndexInterval(){
        Timer updateIndex = new Timer();
        updateIndex.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(p.getIndexingInterval());
            }
        },0,p.getIndexingInterval());
    }
}