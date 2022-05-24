package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.util.Collection;
import java.util.List;

// This class is a dummy class, with the sole purpose of testing equals from RangeFilterCLass

public class RangeFilterClassDummyFilter extends RangeFilterClass{
    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }

    public RangeFilterClassDummyFilter(int ID, String NAME, String DESCRIPTION, String PRODUCT_ATTRIBUTE) {
        super(ID, NAME, DESCRIPTION, PRODUCT_ATTRIBUTE,
                List.of(new String[]{"ean"}));
    }

    public RangeFilterClassDummyFilter(String NAME, String DESCRIPTION, String PRODUCT_ATTRIBUTE, List<String> validAttributes) {
        super(NAME, DESCRIPTION, PRODUCT_ATTRIBUTE, validAttributes);
    }
    @Override
    public FilterTypes getType() {
        return null;
    }

    @Override
    Collection<Product> filterList(Collection<Product> inputs) {
        return null;
    }
}
