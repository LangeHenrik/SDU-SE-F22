package dk.sdu.se_f22.searchmodule.infrastructure.interfaces;

import java.util.List;

public interface Filterable {
    List<String> filter(List<String> tokens);
}
