package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sortingmodule.range.RangeSearchResultMock;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidAttributeException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterTypeException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Intentionally default
 */
abstract class RangeFilterClass implements RangeFilter{
    public static final int DEFAULT_ID = -1;
    private final int ID;
    private final String NAME;
    private final String DESCRIPTION;
    private final String PRODUCT_ATTRIBUTE;

    public List<String> validAttributes;

    public RangeFilterClass(int ID, String NAME, String DESCRIPTION, String PRODUCT_ATTRIBUTE, List<String> validAttributes) {
        this.ID = ID;
        this.NAME = NAME;
        this.DESCRIPTION = DESCRIPTION;
        this.PRODUCT_ATTRIBUTE = PRODUCT_ATTRIBUTE;

        this.validAttributes = validAttributes;
    }

    public RangeFilterClass(String NAME, String DESCRIPTION, String PRODUCT_ATTRIBUTE, List<String> validAttributes) {
        this(DEFAULT_ID, NAME, DESCRIPTION, PRODUCT_ATTRIBUTE, validAttributes);
    }

    /**
     * uses non-strict mode, equivalent to useFilter(inputs, false), but the exception is silently handled here,
     * which is convenient, since it should never be thrown when strict is false.
     */
    public Collection<Product> useFilter(Collection<Product> inputs) {
        try {
            return useFilter(inputs, false);
        } catch (InvalidAttributeException e) {
            //should never happen
            e.printStackTrace();
        }
        // only possible if InvalidAttributeException, which won't happen in non-strict mode
        return inputs;
    }

    abstract Collection<Product> filterList(Collection<Product> inputs);

    public Collection<Product> useFilter(Collection<Product> inputs, boolean strict) throws InvalidAttributeException {
        if (!(validAttributes.contains(this.getProductAttribute()))) {
            if(strict){
                throw new InvalidAttributeException(this.getProductAttribute(), validAttributes);
            }
            return inputs;
        }

        return filterList(inputs);
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof RangeFilterClass casted)){
            return false;
        }
        if (this.ID != casted.getId()) {
            return false;
        }
        if (!this.NAME.equals(casted.getName())) {
            return false;
        }
        if (!this.DESCRIPTION.equals(casted.getDescription())) {
            return false;
        }
        //noinspection RedundantIfStatement
        if (!this.PRODUCT_ATTRIBUTE.equals(casted.getProductAttribute())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RangeFilterClass{" +
                "ID=" + ID +
                ", NAME='" + NAME + '\'' +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                ", PRODUCT_ATTRIBUTE='" + PRODUCT_ATTRIBUTE + '\'' +
                '}';
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public String getProductAttribute() {
        return PRODUCT_ATTRIBUTE;
    }


    //Below are the methods which are specialization dependent
    @Override
    public double getDbMinDouble() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a double filter");
    }

    @Override
    public Instant getDbMinInstant() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a time/Instant filter");
    }

    @Override
    public long getDbMinLong() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a long filter");
    }

    @Override
    public double getDbMaxDouble() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a double filter");
    }

    @Override
    public Instant getDbMaxInstant() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a time/Instant filter");
    }

    @Override
    public long getDbMaxLong() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a long filter");
    }

    @Override
    public double getUserMinDouble() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a double filter");
    }

    @Override
    public Instant getUserMinInstant() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a time/Instant filter");
    }

    @Override
    public long getUserMinLong() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a long filter");
    }

    @Override
    public double getUserMaxDouble() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a double filter");
    }

    @Override
    public Instant getUserMaxInstant() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a time/Instant filter");
    }

    @Override
    public long getUserMaxLong() throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a long filter");
    }

    @Override
    public double setUserMin(double userMin) throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a double filter");
    }

    @Override
    public Instant setUserMin(Instant userMin) throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a time/Instant filter");
    }

    @Override
    public long setUserMin(long userMin) throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a long filter");
    }

    @Override
    public double setUserMax(double userMax) throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a double filter");
    }

    @Override
    public Instant setUserMax(Instant userMax) throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a time/Instant filter");
    }

    @Override
    public long setUserMax(long userMax) throws InvalidFilterTypeException {
        throw new InvalidFilterTypeException("Not a long filter");
    }
}
