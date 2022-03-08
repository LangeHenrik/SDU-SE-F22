package dk.sdu.se_f22.searchmodule.infrastructure.interfaces;

import java.util.List;

public interface IndexingModule<T> {
    List<T> queryIndex(List<String> tokens);
}