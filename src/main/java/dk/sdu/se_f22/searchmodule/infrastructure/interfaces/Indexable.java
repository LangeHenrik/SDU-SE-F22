package dk.sdu.se_f22.searchmodule.infrastructure.interfaces;

import java.util.List;

public interface Indexable<T> {
    List<T> queryIndex(List<String> tokens);
}