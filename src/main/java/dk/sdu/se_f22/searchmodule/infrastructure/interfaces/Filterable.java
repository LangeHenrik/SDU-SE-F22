package dk.sdu.se_f22.searchmodule.infrastructure.interfaces;

import java.util.ArrayList;

public interface Filterable {
    ArrayList<String> filter(ArrayList<String> tokens);
}
