package dk.sdu.se_f22.searchmodule.infrastructure.interfaces;

import java.util.List;

public interface BrandGetter {
    List<Integer> query(List<String> tokens);
}
