package dk.sdu.se_f22.brandmodule.stemming;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface IStemmer {
    public String stem(String word);

    public ArrayList<String> stem(List<String> word);

    public void addException(String exception);

    public List<Collection<String>> getExceptions();

    public void updateException(String exception, String newException);

    public void removeException(String exception);

    public boolean exceptionExists(String exception);
}
