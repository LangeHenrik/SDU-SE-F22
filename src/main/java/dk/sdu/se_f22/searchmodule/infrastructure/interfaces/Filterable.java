package dk.sdu.se_f22.searchmodule.infrastructure.interfaces;

import java.util.ArrayList;
import java.util.List;

public interface Filterable {
    ArrayList<String> filter(ArrayList<String> tokens);
}
