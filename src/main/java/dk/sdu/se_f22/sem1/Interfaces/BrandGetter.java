package dk.sdu.se_f22.sem1.Interfaces;

import java.util.List;

public interface BrandGetter {
    List<Integer> query(List<String> tokens);
}
